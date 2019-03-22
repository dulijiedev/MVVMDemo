package com.mrdo.design.mvvmktx.ext.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.mrdo.design.mvvmktx.base.viewmodel.LifecycleViewModel

fun LifecycleViewModel.addLifecycle(activity: AppCompatActivity) =
    activity inject this

fun LifecycleViewModel.addLifecycle(activity: FragmentActivity) =
    activity inject this

fun LifecycleViewModel.addLifecycle(fragment: Fragment) =
    fragment inject this

fun LifecycleViewModel.addLifecycle(lifecycleOwner: LifecycleOwner) =
    lifecycleOwner inject this

private infix fun <A : LifecycleOwner, B : LifecycleViewModel> A.inject(viewModel: B) =
    lifecycle.addObserver(viewModel)