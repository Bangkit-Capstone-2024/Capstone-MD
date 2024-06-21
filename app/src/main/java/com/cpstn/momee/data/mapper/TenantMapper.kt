package com.cpstn.momee.data.mapper

import com.cpstn.momee.data.domain.TenantDomain
import com.cpstn.momee.network.response.TenantsResponse
import com.cpstn.momee.utils.Mapper

class TenantMapper : Mapper<TenantsResponse, TenantDomain> {
    override fun map(input: TenantsResponse): TenantDomain {
        return TenantDomain(
            data = input.data.toDomain(),
            success = input.success,
        )
    }

    fun List<TenantsResponse.Data>.toDomain(): List<TenantDomain.Data> {
        val data = arrayListOf<TenantDomain.Data>()
        this.forEach {
            data.add(
                TenantDomain.Data(
                    id = it.id,
                    addressTenants = it.addressTenants,
                    image = it.image,
                    locationLat = it.locationLat,
                    locationLng = it.locationLng,
                    nameTenants = it.nameTenants,
                    phone = it.phone,
                    totalProducts = it.totalProducts
                )
            )
        }
        return data
    }
}