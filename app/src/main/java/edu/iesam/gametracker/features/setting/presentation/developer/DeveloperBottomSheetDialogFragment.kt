package edu.iesam.gametracker.features.setting.presentation.developer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import edu.iesam.gametracker.databinding.FragmentBottomSheetDeveloperBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeveloperBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetDeveloperBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DeveloperViewModel by viewModel()
    private val developerAdapter by lazy {
        DeveloperAdapter {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetDeveloperBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadDevelopers()
        setUpObservers()
        setUpView()
    }

    private fun setUpView() {
        binding.apply {
            recyclerViewDev.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = developerAdapter
            }
        }
    }

    private fun setUpObservers() {
        val observer = Observer<DeveloperViewModel.UiState> { uiState ->
            uiState.developers?.let {
                developerAdapter.submitList(it)
            }
            uiState.errorApp?.let {
                Log.d("@dev", "Error")
            }
            if (uiState.isLoading) {

                Log.d("@dev", "Cargando ...")
            } else {

                Log.d("@dev", "Cargando ...")
            }
        }
        viewModel.uiState.observe(viewLifecycleOwner, observer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}