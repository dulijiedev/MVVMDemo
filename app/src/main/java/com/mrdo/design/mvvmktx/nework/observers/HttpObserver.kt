package com.mrdo.design.mvvmktx.nework.observers

import com.mrdo.design.mvvmktx.nework.exceptions.ApiException
import com.mrdo.design.mvvmktx.nework.exceptions.ExceptionEngine
import io.reactivex.observers.DisposableObserver

/**
 * Created by dulijie on 2019/3/22.
 */
abstract class HttpObserver<T> : DisposableObserver<T>() {


    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        when (e) {
            is ApiException -> {
                onFailed(e)
            }
            else -> {
                onFailed(ExceptionEngine.handleException(e))
            }
        }
    }

    override fun onComplete() {
    }

    abstract fun onFailed(e: ApiException)

    abstract fun onSuccess(t: T)

}