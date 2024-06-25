package com.cpstn.momee.ui.account.tenant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpstn.momee.data.domain.ProductCategoryDomain
import com.cpstn.momee.data.domain.SubmitProductDomain
import com.cpstn.momee.data.domain.SubmitTenantDomain
import com.cpstn.momee.data.domain.TenantDetailDomain
import com.cpstn.momee.data.domain.TenantDomain
import com.cpstn.momee.data.payload.ProductPayload
import com.cpstn.momee.data.payload.TenantPayload
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.network.repository.ProductsRepository
import com.cpstn.momee.network.repository.TenantRepository
import com.cpstn.momee.network.response.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class TenantViewModel @Inject constructor(
    private val tenantRepository: TenantRepository,
    private val productRepository: ProductsRepository
) :
    ViewModel() {

    private var _tenantResult: MutableLiveData<DataResult<TenantDomain>> = MutableLiveData()
    val tenantResult: LiveData<DataResult<TenantDomain>> = _tenantResult

    private var _tenantDetailResult: MutableLiveData<DataResult<TenantDetailDomain>> =
        MutableLiveData()
    val tenantDetailResult: LiveData<DataResult<TenantDetailDomain>> = _tenantDetailResult

    private var _submitTenantResult: MutableLiveData<DataResult<SubmitTenantDomain>> =
        MutableLiveData()
    val submitTenantResult: LiveData<DataResult<SubmitTenantDomain>> = _submitTenantResult

    private var _submitProductResult: MutableLiveData<DataResult<SubmitProductDomain>> =
        MutableLiveData()
    val submitProductResult: LiveData<DataResult<SubmitProductDomain>> = _submitProductResult

    private var _categoryResult: MutableLiveData<DataResult<ProductCategoryDomain>> =
        MutableLiveData()
    val categoryResult: LiveData<DataResult<ProductCategoryDomain>> = _categoryResult

    fun getCategory() {
        viewModelScope.launch {
            productRepository.getCategoryProduct().collect {
                _categoryResult.value = it
            }
        }
    }

    fun getTenant() {
        viewModelScope.launch {
            tenantRepository.getTenant().collect {
                _tenantResult.value = it
            }
        }
    }

    fun getDetailTenant(tenantId: Int) {
        viewModelScope.launch {
            tenantRepository.getDetailTenant(tenantId).collect {
                _tenantDetailResult.value = it
            }
        }
    }

    fun submitTenant(
        tenantId: Int? = null,
        name: RequestBody,
        address: RequestBody,
        phone: RequestBody,
        image: MultipartBody.Part?,
        isEdit: Boolean
    ) {
        viewModelScope.launch {
            if (!isEdit) {
                tenantRepository.createTenant(prepareTenantPayload(name, address, phone, image))
                    .collect {
                        _submitTenantResult.value = it
                    }
            } else {
                tenantRepository.updateTenant(
                    tenantId,
                    prepareTenantPayload(name, address, phone, image)
                )
                    .collect {
                        _submitTenantResult.value = it
                    }
            }
        }
    }

    fun submitProductTenant(
        tenantId: Int? = null,
        nameProducts: RequestBody,
        description: RequestBody,
        phone: RequestBody,
        stock: RequestBody,
        isAvailable: RequestBody,
        categoryId: RequestBody,
        file: MultipartBody.Part? = null,
        isEdit: Boolean
    ) {
        val submitPayload = ProductPayload(
            nameProducts,
            description,
            phone,
            stock,
            isAvailable,
            categoryId,
            tenantId.toString().toRequestBody(
                MediaType.TEXT.value.toMediaType()
            ),
            file
        )
        viewModelScope.launch {
            if (!isEdit) {
                productRepository.submitProduct(submitPayload)
                    .collect {
                        _submitProductResult.value = it
                    }
            } else {
                productRepository.updateProduct(
                    tenantId,
                    submitPayload
                )
                    .collect {
                        _submitProductResult.value = it
                    }
            }
        }
    }

    private fun prepareTenantPayload(
        name: RequestBody,
        address: RequestBody,
        phone: RequestBody,
        image: MultipartBody.Part?
    ): TenantPayload {
        return TenantPayload(name, address, phone, image)
    }
}