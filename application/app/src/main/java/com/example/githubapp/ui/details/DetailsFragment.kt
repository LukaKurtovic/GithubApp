package com.example.githubapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel by viewModel<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.apply {
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }

        binding.btnProfile.setOnClickListener {
            viewModel.onProfileClick()
        }

        binding.btnRepository.setOnClickListener {
            viewModel.onRepositoriesClick()
        }

        initUI()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.detailsEvents.collect { event ->
                when (event) {
                    is DetailsViewModel.DetailsEvents.NavigateToProfile -> {
                        val action =
                            DetailsFragmentDirections.actionDetailsFragmentToWebViewFragment(
                                args.repoInfo,
                                PROFILE
                            )
                        findNavController().navigate(action)
                    }
                    is DetailsViewModel.DetailsEvents.NavigateToRepositories -> {
                        val action =
                            DetailsFragmentDirections.actionDetailsFragmentToWebViewFragment(
                                args.repoInfo,
                                REPOSITORY
                            )
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    private fun initUI() {
        binding.apply {
            tvRepoName.text = args.repoInfo.name
            tvUsername.text = args.repoInfo.owner.login
            tvDescription.text = args.repoInfo.description
            tvIssues.text = args.repoInfo.open_issues_count.toString()
            tvWatchers.text = args.repoInfo.watchers_count.toString()
            tvStars.text = args.repoInfo.stargazers_count.toString()
            tvIsPrivate.text = isPrivate(args.repoInfo.private)
            ivAvatar.load(args.repoInfo.owner.avatar_url)
        }
    }

    private fun isPrivate(isPrivate: Boolean): String {
        return if (!isPrivate) getString(R.string.private_no)
        else getString(R.string.private_yes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private const val PROFILE = "profile"
private const val REPOSITORY = "repository"