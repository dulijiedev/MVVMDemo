package com.mrdo.design.mvvmktx.base.view

/**
 * Created by dulijie on 2019/3/21.
 */
interface IBaseAt{

    /**
     * 初始化传递参数
     */
    fun initParam()

    /**
     * 初始化数据
     */
    fun initData()

    /**
     * 初始化View Observer
     */
    fun initObservable()
}