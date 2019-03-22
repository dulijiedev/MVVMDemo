package com.mrdo.design.mvvmktx.binding.command

/**
 * Created by dulijie on 2018/12/24.
 */
class BindingCommand<T> {

    private var execute: BindingAction? = null
    private var consumer: BindingConsumer<T>? = null
    private var canExecute0: BindingFunction<Boolean>? = null

    constructor(execute: BindingAction?) {
        this.execute = execute
    }

    constructor(consumer: BindingConsumer<T>?) {
        this.consumer = consumer
    }

    constructor(execute: BindingAction?, canExecute0: BindingFunction<Boolean>?) {
        this.execute = execute
        this.canExecute0 = canExecute0
    }

    constructor(consumer: BindingConsumer<T>?, canExecute0: BindingFunction<Boolean>?) {
        this.consumer = consumer
        this.canExecute0 = canExecute0
    }

    private fun canExecute0(): Boolean {
        return if (canExecute0 == null) {
            true
        } else {
            canExecute0!!.call()
        }
    }

    fun execute() {
        if (execute != null && canExecute0()) {
            execute?.call()
        }
    }

    fun execute(parameter: T) {
        if (consumer != null && canExecute0()) {
            consumer?.call(parameter)
        }
    }
}