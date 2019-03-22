package com.mrdo.design.mvvmktx.base.repository

import com.mrdo.design.mvvmktx.api.ApiService
import com.mrdo.design.mvvmktx.data.BaseResponse
import com.mrdo.design.mvvmktx.data.HomeData
import io.reactivex.Observable

/**
 * Created by dulijie on 2019/3/22.
 */
object AppRepository : ApiService {

    private val dataSource = AppLocalDataSource()

    override fun homedata(): Observable<BaseResponse<HomeData>> {
        return dataSource.homedata()
    }


}