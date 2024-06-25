package com.cpstn.momee.ui.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpstn.momee.data.domain.ProductCategoryDomain
import com.cpstn.momee.data.domain.ProductDomain
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.network.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productCatRepository: ProductsRepository) :ViewModel() {

    private var _categoryResult: MutableLiveData<DataResult<ProductCategoryDomain>> = MutableLiveData()
    val categoryResult: LiveData<DataResult<ProductCategoryDomain>> = _categoryResult

    private var _getAllProductResult: MutableLiveData<DataResult<ProductDomain>> = MutableLiveData()
    val getAllProductResult: LiveData<DataResult<ProductDomain>> = _getAllProductResult

    fun getCategory() {
        viewModelScope.launch {
            productCatRepository.getCategoryProduct().collect {
                _categoryResult.value = it
            }
        }
    }

    fun getAllProduct() {
        viewModelScope.launch {
            productCatRepository.getAllProduct().collect {
                _getAllProductResult.value = it
            }
        }
    }
}