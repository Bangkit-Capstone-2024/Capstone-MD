package com.cpstn.momee.data.mapper

import com.cpstn.momee.data.domain.ProductsSearchByImageDomain
import com.cpstn.momee.network.response.ProductsSearchByImageResponse
import com.cpstn.momee.utils.Mapper


class ProductsSearchByImageMapper :
    Mapper<ProductsSearchByImageResponse.Result, ProductsSearchByImageDomain.Result> {
    override fun map(input: ProductsSearchByImageResponse.Result): ProductsSearchByImageDomain.Result {
        return ProductsSearchByImageDomain.Result(
            success = input.success.orEmpty(),
            message = input.message.orEmpty(),
            data = input.data.toDomainData()
        )
    }

    private fun List<ProductsSearchByImageResponse.Data>?.toDomainData(): List<ProductsSearchByImageDomain.Data> {
        val data = arrayListOf<ProductsSearchByImageDomain.Data>()
        this?.forEach { item ->
            data.add(
                ProductsSearchByImageDomain.Data(
                    id = item.id,
                    name = item.name,
                    description = item.description
                )
            )
        }
        return data
    }
}