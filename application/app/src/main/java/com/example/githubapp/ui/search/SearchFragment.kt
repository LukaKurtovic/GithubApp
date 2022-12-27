package com.example.githubapp.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.githubapp.R
import com.example.githubapp.data.model.RecentSearch
import com.example.githubapp.databinding.FragmentSearchBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<SearchViewModel>()
    private lateinit var popup: PopupMenu

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgMenu.setOnClickListener {
            popup = PopupMenu(requireContext(), binding.imgMenu)
            popup.inflate(R.menu.menu)
            popup.setOnMenuItemClickListener {
                viewModel.onDeleteHistoryClick()
                true
            }
            popup.show()
        }

        binding.btnSearch.setOnClickListener {
            viewModel.onSearchClicked()
        }

        viewModel.suggestions.observe(viewLifecycleOwner) { list ->
            updateSuggestions(list)
        }


        binding.editText.apply {

            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.query.value = p0.toString()
                    binding.btnSearch.isEnabled = viewModel.isQueryValid()
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })

        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.searchEvents.collect { event ->
                when (event) {
                    is SearchViewModel.SearchEvents.ShowDeleteHistoryDialog -> {
                        MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                            .setTitle(getString(R.string.dialog_title))
                            .setMessage(getString(R.string.dialog_message))
                            .setNegativeButton(getString(R.string.cancel_button), null)
                            .setPositiveButton(getString(R.string.yes_button)) { _, _ ->
                                viewModel.onConfirmClick()
                                Toast.makeText(
                                    requireContext(),
                                    getText(R.string.history_deleted_message),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .create().show()
                    }
                    is SearchViewModel.SearchEvents.NavigateToDetailsScreen -> {
                        val action =
                            SearchFragmentDirections.actionSearchFragmentToResultsFragment(event.query)
                        findNavController().navigate(action)

                    }
                }
            }
        }
    }

    private fun updateSuggestions(list: List<RecentSearch>?) {
        val adapter = ArrayAdapter(requireContext(), R.layout.search_suggestion, list!!.toTypedArray())
        binding.editText.setAdapter(adapter)
    }

    override fun onResume() {
        super.onResume()
        binding.editText.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}