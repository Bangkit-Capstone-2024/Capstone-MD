package com.cpstn.momee.data.mapper

import com.cpstn.momee.data.domain.UserListDomain
import com.cpstn.momee.network.response.UserListResponse
import com.cpstn.momee.utils.Mapper


class UserListMapper : Mapper<UserListResponse, UserListDomain> {
    override fun map(input: UserListResponse): UserListDomain {
        return UserListDomain(
            success = input.success.orEmpty(),
            message = input.message.orEmpty(),
            query = input.query.toDomainData()
        )
    }

    private fun List<UserListResponse.Query>.toDomainData(): List<UserListDomain.Query> {
        val data = ArrayList<UserListDomain.Query>()
        this.forEach { item ->
            data.add(
                UserListDomain.Query(
                    email = item.email.orEmpty(),
                    id = item.id,
                    isVerified = item.isVerified,
                    username = item.username
                )
            )
        }
        return data
    }
}