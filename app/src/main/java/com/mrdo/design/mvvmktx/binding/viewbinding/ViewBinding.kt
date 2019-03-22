package com.mrdo.design.mvvmktx.binding.viewbinding

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.jakewharton.rxbinding3.view.clicks
import com.mrdo.design.mvvmktx.binding.command.BindingCommand
import java.util.concurrent.TimeUnit

/**
 * Created by dulijie on 2019/3/21.
 */

//防重复点击间隔(秒)
val CLICK_INTERVAL = 1

/**
 * requireAll 是意思是是否需要绑定全部参数, false为否
 * View的onClick事件绑定
 * onClickCommand 绑定的命令,
 * isThrottleFirst 是否开是否开启防止过快点击启防止过快点击
 */
@BindingAdapter(value = ["onClickCommand", "isThrottleFirst"], requireAll = false)
fun onClickCommand(view: View, clickCommand: BindingCommand<*>?, isThrottleFirst: Boolean = false) {
    if (isThrottleFirst) {
        view.clicks()
            .subscribe {
                if (clickCommand != null) {
                    clickCommand!!.execute()
                }
            }
    } else {
        view.clicks()
            .throttleFirst(CLICK_INTERVAL.toLong(), TimeUnit.SECONDS)//1秒钟内只允许点击1次
            .subscribe {
                if (clickCommand != null) {
                    clickCommand!!.execute()
                }
            }
    }
}

@BindingAdapter("bind_image_src")
fun bindImageViewSrc(imageView: ImageView, @DrawableRes resId: Int) {
    imageView.setImageResource(resId)
}