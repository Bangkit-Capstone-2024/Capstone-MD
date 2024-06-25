package com.cpstn.momee.data.mapper

import com.cpstn.momee.data.domain.ProductCategoryDomain
import com.cpstn.momee.network.response.ProductCategoryResponse
import com.cpstn.momee.utils.Mapper


class ProductCategoryMapper : Mapper<ProductCategoryResponse, ProductCategoryDomain> {
    override fun map(input:ProductCategoryResponse): ProductCategoryDomain {
        return ProductCategoryDomain(
            success = input.success.orEmpty(),
            data = input.data.toDomainData()
        )
    }

    private fun List<ProductCategoryResponse.Data>?.toDomainData(): List<ProductCategoryDomain.Data> {
        val data = arrayListOf<ProductCategoryDomain.Data>()
        this?.forEach { item ->
            data.add(
                ProductCategoryDomain.Data(
                    id = item.id,
                    nameCategories = item.nameCategories.orEmpty(),
                    image = item.image.orEmpty(),
                    amount = item.amount
                )
            )
        }
        return data
    }
}