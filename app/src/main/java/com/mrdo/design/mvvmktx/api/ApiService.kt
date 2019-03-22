package com.mrdo.design.mvvmktx.api

import com.mrdo.design.mvvmktx.data.BaseResponse
import com.mrdo.design.mvvmktx.data.HomeData
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by dulijie on 2019/3/22.
 */
interface ApiService {

    @GET("newc2c/homedata")
    fun homedata(): Observable<BaseResponse<HomeData>>
}