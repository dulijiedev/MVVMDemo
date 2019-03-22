package com.mrdo.design.mvvmktx.nework.schedulers

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * new-main
 */
class NewThreadMainScheduler<T> private constructor() :
    BaseScheduler<T>(Schedulers.newThread(), AndroidSchedulers.mainThread())