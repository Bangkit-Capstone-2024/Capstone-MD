package com.cpstn.momee.data.mapper

import com.cpstn.momee.data.domain.ProductDomain
import com.cpstn.momee.network.response.ProductResponse
import com.cpstn.momee.utils.Mapper

class ProductMapper : Mapper<ProductResponse, ProductDomain> {
    override fun map(input: ProductResponse): ProductDomain {
        return ProductDomain(
            data = input.data.toDomain(),
            message = input.message,
            success = input.success
        )
    }

    fun List<ProductResponse.Data>.toDomain(): List<ProductDomain.Data> {
        val data = arrayListOf<ProductDomain.Data>()
        this.forEach { item ->
            data.add(
                ProductDomain.Data(
                    addressTenants = item.addressTenants,
                    category = item.category?.toDomainData(),
                    description = item.description,
                    categoryId = item.categoryId,
                    id =  item.id,
                    isAvailable = item.isAvailable,
                    nameProducts = item.nameProducts,
                    pictures = item.pictures,
                    price = item.price,
                    slug = item.slug,
                    stock = item.stock,
                    tenant = item.tenant?.toDomainData()
                )
            )
        }
        return data
    }

    private fun ProductResponse.Data.Category.toDomainData(): ProductDomain.Data.Category {
        return ProductDomain.Data.Category (
            id = this.id,
            image = this.image,
            nameCategories = this.nameCategories
        )
    }

    private fun ProductResponse.Data.Tenant.toDomainData(): ProductDomain.Data.Tenant {
        return ProductDomain.Data.Tenant(
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