package com.mrdo.design.mvvmktx.base.view

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.mrdo.design.mvvmktx.base.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.base_title_view.*
import java.lang.reflect.ParameterizedType

abstract class BaseInjectAt<V : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(), IBaseAction {

    lateinit var binding: V
    var viewModel: VM? = null
    private var viewModelId = -1
    private var loadingDialog: Dialog? = null
    private var requestCode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化接受参数
        initParam()
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState)
        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack()
        viewModel?.registerEventBus()
        //页面数据初始化方法
        initData()
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initObservable()
        initToolBar()
    }

    private fun initToolBar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar)
        }
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
        }
    }

    private fun initViewDataBinding(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState))
        with(binding) {
            setLifecycleOwner(this@BaseInjectAt)
        }
        //
        viewModelId = initVariableId()
        viewModel = initViewModel()
        if (viewModel == null) {
            val modelClass: Class<out ViewModel>
            val type = javaClass.genericSuperclass
            modelClass = if (type is ParameterizedType) {
                type.actualTypeArguments[1] as Class<out ViewModel>
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                BaseViewModel::class.java
            }
            viewModel = this.createViewModel(this@BaseInjectAt, modelClass) as? VM
        }

        binding.setVariable(viewModelId, viewModel)
        viewModel?.let {
            lifecycle.addObserver(it)
        }
    }


    private fun registorUIChangeLiveDataCallBack() {
//        //加载对话框显示
//        viewModel?.dialogEvent?.observe(this@BaseInjectActivity, Observer { it ->
//            if (loadDialog == null)
//                loadDialog = LoadingDialog(this@BaseInjectActivity)
//            it?.let {
//                if (it && loadDialog!!.isShowing)
//                    return@Observer
//                if (it) {
//                    ImmersionBar.with(this@BaseInjectActivity, loadDialog!!).init()
//                    loadDialog?.show()
//                } else {
//                    ImmersionBar.with(this@BaseInjectActivity, loadDialog!!).destroy()
//                    loadDialog?.dismiss()
//                }
//            }
//        })
//
//        //跳入新页面
//        viewModel?.startActivityEvent?.observe(this@BaseInjectActivity, Observer {
//            val clz = it!![BaseViewModel.ParameterField.CLASS] as Class<*>
//            val bundle = it!![BaseViewModel.ParameterField.BUNDLE] as? Bundle
//            startActivity(clz, bundle)
//        })
//
//        //跳入新页面并回调
//        viewModel?.startActivityForResultEvent?.observe(this@BaseInjectActivity, Observer {
//            val clz = it!![BaseViewModel.ParameterField.CLASS] as Class<*>
//            val bundle = it!![BaseViewModel.ParameterField.BUNDLE] as? Bundle
//            val code = it!![BaseViewModel.ParameterField.CODE] as? Int
//            startActivityForResult(clz, bundle, code!!)
//        })
//
//        //关闭界面
//        viewModel?.finishEvent?.observe(this@BaseInjectActivity, Observer {
//            finish()
//        })
//
//        //关闭上一层
//        viewModel?.onBackPressedEvent?.observe(this@BaseInjectActivity, Observer {
//            onBackPressed()
//        })
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    private fun <T : ViewModel> createViewModel(activity: FragmentActivity, cls: Class<T>): T {
        return ViewModelProviders.of(activity).get(cls)
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun initContentView(savedInstanceState: Bundle?): Int

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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.unregisterEventBus()
        viewModel = null
        binding.unbind()
    }

    override fun initParam() {

    }

    override fun initData() {
    }

    override fun initObservable() {
    }


}