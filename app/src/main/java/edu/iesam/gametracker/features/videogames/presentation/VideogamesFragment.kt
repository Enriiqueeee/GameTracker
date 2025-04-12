package edu.iesam.gametracker.features.videogames.presentation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import edu.iesam.gametracker.MainActivity
import edu.iesam.gametracker.R
import edu.iesam.gametracker.databinding.FragmentVideogamesBinding
import edu.iesam.gametracker.features.videogames.domain.Videogame
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideogamesFragment : Fragment() {

    private var _binding: FragmentVideogamesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VideogamesViewModel by viewModel()

    private var isShowingFavorites = false
    private val videogamesAdapter: VideogamesAdapter = VideogamesAdapter()

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
        videogamesAdapter.setOnItemClickListener { videGame ->
            viewModel.toggleFavorite(videGame, isShowingFavorites)
        }
        binding.videogameList.adapter = videogamesAdapter
        (requireActivity() as MainActivity).findViewById<MaterialToolbar>(R.id.toolbar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.loadGames()
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner, Observer { uiState ->
            uiState.videogames?.let { videogames ->
                videogamesAdapter.submitList(videogames)
            }
            uiState.errorApp?.let {

            }
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
                    viewModel.loadGames()
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
