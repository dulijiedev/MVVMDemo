package com.mrdo.design.mvvmktx.utils.voice.utils

import android.content.Context
import android.content.res.AssetFileDescriptor

/**
 * Created by dulijie on 2019/4/28.
 * 工具
 */

//获取Asset 根据文件名
fun getAssetFileDescription(context:Context,filename:String):AssetFileDescriptor{
    val manager = context.applicationContext.assets
    return manager.openFd(filename)
}