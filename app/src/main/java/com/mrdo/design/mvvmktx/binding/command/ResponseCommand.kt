package com.mrdo.design.mvvmktx.binding.command

import io.reactivex.functions.Function

/**
 * Created by dulijie on 2018/12/24.
 */
class ResponseCommand<T, R> {
    private var execute: BindingFunction<R>? = null
    private var function: Function<T, R>? = null
    private var canExecute: BindingFunction<Boolean>? = null

    constructor(execute: BindingFunction<R>?) {
        this.execute = execute
    }

    constructor(execute: Function<T, R>?) {
        this.function = execute
    }

    constructor(execute: BindingFunction<R>?, canExecute: BindingFunction<Boolean>?) {
        this.execute = execute
        this.canExecute = canExecute
    }

    private fun canExecute(): Boolean {
        if (canExecute == null) {
            return true
        } else {
            return canExecute!!.call()
        }

    }

    fun execute(): R? {
        if (execute != null && canExecute()) {
            return execute?.call()
        }
        return null
    }

    fun execute(parameter: T): R? {
        if (function != null && canExecute()) {
            return function?.apply(parameter)
        }
        return null
    }

}