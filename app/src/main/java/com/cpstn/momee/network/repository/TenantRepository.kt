package com.cpstn.momee.network.repository

import com.cpstn.momee.data.domain.SubmitTenantDomain
import com.cpstn.momee.data.domain.TenantDetailDomain
import com.cpstn.momee.data.domain.TenantDomain
import com.cpstn.momee.data.mapper.SubmitTenantMapper
import com.cpstn.momee.data.mapper.TenantDetailMapper
import com.cpstn.momee.data.mapper.TenantMapper
import com.cpstn.momee.data.payload.TenantPayload
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.network.datasource.TenantDataSource
import com.cpstn.momee.network.response.SubmitTenantResponse
import com.cpstn.momee.network.response.TenantDetailResponse
import com.cpstn.momee.network.response.TenantsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface TenantRepository {
    fun createTenant(tenantPayload: TenantPayload): Flow<DataResult<SubmitTenantDomain>>
    fun getTenant(): Flow<DataResult<TenantDomain>>
    fun getDetailTenant(tenantId: Int): Flow<DataResult<TenantDetailDomain>>
    fun updateTenant(tenantId: Int?, tenantPayload: TenantPayload): Flow<DataResult<SubmitTenantDomain>>
    fun deleteTenant()
}

class TenantRepositoryImpl(private val tenantDataSource: TenantDataSource) : TenantRepository {
    override fun createTenant(tenantPayload: TenantPayload) = flow {
        emit(DataResult.Loading)
        val response = tenantDataSource.createTenant(
            nameTenant = tenantPayload.name_tenants,
            addressTenants = tenantPayload.address_tenants,
            phone = tenantPayload.phone,
            file = tenantPayload.image
        )
        try {
            if (response.isSuccessful) {
                val mapper = SubmitTenantMapper().map(response.body() ?: SubmitTenantResponse())
                emit(DataResult.Success(mapper))
            } else {
                val errorBody = response.errorBody()?.string().orEmpty()
                val message = org.json.JSONObject(errorBody).getString("message")
                emit(DataResult.Error(message))
            }
        } catch (e: Exception) {
            emit(DataResult.Error(e.message.orEmpty()))
        }
    }

    override fun getTenant() = flow {
        emit(DataResult.Loading)
        val response = tenantDataSource.getTenantByUserId()
        try {
            if (response.isSuccessful) {
                val mapper = TenantMapper().map(response.body() ?: TenantsResponse())
                emit(DataResult.Success(mapper))
            } else {
                val errorBody = response.errorBody()?.string().orEmpty()
                val message = org.json.JSONObject(errorBody).getString("message")
                emit(DataResult.Error(message))
            }
        } catch (e: Exception) {
            emit(DataResult.Error(e.message.orEmpty()))
        }
    }

    override fun getDetailTenant(tenantId: Int) = flow {
        emit(DataResult.Loading)
        val response = tenantDataSource.getDetailTenant(tenantId)
        try {
            if (response.isSuccessful) {
                val mapper = TenantDetailMapper().map(response.body() ?: TenantDetailResponse())
                emit(DataResult.Success(mapper))
            } else {
                val errorBody = response.errorBody()?.string().orEmpty()
                val message = org.json.JSONObject(errorBody).getString("message")
                emit(DataResult.Error(message))
            }
        } catch (e: Exception) {
            emit(DataResult.Error(e.message.orEmpty()))
        }
    }

    override fun updateTenant(tenantId: Int?, tenantPayload: TenantPayload) = flow {
        emit(DataResult.Loading)
        val response = tenantDataSource.updateTenant(
            tenantId = tenantId,
            nameTenant = tenantPayload.name_tenants,
            addressTenants = tenantPayload.address_tenants,
            phone = tenantPayload.phone,
            file = tenantPayload.image
        )
        try {
            if (response.isSuccessful) {
                val mapper = SubmitTenantMapper().map(response.body() ?: SubmitTenantResponse())
                emit(DataResult.Success(mapper))
            } else {
                val errorBody = response.errorBody()?.string().orEmpty()
                val message = org.json.JSONObject(errorBody).getString("message")
                emit(DataResult.Error(message))
            }
        } catch (e: Exception) {
            emit(DataResult.Error(e.message.orEmpty()))
        }
    }

    override fun deleteTenant() {

    }

}