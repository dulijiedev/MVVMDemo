package com.mrdo.design.mvvmktx.nework.funs

import android.util.Log
import com.mrdo.design.mvvmktx.BuildConfig
import com.mrdo.design.mvvmktx.nework.exceptions.ExceptionEngine
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * Created by dulijie on 2019/3/22.
 * 异常
 */
class ResultFunction<T> : Function<Throwable, Observable<T>> {

    override fun apply(t: Throwable): Observable<T> {
        if (BuildConfig.DEBUG) {
            Log.e("ResultFunction", t.toString())
        }
        return Observable.error(ExceptionEngine.handleException(t))
    }
}