package com.example.shopandreview.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopandreview.R
import com.example.shopandreview.databinding.ItemProductLayoutBinding
import com.example.shopandreview.model.ProductWithReviews

class ProductAdapter(private val onProductClicked: (ProductWithReviews) -> Unit) :
    ListAdapter<ProductWithReviews, ProductAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemProductLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onProductClicked(getItem(bindingAdapterPosition))
            }
        }

        fun bind(product: ProductWithReviews) {
            with(product.product) {
                binding.name.text = name
                binding.description.text = description
                binding.price.text = binding.root.context.getString(R.string.price, currency, price)
                Glide.with(binding.productImg).load(imgUrl).into(binding.productImg)
            }

        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ProductWithReviews>() {
            override fun areItemsTheSame(
                oldItem: ProductWithReviews,
                newItem: ProductWithReviews
            ): Boolean {
                return oldItem.product.id == newItem.product.id
            }

            override fun areContentsTheSame(
                oldItem: ProductWithReviews,
                newItem: ProductWithReviews
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}