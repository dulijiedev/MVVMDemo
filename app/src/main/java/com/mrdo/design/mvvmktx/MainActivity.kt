package com.mrdo.design.mvvmktx

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.mrdo.design.mvvmktx.base.view.BaseInjectAt
import com.mrdo.design.mvvmktx.databinding.ActivityMainBinding
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
    }
}
