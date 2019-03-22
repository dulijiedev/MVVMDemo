package com.mrdo.design.mvvmktx.nework.schedulers

/**
 * 工具類
 */

object SchedulerUtils {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}