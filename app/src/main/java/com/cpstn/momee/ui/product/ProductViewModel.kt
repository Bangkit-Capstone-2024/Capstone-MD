package com.cpstn.momee.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpstn.momee.data.domain.ProductDetailDomain
import com.cpstn.momee.data.domain.ProductDomain
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.network.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductsRepository) : ViewModel() {

    private var _getAllProductResult: MutableLiveData<DataResult<ProductDomain>> = MutableLiveData()
    val getAllProductResult: LiveData<DataResult<ProductDomain>> = _getAllProductResult

    private var _searchProductResult: MutableLiveData<DataResult<ProductDomain>> = MutableLiveData()
    val searchProductResult: LiveData<DataResult<ProductDomain>> = _searchProductResult

    private var _getDetailProduct: MutableLiveData<DataResult<ProductDetailDomain>> = MutableLiveData()
    val getDetailProduct: LiveData<DataResult<ProductDetailDomain>> = _getDetailProduct

    fun getAllProduct() {
        viewModelScope.launch {
            productRepository.getAllProduct().collect {
                _getAllProductResult.value = it
            }
        }
    }

    fun getDetailProduct(productId: Int) {
        viewModelScope.launch {
            productRepository.productDetail(productId).collect {
                _getDetailProduct.value = it
            }
        }
    }

    fun searchProduct(query: String) {
        viewModelScope.launch {
            productRepository.searchProduct(query).collect {
                _searchProductResult.value = it
            }
        }
    }

}