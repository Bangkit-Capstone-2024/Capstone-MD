package com.cpstn.momee.data.mapper

import com.cpstn.momee.data.domain.AuthDomain
import com.cpstn.momee.network.response.AuthResponse
import com.cpstn.momee.utils.Mapper

class AuthMapper : Mapper<AuthResponse.Result, AuthDomain.Result> {
    override fun map(input: AuthResponse.Result): AuthDomain.Result {
        return AuthDomain.Result(
            success = input.success.orEmpty(),
            message = input.message.orEmpty(),
            data = input.data.toDomainData()
        )
    }

    private fun AuthResponse.Data?.toDomainData(): AuthDomain.Data {
        return AuthDomain.Data(
            id = this?.id,
            email = this?.email.orEmpty(),
            username = this?.username.orEmpty(),
            isVerified = this?.isVerified,
            token = this?.token.orEmpty()
        )
    }
}