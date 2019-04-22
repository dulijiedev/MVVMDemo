package com.mrdo.design.mvvmktx

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.mrdo.design.mvvmktx.base.view.BaseInjectAt
import com.mrdo.design.mvvmktx.databinding.ActivityMainBinding
import com.mrdo.design.mvvmktx.glide.GlideUtils
import com.mrdo.design.mvvmktx.utils.ActivityUtils
import com.mrdo.design.mvvmktx.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseInjectAt<ActivityMainBinding, MainViewModel>() {

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        super.initData()
        tv_test.movementMethod = ScrollingMovementMethod.getInstance()
        GlideUtils.setImage(
            this,
            iv_test,
            "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1553689475&di=26cf068df37096567840d6e8cb00ea1a&src=http://pic.58pic.com/58pic/15/68/59/71X58PICNjx_1024.jpg",
//            blur = true//,
            roundedCorners = true
        )

        tv_test.setOnClickListener {
            ActivityUtils.startActivity(this,MainActivity::class.java,iv_test)
        }
    }
}
