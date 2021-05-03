package com.example.shopandreview.ui.products

import androidx.lifecycle.ViewModel
import com.example.shopandreview.repository.ProductRepository
import com.example.shopandreview.util.DispatcherProvider
import com.example.shopandreview.util.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val mutableProductsFlow = MutableSharedFlow<Unit>(1)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val productFlow = mutableProductsFlow.flatMapLatest {
        repository.getProducts()
    }
    private val queryFlow = MutableStateFlow("")
    val filteredFlow = queryFlow.combine(productFlow) { query, response ->
        return@combine when (response) {
            is NetworkStatus.Error -> response.copy(
                data = response.data.orEmpty().filter { productWithReviews ->
                    productWithReviews.product.name.contains(
                        query,
                        true
                    ) || productWithReviews.product.description.contains(query, true)
                })
            is NetworkStatus.Loading -> response.copy(
                data = response.data.orEmpty().filter { productWithReviews ->
                    productWithReviews.product.name.contains(
                        query,
                        true
                    ) || productWithReviews.product.description.contains(query, true)
                })
            is NetworkStatus.Success -> response.copy(data = response.data.filter { productWithReviews ->
                productWithReviews.product.name.contains(
                    query,
                    true
                ) || productWithReviews.product.description.contains(query, true)
            })
        }
    }.flowOn(dispatcherProvider.io())

    fun searchProduct(query: String) {
        queryFlow.value = query
    }

    fun getProducts() {
        mutableProductsFlow.tryEmit(Unit)
    }
}