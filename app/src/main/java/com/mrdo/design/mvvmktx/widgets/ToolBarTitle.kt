package com.mrdo.design.mvvmktx.widgets

import androidx.lifecycle.MutableLiveData
import com.mrdo.design.mvvmktx.binding.command.BindingCommand

/**
 * Created by dulijie 2019-3-21
 */
class ToolBarTitle(val title: MutableLiveData<String>) {


    var rightText: MutableLiveData<String>? = null
    var rightAction: BindingCommand<Unit>? = null
    var rightImg: MutableLiveData<Int>? = null
    var unreadNum: MutableLiveData<Int>? = null

    constructor(
        title: MutableLiveData<String>,
        rightText: MutableLiveData<String>?,
        rightAction: BindingCommand<Unit>?
    ) : this(title) {
        this.rightText = rightText
        this.rightAction = rightAction
    }

    constructor(
        title: MutableLiveData<String>,
        rightText: MutableLiveData<String>?,
        rightImg: MutableLiveData<Int>?,
        rightAction: BindingCommand<Unit>
    ) : this(title, rightText, rightAction) {
        this.rightImg = rightImg
    }

    constructor(
        title: MutableLiveData<String>,
        rightText: MutableLiveData<String>?,
        rightImg: MutableLiveData<Int>?,
        rightAction: BindingCommand<Unit>,
        unreadNum: MutableLiveData<Int>?
    ) : this(title, rightText, rightImg, rightAction) {
        this.unreadNum = unreadNum
    }
}