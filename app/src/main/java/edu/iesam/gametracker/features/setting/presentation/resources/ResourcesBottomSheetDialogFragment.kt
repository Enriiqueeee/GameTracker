package edu.iesam.gametracker.features.setting.presentation.resources

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import edu.iesam.gametracker.databinding.FragmentBottomSheetResourcesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class ResourcesBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetResourcesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ResourcesViewmodel by viewModel()
    private val resourceAdapter = ResourcesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetResourcesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadResources()
        setUpView()
        setUpObserver()
    }

    private fun setUpObserver() {
        val observer = Observer<ResourcesViewmodel.UiState> { uiState ->
            uiState.resources?.let {
                resourceAdapter.submitList(it)
                Log.d("@Dev", "Resources loaded: ${it.size}")
            }
            uiState.errorApp?.let {

            }
        }
        viewModel.uiState.observe(viewLifecycleOwner, observer)
    }

    private fun setUpView() {
        binding.apply {
            recyclerViewRes.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = resourceAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}