package edu.iesam.gametracker.features.videogames.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import edu.iesam.gametracker.databinding.FragmentVideogamesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class VideogamesFragment() : Fragment() {

    private var _binding: FragmentVideogamesBinding? = null
    private val binding get() = _binding!!

    private val videogamesAdapter = VideogamesAdapter()

    private val viewModel: VideogamesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVideogamesBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView(){
        binding.apply {
            videogameList.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            videogameList.adapter = videogamesAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        viewModel.videogamesCreated()
    }

    private fun setupObserver() {
        val videogameObserver = Observer<VideogamesViewModel.UiState> { uiState ->
            uiState.videogame?.let {
                videogamesAdapter.submitList(it)
            }
            uiState.errorApp?.let {
                //pinto el error
            } ?: kotlin.run {
                //ocultar error
            }
            if (uiState.isLoading) {
                Log.d("@dev", "Cargando...")
            } else {
                Log.d("@dev", "Cargado")
            }
        }
        viewModel.uiState.observe(viewLifecycleOwner, videogameObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}