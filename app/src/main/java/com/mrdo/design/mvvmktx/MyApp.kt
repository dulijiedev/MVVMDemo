package com.mrdo.design.mvvmktx

import android.app.Application
import com.mrdo.design.mvvmktx.utils.ActivityUtils
import com.mrdo.design.mvvmktx.utils.DelegatesExt

/**
 * Created by dulijie on 2019/3/21.
 */
class MyApp : Application() {

    companion object {
        var instance: MyApp by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ActivityUtils.init(this)
    }
}