package com.mrdo.design.mvvmktx.base.repository

import com.mrdo.design.mvvmktx.api.ApiService
import com.mrdo.design.mvvmktx.data.BaseResponse
import com.mrdo.design.mvvmktx.data.HomeData
import com.mrdo.design.mvvmktx.nework.RetrofitManager
import com.mrdo.design.mvvmktx.nework.getObservable
import io.reactivex.Observable

/**
 * Created by dulijie on 2019/3/22.
 */
class AppLocalDataSource : ApiService {

    override fun homedata(): Observable<BaseResponse<HomeData>> {
        return getObservable(RetrofitManager.service.homedata())
    }
}