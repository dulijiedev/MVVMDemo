package com.mrdo.design.mvvmktx.nework.funs

import android.util.Log
import com.mrdo.design.mvvmktx.BuildConfig
import com.mrdo.design.mvvmktx.data.BaseResponse
import com.mrdo.design.mvvmktx.nework.exceptions.ServerException
import io.reactivex.functions.Function

class ServerResultFunction<T> : Function<BaseResponse<T>, BaseResponse<T>> {
    override fun apply(response: BaseResponse<T>): BaseResponse<T> {
        //打印服务器回传结果
        if (BuildConfig.DEBUG) {
            Log.e("ServerResultFunction:", response.toString())
        }
        if (!response.success) {
            throw ServerException(response.responseCode, response.message)
        }
        return response
    }
}