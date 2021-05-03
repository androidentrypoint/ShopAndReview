package com.example.shopandreview.ui.productdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopandreview.R
import com.example.shopandreview.databinding.ItemProductDetailLayoutBinding
import com.example.shopandreview.model.Product

class ProductDetailAdapter : RecyclerView.Adapter<ProductDetailAdapter.ViewHolder>() {

    lateinit var product: Product


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductDetailLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 1

    inner class ViewHolder(private val binding: ItemProductDetailLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            if (::product.isInitialized.not()) return
            with(binding) {
                name.text = product.name
                price.text = root.context.getString(R.string.price, product.currency, product.price)
                description.text = product.description
                Glide.with(productImg).load(product.imgUrl).into(productImg)
            }
        }
    }
}