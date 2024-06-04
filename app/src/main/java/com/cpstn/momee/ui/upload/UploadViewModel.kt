package com.cpstn.momee.ui.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpstn.momee.data.domain.ProductsSearchByImageDomain
import com.cpstn.momee.network.Result
import com.cpstn.momee.network.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(private val productsRepository: ProductsRepository) : ViewModel() {

    private  var _uploadResult: MutableLiveData<Result<ProductsSearchByImageDomain.Result>> = MutableLiveData()
    val uploadResult : LiveData<Result<ProductsSearchByImageDomain.Result>> = _uploadResult

    fun uploadImage(image: MultipartBody.Part) {
        viewModelScope.launch {
            productsRepository.searchByImage(image).collect {
                _uploadResult.value = it
            }
        }
    }
}