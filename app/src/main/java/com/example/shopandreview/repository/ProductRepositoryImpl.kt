package com.example.shopandreview.repository

import com.example.shopandreview.model.ProductWithReviews
import com.example.shopandreview.network.NetworkProcessor
import com.example.shopandreview.network.ProductService
import com.example.shopandreview.network.model.ProductResponseDTO
import com.example.shopandreview.room.dao.ProductDao
import com.example.shopandreview.room.relation.ProductWithReviewsRelation
import com.example.shopandreview.util.DispatcherProvider
import com.example.shopandreview.util.NetworkStatus
import com.example.shopandreview.util.mapper.Mapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val productDao: ProductDao,
    private val productMapper: Mapper<ProductWithReviewsRelation, ProductWithReviews>,
    private val productDTOMapper: Mapper<ProductResponseDTO, ProductWithReviewsRelation>,
    private val dispatcherProvider: DispatcherProvider,
    private val networkProcessor: NetworkProcessor
) : ProductRepository, NetworkProcessor by networkProcessor,
    DispatcherProvider by dispatcherProvider {


    override fun getProducts(): Flow<NetworkStatus<List<ProductWithReviews>>> {
        return flow {
            emit(
                NetworkStatus.Loading(
                    productMapper.mapList(
                        productDao.getAllProductsAsFlow().first()
                    )
                )
            )
            when (val response = processNetworkResponse { productService.getProducts() }) {
                is NetworkStatus.Success -> {
                    productDao.insertProductsWithReviews(productDTOMapper.mapList(response.data))
                    emitAll(
                        productDao.getAllProductsAsFlow()
                            .map { NetworkStatus.Success(productMapper.mapList(it)) })
                }
                is NetworkStatus.Error -> {
                    emit(
                        NetworkStatus.Error(
                            response.message,
                            productMapper.mapList(productDao.getAllProductsAsFlow().first())
                        )
                    )
                }
            }
        }.flowOn(io())
    }


}