package com.mrdo.design.mvvmktx.nework.schedulers

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 計算
 */
class ComputationMainScheduler<T> private constructor() :
    BaseScheduler<T>(Schedulers.computation(), AndroidSchedulers.mainThread())