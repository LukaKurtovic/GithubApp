package com.example.githubapp.ui.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.R
import com.example.githubapp.data.model.GithubRepository
import com.example.githubapp.databinding.FragmentResultsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ResultsFragment : Fragment(), ResultsAdapter.OnItemClickListener {

    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!
    private val resultsAdapter = ResultsAdapter(this)
    private val viewModel by viewModel<ResultsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.progressBar.isVisible = state.isLoading
            viewModel.showResult(state)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.resultEvents.collect { event ->
                when (event) {
                    is ResultsViewModel.ResultEvents.ShowErrorMessage -> {
                        binding.tvTotal.text = event.message
                    }
                    is ResultsViewModel.ResultEvents.ShowRepositories -> {
                        binding.apply {
                            tvTotal.text = getString(
                                R.string.number_of_repositories,
                                event.response.totalCountFormatted
                            )
                            resultsAdapter.submitList(event.response.items)
                        }
                    }
                }
            }
        }
    }

    private fun initUI() {
        binding.recyclerView.apply {
            adapter = resultsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onItemClick(repo: GithubRepository) {
        val action = ResultsFragmentDirections.actionResultsFragmentToDetailsFragment(repo)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}