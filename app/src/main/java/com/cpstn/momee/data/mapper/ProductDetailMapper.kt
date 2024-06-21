package com.cpstn.momee.data.mapper

import com.cpstn.momee.data.domain.ProductDetailDomain
import com.cpstn.momee.network.response.ProductDetailResponse
import com.cpstn.momee.utils.Mapper


class ProductDetailMapper : Mapper<ProductDetailResponse, ProductDetailDomain> {
    override fun map(input: ProductDetailResponse): ProductDetailDomain {
        return ProductDetailDomain(
            data = input.data.toDomain(),
            message = input.message,
            success = input.success
        )
    }

    fun ProductDetailResponse.Data.toDomain(): ProductDetailDomain.Data {
        return ProductDetailDomain.Data(
            addressTenants = this.addressTenants,
            description = this.description,
            categoryId = this.categoryId,
            id = this.id,
            isAvailable = this.isAvailable,
            nameProducts = this.nameProducts,
            pictures = this.pictures,
            price = this.price,
            slug = this.slug,
            stock = this.stock,
            tenant = this.tenant?.toDomainData()
        )
    }

    private fun ProductDetailResponse.Data.Tenant.toDomainData(): ProductDetailDomain.Data.Tenant {
        return ProductDetailDomain.Data.Tenant(
            id = this.id,
            image = this.image,
            addressTenants = this.addressTenants,
            locationLng = this.locationLng,
            locationLat = this.locationLat,
            nameTenants = this.nameTenants,
            phone = this.phone,
            userId = this.userId
        )
    }
}