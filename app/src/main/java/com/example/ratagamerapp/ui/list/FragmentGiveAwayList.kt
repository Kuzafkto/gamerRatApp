    package com.example.ratagamerapp.ui.list

    import android.os.Binder
    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Button
    import androidx.fragment.app.viewModels
    import androidx.lifecycle.Lifecycle
    import androidx.lifecycle.lifecycleScope
    import androidx.lifecycle.repeatOnLifecycle
    import androidx.navigation.fragment.findNavController
    import com.example.ratagamerapp.R
    import com.example.ratagamerapp.databinding.FragmentGiveAwayListBinding
    import com.example.ratagamerapp.databinding.GiveawayListItemBinding
    import dagger.hilt.android.AndroidEntryPoint
    import kotlinx.coroutines.flow.collect
    import kotlinx.coroutines.launch

    @AndroidEntryPoint
    class FragmentGiveAwayList : Fragment() {

        private lateinit var binding: FragmentGiveAwayListBinding
        private val viewModel: ViewModelGiveAwayList by viewModels()

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentGiveAwayListBinding.inflate(
                inflater,
                container,
                false
            )
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val adapter = AdapterGiveawayList(requireContext(), viewModel,lifecycleScope)
            val rv = binding.giveawayList
            rv.adapter = adapter
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiState.collect {
                        adapter.submitList(it.giveaway)
                    }
                }
            }
        }


    }