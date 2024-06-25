package com.cpstn.momee.data.mapper

import com.cpstn.momee.data.domain.SubmitTenantDomain
import com.cpstn.momee.network.response.SubmitTenantResponse
import com.cpstn.momee.utils.Mapper

class SubmitTenantMapper : Mapper<SubmitTenantResponse, SubmitTenantDomain> {
    override fun map(input: SubmitTenantResponse): SubmitTenantDomain {
        return SubmitTenantDomain(
            data = input.data?.toDomain(),
            message = input.message,
            success = input.success
        )
    }

    private fun SubmitTenantResponse.Data.toDomain(): SubmitTenantDomain.Data {
        return SubmitTenantDomain.Data(
            addressTenants = this.addressTenants,
            id = this.id,
            image = this.image,
            nameTenants = this.nameTenants,
            phone = this.phone
        )
    }
}