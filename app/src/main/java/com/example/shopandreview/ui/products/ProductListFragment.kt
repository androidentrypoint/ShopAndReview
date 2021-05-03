package com.example.shopandreview.ui.products

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.shopandreview.R
import com.example.shopandreview.databinding.FragmentProductListBinding
import com.example.shopandreview.util.NetworkStatus
import com.example.shopandreview.util.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProductListFragment : Fragment(R.layout.fragment_product_list) {

    private val viewModel by viewModels<ProductListViewModel>()

    private val binding by viewBinding(FragmentProductListBinding::bind)

    private val adapter = ProductAdapter {
        findNavController().navigate(
            ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(
                it
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productList.adapter = adapter
        binding.search.doAfterTextChanged { query ->
            viewModel.searchProduct(query.toString())
        }
        binding.swipe.setOnRefreshListener {
            viewModel.getProducts()
        }
        viewModel.getProducts()
        viewModel.filteredFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach {
            when (it) {
                is NetworkStatus.Error -> {
                    binding.swipe.isRefreshing = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                is NetworkStatus.Loading -> {
                    binding.swipe.isRefreshing = true
                    adapter.submitList(it.data)
                }
                is NetworkStatus.Success -> {
                    binding.swipe.isRefreshing = false
                    adapter.submitList(it.data)
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}