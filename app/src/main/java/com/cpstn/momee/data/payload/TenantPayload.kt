package com.cpstn.momee.data.payload

import okhttp3.MultipartBody
import okhttp3.RequestBody

data class TenantPayload(
    var name_tenants: RequestBody,
    var address_tenants: RequestBody,
    var phone: RequestBody,
    var image: MultipartBody.Part?,
)