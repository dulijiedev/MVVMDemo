package com.mrdo.design.mvvmktx.data

/**
 * Created by dulijie on 2019/3/22.
 */
class BaseResponse<T>(
    val responseCode: Int,
    val message: String,
    val data: T
) {
    val success: Boolean
        get() {
            return responseCode == 200
        }
}