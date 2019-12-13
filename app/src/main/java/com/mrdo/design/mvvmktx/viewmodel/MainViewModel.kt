package com.mrdo.design.mvvmktx.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.mrdo.design.mvvmktx.base.repository.AppRepository
import com.mrdo.design.mvvmktx.base.viewmodel.BaseViewModel
import com.mrdo.design.mvvmktx.data.BaseResponse
import com.mrdo.design.mvvmktx.data.HomeData
import com.mrdo.design.mvvmktx.datasource.VerifyCodeDataSource
import com.mrdo.design.mvvmktx.datasource.VerifyCodeRepository
import com.mrdo.design.mvvmktx.ext.lifecycle.bindLifecycle
import com.mrdo.design.mvvmktx.nework.RetrofitManager
import com.mrdo.design.mvvmktx.nework.exceptions.ApiException
import com.mrdo.design.mvvmktx.nework.observers.HttpObserver
import okhttp3.*
import java.io.IOException

/**
 * Created by dulijie on 2019/3/21.
 */
class MainViewModel : BaseViewModel() {

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)
        homedata()
    }

    val data = MutableLiveData<String>()

    fun homedata() {
        AppRepository.homedata()
            .bindLifecycle(this@MainViewModel)
            .subscribe(object : HttpObserver<BaseResponse<HomeData>>() {
                override fun onFailed(e: ApiException) {
                    Log.e("dlj=:", e.toString())
                    data.value = e.msg
                }

                override fun onSuccess(t: BaseResponse<HomeData>) {
                    Log.d("dlj=:", t.data.toString())
                    data.value = t.data.toString()
                }

            })

        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .url("www.baidu.com")
            .build()
        val call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
            }

        })
    }

    val emailDataSource = VerifyCodeRepository(VerifyCodeDataSource())

    fun sendEmailCode(phoneNum: String) {
        emailDataSource.sendEmail(phoneNum)
    }
}