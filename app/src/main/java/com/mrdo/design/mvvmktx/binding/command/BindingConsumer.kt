package com.mrdo.design.mvvmktx.binding.command

/**
 * Created by dulijie on 2018/12/24.
 */
interface BindingConsumer<T> {
    fun call(t: T)
}
