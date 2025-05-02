package edu.iesam.gametracker.features.recommend.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import edu.iesam.gametracker.R
import edu.iesam.gametracker.app.domain.ErrorApp
import edu.iesam.gametracker.app.presentation.ContentShare
import edu.iesam.gametracker.app.presentation.hide
import edu.iesam.gametracker.app.presentation.views.ErrorAppUIFactory
import edu.iesam.gametracker.databinding.FragmentRecommendedBinding
import edu.iesam.gametracker.features.videogames.presentation.VideogamesAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecommendedFragment : Fragment() {

    private var _binding: FragmentRecommendedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecommendedViewModel by viewModel()
    private val videogamesAdapter: VideogamesAdapter = VideogamesAdapter()
    private lateinit var skeleton: Skeleton
    private val errorFactory: ErrorAppUIFactory by inject()
    private val contentShare: ContentShare by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendedBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
        binding.apply {
            videogameList.layoutManager = LinearLayoutManager(requireContext())
            videogameList.adapter = videogamesAdapter

            skeleton = videogameList.applySkeleton(R.layout.view_videogames_item, 8)

            swiperefresh.setOnRefreshListener {
                viewModel.loadRecommendations()
            }

            videogamesAdapter.apply {
                setOnShareClickListener { videoGame, bitmap ->
                    contentShare.shareVideogame(videoGame, bitmap)
                }
            }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.loadRecommendations()
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
                        videogamesAdapter.submitList(list)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}