package edu.iesam.gametracker.features.videogames.presentation

import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import androidx.core.content.FileProvider
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.android.material.appbar.MaterialToolbar
import edu.iesam.gametracker.MainActivity
import edu.iesam.gametracker.R
import edu.iesam.gametracker.app.domain.ErrorApp
import edu.iesam.gametracker.app.presentation.ContentShare
import edu.iesam.gametracker.app.presentation.hide
import edu.iesam.gametracker.app.presentation.views.ErrorAppUIFactory
import edu.iesam.gametracker.databinding.FragmentVideogamesBinding
import edu.iesam.gametracker.features.videogames.domain.GetVideogamesUseCase
import edu.iesam.gametracker.features.videogames.domain.Videogame
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream

class VideogamesFragment : Fragment() {

    private var _binding: FragmentVideogamesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VideogamesViewModel by viewModel()

    private var isShowingFavorites = false
    private val videogamesAdapter: VideogamesAdapter = VideogamesAdapter()

    private lateinit var skeleton: Skeleton

    private val contentShare: ContentShare by inject()
    private val errorFactory: ErrorAppUIFactory by inject()

    private var allVideogames: List<GetVideogamesUseCase.VideoGameFeed> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideogamesBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
        binding.apply {
            videogameList.layoutManager = LinearLayoutManager(requireContext())
            videogameList.adapter = videogamesAdapter

            skeleton = videogameList.applySkeleton(R.layout.view_videogames_item, 8)

            swiperefresh.setOnRefreshListener {
                if (!isShowingFavorites) viewModel.loadGames()
                else viewModel.loadFavorites()
            }
        }

        videogamesAdapter.apply {
            setOnItemClickListener { videogame ->
                viewModel.toggleFavorite(videogame, isShowingFavorites)
            }
            setOnDetailClickListener { videogame ->
                navigateToVideogameDetail(videogame.id)
            }
            setOnShareClickListener { videogame, bitmap ->
                shareVideogame(videogame, bitmap)
            }
        }

        (requireActivity() as MainActivity)
            .findViewById<MaterialToolbar>(R.id.toolbar)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.loadGames()
        setupMenu()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_menu, menu)

                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
                searchView.queryHint = getString(R.string.search_hint)

                searchView.setOnQueryTextListener(object :
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        filterList(query)
                        return true
                    }
                    override fun onQueryTextChange(newText: String): Boolean {
                        filterList(newText)
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_save -> {
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->

            if (binding.swiperefresh.isRefreshing) {
                binding.swiperefresh.isRefreshing = uiState.isLoading
            }

            if (uiState.isLoading) {
                skeleton.showSkeleton()
            } else {
                skeleton.showOriginal()
            }

            uiState.errorApp?.let { error ->
                val errorUI = errorFactory.build(error)
                binding.errorApp.render(errorUI)
            } ?: run {
                val list = uiState.videogames
                when {
                    list == null -> {
                        binding.errorApp.hide()
                    }

                    list.isEmpty() -> {
                        val emptyUI = errorFactory.build(ErrorApp.DataExpiredError)
                        binding.errorApp.render(emptyUI)
                    }

                    else -> {
                        binding.errorApp.hide()
                        uiState.videogames.let { list ->
                            allVideogames = list
                            videogamesAdapter.submitList(list)
                        }
                    }
                }
            }
        }
    }


    private fun navigateToVideogameDetail(videogameId: Int) {
        findNavController().navigate(
            VideogamesFragmentDirections.actionVideogamesToVideogamesDetail(videogameId)
        )
    }

    private fun shareVideogame(videoGame: Videogame, bmp: Bitmap?) {
        val text = "${videoGame.name}\n${videoGame.released}"
        if (bmp != null) {
            File(requireContext().cacheDir, "shared_images").apply { mkdirs() }
                .resolve("share_${videoGame.id}.png")
                .also { file ->
                    FileOutputStream(file).use { out ->
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, out)
                    }
                }
                .let { file ->
                    FileProvider.getUriForFile(
                        requireContext(),
                        "${requireContext().packageName}.fileprovider",
                        file
                    )
                }
                .also { uri ->
                    contentShare.shareContentWithImage(videoGame.name, text, uri)
                }
        } else {
            contentShare.shareContent(videoGame.name, text)
        }
    }

    private fun filterList(text: String) {
        val filtered = if (text.isBlank()) {
            allVideogames
        } else {
            allVideogames.filter {
                it.videogame.name.contains(text.trim(), ignoreCase = true)
            }
        }
        videogamesAdapter.submitList(filtered)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
