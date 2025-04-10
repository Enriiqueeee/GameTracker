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

    // Bandera para controlar el estado de la vista: completa o favoritos.
    private var isShowingFavorites = false
    // Se guarda la lista completa para poder volver a ella cuando se desactive el modo favoritos.
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
        // Permite al fragmento gestionar su propio menú (toolbar)
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
        // Observer para la lista completa de videojuegos.
        viewModel.uiState.observe(viewLifecycleOwner, Observer { uiState ->
            uiState.videogames?.let { videogames ->
                fullVideogamesList = videogames
                if (!isShowingFavorites) {
                    videogamesAdapter.submitList(videogames)
                }
            }
            uiState.errorApp?.let {
                // Aquí podrías manejar errores si fuese necesario.
            }
        })

        // Observer para la lista de favoritos (obtenida desde Room).
        viewModel.favorites.observe(viewLifecycleOwner, Observer { favorites ->
            if (isShowingFavorites) {
                videogamesAdapter.submitList(favorites)
            }
            // Actualiza el estado de los íconos de favoritos en cada elemento.
            val favoriteIds = favorites.map { it.id }.toSet()
            videogamesAdapter.updateFavoriteIds(favoriteIds)
        })
    }

    // Infla el menú de la toolbar a partir del recurso XML (en este caso, res/menu/toolbar_menu.xml)
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    // Al pulsar el botón (con id action_save) se alterna la vista:
    // - Si NO se muestran los favoritos, activa ese modo, cambia el ícono y carga los favoritos.
    // - Si ya se muestran los favoritos, vuelve a la lista completa y restablece el ícono.
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
