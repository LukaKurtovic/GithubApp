package com.example.githubapp.ui.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.githubapp.databinding.FragmentWebviewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WebViewFragment : Fragment() {

    private var _binding: FragmentWebviewBinding? = null
    private val binding get() = _binding!!
    private val args: WebViewFragmentArgs by navArgs()
    private val viewModel by viewModel<WebViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()

        binding.toolbar.apply {
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }

        viewModel.openPage(args)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.webViewEvents.collect { event ->
                when (event) {
                    is WebViewModel.WebViewEvents.LaunchWebView -> {
                        binding.webview.loadUrl(event.url)
                    }
                }
            }
        }
    }

    private fun initUi() {
        binding.tvOwner.text = args.repoInfo.owner.login
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

