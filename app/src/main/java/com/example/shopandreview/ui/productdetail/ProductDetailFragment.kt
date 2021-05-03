package com.example.shopandreview.ui.productdetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import com.example.shopandreview.R
import com.example.shopandreview.databinding.FragmentProductDetailBinding
import com.example.shopandreview.ui.RatingDialogFragment.Companion.RATING_KEY
import com.example.shopandreview.util.NetworkStatus
import com.example.shopandreview.util.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private val binding by viewBinding(FragmentProductDetailBinding::bind)

    private val viewModel by viewModels<ProductDetailViewModel>()

    private val args by navArgs<ProductDetailFragmentArgs>()

    private val productDetailAdapter = ProductDetailAdapter()

    private val reviewAdapter = ReviewAdapter()

    private val pageAdapter = ConcatAdapter(productDetailAdapter, reviewAdapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(RATING_KEY) { requestKey, bundle ->
            viewModel.postReview(bundle.getParcelable(requestKey)!!)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productDetailAdapter.product = args.product.product
        binding.pageList.adapter = pageAdapter
        binding.addReview.setOnClickListener {
            findNavController().navigate(
                ProductDetailFragmentDirections.actionProductDetailFragmentToRatingDialogFragment(
                    args.product.product
                )
            )
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        setUpObservers()
        viewModel.getReviews(args.product.product.id)

    }

    private fun setUpObservers() {
        viewModel.reviewsFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach {
            reviewAdapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.reviewsPostResult.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach {
            when (it) {
                is NetworkStatus.Error -> {
                    hideLoading()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                is NetworkStatus.Loading -> {
                    showLoading()
                }
                is NetworkStatus.Success -> {
                    hideLoading()
                    Toast.makeText(requireContext(), "Review added successfully", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun hideLoading() {

    }

    private fun showLoading() {

    }


}