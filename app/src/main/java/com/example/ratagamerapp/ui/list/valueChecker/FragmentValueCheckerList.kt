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
import com.example.ratagamerapp.databinding.FragmentValueCheckerListBinding
import com.example.ratagamerapp.databinding.GiveawayListItemBinding
import com.example.ratagamerapp.ui.list.valueChecker.AdapterValueCheckerList
import com.example.ratagamerapp.ui.list.valueChecker.ViewModelValueCheckerList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentValueCheckerList : Fragment() {

    private lateinit var binding: FragmentValueCheckerListBinding
    private val viewModel: ViewModelValueCheckerList by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentValueCheckerListBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AdapterValueCheckerList(requireContext())
        val rv = binding.valueCheckerList
        rv.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { giveaways ->
                    giveaways?.let {
                        adapter.submitList(it)
                    }
                }
            }
        }
        binding.worthTitle.text=getString(R.string.total_worth)+"=$"+viewModel.totalWorth.value.toString()
    }


}