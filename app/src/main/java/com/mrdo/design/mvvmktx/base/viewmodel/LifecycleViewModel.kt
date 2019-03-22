package com.mrdo.design.mvvmktx.base.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

/**
 * Created by dulijie on 2019/3/21.
 */
open class LifecycleViewModel : ViewModel(), IViewModel {

    var lifecycleOwner: LifecycleOwner? = null

    override fun onLifecycleChange(lifecycleOwner: LifecycleOwner, event: Lifecycle.Event) {

    }

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    override fun onStart(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    override fun onResume(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    override fun onPause(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    override fun onStop(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    override fun onDestroy(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = null
    }

    override fun registerEventBus() {
    }

    override fun unregisterEventBus() {
    }

}