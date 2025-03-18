package edu.iesam.gametracker.features.videogames.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import edu.iesam.gametracker.app.extensions.loadUrl
import edu.iesam.gametracker.databinding.FragmentVideogameDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideogameDetailFragment : Fragment() {

    private var _binding: FragmentVideogameDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VideogameDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideogameDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videogameId = arguments?.getInt("videogame_id") ?: return
        viewModel.loadVideogameDetail(videogameId)

        setupObserver()
    }

    private fun setupObserver() {
        val videogameObserver = Observer<VideogameDetailViewModel.UiState> { uiState ->
            uiState.videogame?.let { videogame ->
                binding.apply {
                    imageDetail.loadUrl(videogame.backgroundImage)
                    nameGame.text = videogame.name
                    released.text = videogame.released
                    rating.text = videogame.rating.toString()
                }
            }
            uiState.errorApp?.let {
            }
        }
        viewModel.uiState.observe(viewLifecycleOwner, videogameObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
