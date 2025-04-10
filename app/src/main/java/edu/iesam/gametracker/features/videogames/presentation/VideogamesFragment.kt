package edu.iesam.gametracker.features.videogames.presentation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.iesam.gametracker.R
import edu.iesam.gametracker.databinding.FragmentVideogamesBinding
import edu.iesam.gametracker.features.videogames.domain.Videogame
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideogamesFragment : Fragment() {

    private var _binding: FragmentVideogamesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VideogamesViewModel by viewModel()

    private var isShowingFavorites = false
    private var fullVideogamesList: List<Videogame>? = null

    private val videogamesAdapter by lazy {
        VideogamesAdapter(favoriteIds = emptySet(), onFavoriteToggle = { videogame, newState ->
            viewModel.toggleFavorite(videogame)
        }).apply {
            setOnItemClickListener { videogameId ->
                navigateToVideogameDetail(videogameId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideogamesBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
        binding.videogameList.layoutManager = LinearLayoutManager(requireContext())
        binding.videogameList.adapter = videogamesAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.videogamesCreated()
        viewModel.loadFavorites()
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner, Observer { uiState ->
            uiState.videogames?.let { videogames ->
                fullVideogamesList = videogames
                if (!isShowingFavorites) {
                    videogamesAdapter.submitList(videogames)
                }
            }
            uiState.errorApp?.let {
            }
        })

        viewModel.favorites.observe(viewLifecycleOwner, Observer { favorites ->
            if (isShowingFavorites) {
                videogamesAdapter.submitList(favorites)
            }
            val favoriteIds = favorites.map { it.id }.toSet()
            videogamesAdapter.updateFavoriteIds(favoriteIds)
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                if (!isShowingFavorites) {
                    isShowingFavorites = true
                    item.setIcon(R.drawable.ic_favorite_click)
                    viewModel.loadFavorites()
                } else {
                    isShowingFavorites = false
                    item.setIcon(R.drawable.ic_save)
                    fullVideogamesList?.let { videogamesAdapter.submitList(it) }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
