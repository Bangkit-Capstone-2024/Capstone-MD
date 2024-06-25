package com.cpstn.momee.data.payload

import okhttp3.MultipartBody
import okhttp3.RequestBody

data class ProductPayload(
    var nameProducts: RequestBody,
    var description: RequestBody,
    var phone: RequestBody,
    var stock: RequestBody,
    var isAvailable: RequestBody,
    var categoryId: RequestBody,
    var tenantId: RequestBody,
    var file: MultipartBody.Part? = null,
)