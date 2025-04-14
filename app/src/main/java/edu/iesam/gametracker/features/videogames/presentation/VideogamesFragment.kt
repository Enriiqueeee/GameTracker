package edu.iesam.gametracker.features.videogames.presentation

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.android.material.appbar.MaterialToolbar
import edu.iesam.gametracker.MainActivity
import edu.iesam.gametracker.R
import edu.iesam.gametracker.databinding.FragmentVideogamesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideogamesFragment : Fragment() {

    private var _binding: FragmentVideogamesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VideogamesViewModel by viewModel()

    private var isShowingFavorites = false
    private val videogamesAdapter: VideogamesAdapter = VideogamesAdapter()

    private lateinit var skeleton: Skeleton

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
        }
        videogamesAdapter.setOnItemClickListener { videGame ->
            viewModel.toggleFavorite(videGame, isShowingFavorites)
        }
        videogamesAdapter.setOnDetailClickListener { videogame ->
            navigateToVideogameDetail(videogame.id)
        }
        (requireActivity() as MainActivity).findViewById<MaterialToolbar>(R.id.toolbar)
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
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_save -> {
                        if (!isShowingFavorites) {
                            isShowingFavorites = true
                            menuItem.setIcon(R.drawable.ic_favorite_click)
                            viewModel.loadFavorites()
                        } else {
                            isShowingFavorites = false
                            menuItem.setIcon(R.drawable.ic_save)
                            viewModel.loadGames()
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner, Observer { uiState ->
            if (uiState.isLoading) {
                skeleton.showSkeleton()
            } else {
                skeleton.showOriginal()
            }
            uiState.videogames?.let { videogames ->
                videogamesAdapter.submitList(videogames)
            }
            uiState.errorApp?.let {
            }
        })
    }

    private fun navigateToVideogameDetail(videogameId: Int) {
        findNavController().navigate(
            VideogamesFragmentDirections.actionVideogamesToVideogamesDetail(videogameId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
