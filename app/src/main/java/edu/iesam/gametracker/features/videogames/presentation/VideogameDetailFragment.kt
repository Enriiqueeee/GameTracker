package edu.iesam.gametracker.features.videogames.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import edu.iesam.gametracker.R
import edu.iesam.gametracker.app.extensions.loadUrl
import edu.iesam.gametracker.databinding.FragmentVideogameDetailBinding
import edu.iesam.gametracker.features.videogames.domain.Videogame
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideogameDetailFragment : Fragment() {

    private var _binding: FragmentVideogameDetailBinding? = null
    private val binding get() = _binding!!

    private val videogameDetailviewModel: VideogameDetailViewModel by viewModel()
    private val args: VideogameDetailFragmentArgs by navArgs()

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
        setupObserver()
        val videogameId = args.videogameId
        videogameDetailviewModel.loadVideogameDetail(videogameId)
    }

    private fun setupObserver() {
        val videogameObserver = Observer<VideogameDetailViewModel.UiState> { uiState ->
            uiState.videogame?.let { videogame ->
                updateVideogameDetail(videogame)
            }
            uiState.errorApp?.let {
            }
        }
        videogameDetailviewModel.uiState.observe(viewLifecycleOwner, videogameObserver)
    }

    private fun updateVideogameDetail(videogame: Videogame) {
        binding.apply {
            imageDetail.loadUrl(videogame.backgroundImage)
            nameGame.text = videogame.name
            playtime.text = root.context.getString(
                R.string.playtime,
                videogame.playtime.toString()
            )
            released.text = root.context.getString(
                R.string.released_date,
                videogame.released
            )
            description.text = videogame.description.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
