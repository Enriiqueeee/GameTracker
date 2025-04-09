package edu.iesam.gametracker.features.videogames.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.iesam.gametracker.databinding.FragmentVideogamesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideogamesFragment : Fragment() {

    private var _binding: FragmentVideogamesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VideogamesViewModel by viewModel()

    private val videogamesAdapter by lazy {
        VideogamesAdapter(favoriteIds = emptySet(), onFavoriteToggle = { videogame, newState ->
            viewModel.toggleFavorite(videogame)
        }).apply {
            setOnItemClickListener { videogameId ->
                navigateToVideogameDetail(videogameId)
            }
        }
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
        binding.apply {
            videogameList.layoutManager = LinearLayoutManager(requireContext())
            videogameList.adapter = videogamesAdapter
        }
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
                videogamesAdapter.submitList(videogames)
            }
            uiState.errorApp?.let {
                // Manejar error...
            }
        })

        viewModel.favorites.observe(viewLifecycleOwner, Observer { favorites ->
            val favoriteIds = favorites.map { it.id }.toSet()
            videogamesAdapter.updateFavoriteIds(favoriteIds)
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
