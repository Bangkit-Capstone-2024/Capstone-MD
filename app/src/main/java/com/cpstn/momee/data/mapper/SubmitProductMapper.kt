package com.cpstn.momee.data.mapper

import com.cpstn.momee.data.domain.SubmitProductDomain
import com.cpstn.momee.network.response.SubmitProductResponse
import com.cpstn.momee.utils.Mapper

class SubmitProductMapper : Mapper<SubmitProductResponse, SubmitProductDomain> {

    override fun map(input: SubmitProductResponse): SubmitProductDomain {
        return SubmitProductDomain(
            data = input.data?.toDomain(),
            message = input.message,
            success = input.success
        )
    }

    private fun SubmitProductResponse.Data.toDomain(): SubmitProductDomain.Data {
        return SubmitProductDomain.Data(
            addressTenants = this.addressTenants,
            description = this.description,
            id = this.id,
            isAvailable = this.isAvailable,
            nameProducts = this.nameProducts,
            pictures = this.pictures,
            price = this.price,
            slug = this.slug,
            stock = this.stock
        )
    }
}