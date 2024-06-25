package com.cpstn.momee.data.mapper

import com.cpstn.momee.data.domain.TenantDetailDomain
import com.cpstn.momee.network.response.TenantDetailResponse
import com.cpstn.momee.utils.Mapper

class TenantDetailMapper : Mapper<TenantDetailResponse, TenantDetailDomain> {
    override fun map(input: TenantDetailResponse): TenantDetailDomain {
        return TenantDetailDomain(
            data = input.data?.toDomain(),
            success = input.success
        )
    }

    private fun TenantDetailResponse.Data.toDomain(): TenantDetailDomain.Data {
        return TenantDetailDomain.Data(
            addressTenants = this.addressTenants,
            id = this.id,
            image = this.image,
            locationLat = this.locationLat,
            locationLng = this.locationLng,
            nameTenants = this.nameTenants,
            phone = this.phone,
            products = this.products.toData(),
            totalProducts = this.totalProducts
        )
    }

    private fun List<TenantDetailResponse.Data.Product>.toData(): List<TenantDetailDomain.Data.Product> {
        val data = arrayListOf<TenantDetailDomain.Data.Product>()
        this.forEach {
            data.add(
                TenantDetailDomain.Data.Product(
                    addressTenants = it.addressTenants,
                    description = it.description,
                    id = it.id,
                    isAvailable = it.isAvailable,
                    nameProducts = it.nameProducts,
                    pictures = it.pictures,
                    price = it.price,
                    slug = it.slug,
                    stock = it.stock,
                    tenantId = it.tenantId
                )
            )
        }
        return data
    }

}