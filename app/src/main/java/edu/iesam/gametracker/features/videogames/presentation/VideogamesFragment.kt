package edu.iesam.gametracker.features.videogames.presentation

import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
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
    private lateinit var skeleton: Skeleton
    private val contentShare: ContentShare by inject()
    private val errorFactory: ErrorAppUIFactory by inject()
    private val videogamesAdapter = VideogamesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
                if (isShowingFavorites) viewModel.loadFavorites() else viewModel.loadGames()
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
                val searchView = searchItem.actionView as SearchView
                searchView.queryHint = getString(R.string.search_hint)
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        viewModel.searchGames(query.trim())
                        searchView.clearFocus()
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        val q = newText.trim()
                        if (q.isBlank()) viewModel.loadGames() else viewModel.searchGames(q)
                        return true
                    }
                })

                val favItem = menu.findItem(R.id.action_save)
                updateFavIcon(favItem)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_save -> {
                        isShowingFavorites = !isShowingFavorites
                        if (isShowingFavorites) viewModel.loadFavorites() else viewModel.loadGames()
                        updateFavIcon(menuItem)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun updateFavIcon(item: MenuItem) {
        val iconRes = if (isShowingFavorites) R.drawable.ic_favorite_click else R.drawable.ic_save
        item.icon = ContextCompat.getDrawable(requireContext(), iconRes)
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding.swiperefresh.isRefreshing = uiState.isLoading
            if (uiState.isLoading) {
                skeleton.showSkeleton()
                binding.errorApp.hide()
                return@observe
            } else {
                skeleton.showOriginal()
            }

            uiState.errorApp?.let { error ->
                binding.errorApp.render(errorFactory.build(error))
                return@observe
            }

            val list = uiState.videogames.orEmpty()
            if (list.isEmpty()) {
                binding.errorApp.render(errorFactory.build(ErrorApp.DataExpiredError))
            } else {
                binding.errorApp.hide()
                videogamesAdapter.submitList(list)
            }
        }
    }


    private fun navigateToVideogameDetail(videogameId: Int) {
        findNavController().navigate(
            VideogamesFragmentDirections.actionVideogamesToVideogamesDetail(videogameId)
        )
    }

    private fun shareVideogame(videoGame: Videogame, bmp: Bitmap?) {
        val text = "${'$'}{videoGame.name}\n${'$'}{videoGame.released}"
        if (bmp != null) {
            File(requireContext().cacheDir, "shared_images").apply { mkdirs() }
                .resolve("share_${'$'}{videoGame.id}.png")
                .also { file ->
                    FileOutputStream(file).use { out ->
                        bmp.compress(
                            Bitmap.CompressFormat.PNG,
                            100,
                            out
                        )
                    }
                }
                .let { file ->
                    FileProvider.getUriForFile(
                        requireContext(),
                        "${'$'}{requireContext().packageName}.fileprovider",
                        file
                    )
                }
                .also { uri -> contentShare.shareContentWithImage(videoGame.name, text, uri) }
        } else {
            contentShare.shareContent(videoGame.name, text)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (requireActivity() as MainActivity)
            .findViewById<MaterialToolbar>(R.id.toolbar)
            .menu
            .clear()
    }
}