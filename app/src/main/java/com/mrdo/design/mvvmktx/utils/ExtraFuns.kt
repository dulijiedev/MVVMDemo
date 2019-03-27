package com.mrdo.design.mvvmktx.utils

import android.app.Activity
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.mrdo.design.mvvmktx.glide.GlideUtils

/**
 * Created by dulijie on 2019/3/27.
 */

fun ImageView.setImage(activity: Activity, url: String) {
    GlideUtils.setImage(activity, this, url)
}

//fun ImageView.setImage(fragment: Fragment, url: String) {
//    GlideUtils.setImage(fragment, this, url)
//}