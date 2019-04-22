package com.mrdo.design.mvvmktx.base.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.mrdo.design.mvvmktx.base.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * Created by dulijie on 2019/4/9.
 */
abstract class BaseInjectFrag<V : ViewDataBinding, VM : BaseViewModel> : Fragment(), IBaseAction {

    lateinit var binding: V
    var viewModel: VM? = null
    private var viewModelId = -1

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParam()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, initContentView(), container, false)
        with(binding) {
            this.lifecycleOwner = this@BaseInjectFrag
        }
        initParam()
        viewModelId = initVariableId()
        viewModel = initViewModel()
        if (viewModel == null) {
            val modelClazz: Class<out ViewModel>
            val type = javaClass.genericSuperclass
            modelClazz = if (type is ParameterizedType) {
                type.actualTypeArguments[1] as Class<out ViewModel>
            } else {
                BaseViewModel::class.java
            }
            viewModel = createViewModel(this@BaseInjectFrag, modelClazz) as? VM
        }
        binding.setVariable(viewModelId, viewModel)
        viewModel?.let {
            lifecycle.addObserver(it)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initObservable()
        viewModel?.registerEventBus()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //->onCreateView
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.let {
            lifecycle.removeObserver(it)
        }
        viewModel?.unregisterEventBus()
        viewModel = null
        binding.unbind()
    }

    override fun onDetach() {
        super.onDetach()
    }


    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun initContentView(): Int

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun initVariableId(): Int

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    open fun initViewModel(): VM? {
        return null
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    private fun <T : ViewModel> createViewModel(fragment: Fragment, cls: Class<T>): T {
        return ViewModelProviders.of(fragment).get(cls)
    }

    /**
     * 初始化传递参数
     */
    override fun initParam() {
    }

    /**
     *
     */
    override fun initData() {
    }

    override fun initObservable() {
    }


}