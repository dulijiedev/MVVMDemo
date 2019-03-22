package com.mrdo.design.mvvmktx.nework

import android.util.Log
import com.mrdo.design.mvvmktx.base.viewmodel.LifecycleViewModel
import com.mrdo.design.mvvmktx.data.BaseResponse
import com.mrdo.design.mvvmktx.ext.lifecycle.bindLifecycle
import com.mrdo.design.mvvmktx.nework.exceptions.ApiException
import com.mrdo.design.mvvmktx.nework.funs.ResultFunction
import com.mrdo.design.mvvmktx.nework.funs.ServerResultFunction
import com.mrdo.design.mvvmktx.nework.schedulers.SchedulerUtils
import com.uber.autodispose.ObservableSubscribeProxy
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by dulijie on 2019/3/22.
 */

/**直接绑定ViewModel 获取相关生命周期*/
fun <T> getObservable(
    apiObservable: Observable<out BaseResponse<T>>,
    lifecycleViewModel: LifecycleViewModel
): ObservableSubscribeProxy<BaseResponse<T>> {
    return apiObservable
        .map(ServerResultFunction())
        .onErrorResumeNext(ResultFunction())
        .compose(SchedulerUtils.ioToMain())
        .bindLifecycle(lifecycleViewModel)
}

/**
 * 需要手动绑定 调用 bindLifecycle
 */
fun <T> getObservable(apiObservable: Observable<out BaseResponse<T>>): Observable<BaseResponse<T>> {
    val maxRetries = 3
    var retryCount = 0
    return apiObservable
        .map(ServerResultFunction())
        .onErrorResumeNext(ResultFunction())
        .retryWhen { it ->
            it.flatMap {
                val exception = it as? ApiException
                if (exception != null) {
                    if (it.code == 1004 || it.code == 1001) {
                        Observable.error(it)
                    } else {
                        if (++retryCount <= maxRetries) {
                            Log.e(
                                "getObservable retry",
                                "get error, it will try after 3 seconds retry count $retryCount"
                            )
                            Observable.timer(1, TimeUnit.SECONDS)
                        } else
                            Observable.error(it)
                    }
                } else {
                    Observable.error(it)
                }
            }
        }
        .compose(SchedulerUtils.ioToMain())
}
