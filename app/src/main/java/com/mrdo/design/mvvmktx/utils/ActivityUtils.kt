package com.mrdo.design.mvvmktx.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.AnimRes
import androidx.annotation.NonNull
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.mrdo.design.mvvmktx.MyApp
import java.lang.ref.WeakReference
import java.util.*

object ActivityUtils {

    var application: Application? = null

    var sActivityList = LinkedList<Activity>()

    var sTopActivityWeakRef: WeakReference<Activity>? = null


    fun init(app: Application) {
        application = app
        app.registerActivityLifecycleCallbacks(mCallbacks)
    }

    private val mCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            activity?.let {
                sActivityList.add(it)
                setTopActivityWeakRef(it)
            }

        }
    }

    private fun setTopActivityWeakRef(activity: Activity) {
        if (sTopActivityWeakRef == null || activity != sTopActivityWeakRef!!.get()) {
            sTopActivityWeakRef = WeakReference(activity)
        }
    }


    fun getTopActivity(): Activity? {
        if (sTopActivityWeakRef != null) {
            val activity = sTopActivityWeakRef!!.get()
            if (activity != null) {
                return activity
            }
        }
        val activities = sActivityList
        val size = activities.size
        return if (activities.size > 0) activities[size - 1] else null
    }


    fun getActivityOrApp(): Context {
        return getTopActivity() ?: MyApp.instance
    }

    /**
     * Activity是否存在
     * @param packageName 包名
     * @param className activity 全路径类名
     * @return true :是 <br/> false :否
     */
    fun isActivityExists(@NonNull packageName: String, @NonNull className: String): Boolean {
        var intent = Intent()
        intent.setClassName(packageName, className)
        return !(MyApp.instance.packageManager.resolveActivity(intent, 0) == null ||
                intent.resolveActivity(MyApp.instance.packageManager) == null ||
                MyApp.instance.packageManager.queryIntentActivities(intent, 0).size == 0)
    }

    /**
     * 1
     * 基础启动
     */
    fun startActivity(@NonNull clz: Class<*>) {
        val context = getActivityOrApp()
        startActivity(context, null, context.packageName, clz.name, null)
    }

    /**
     * 2启动Activity
     * 跳转动画
     */
    fun startActivity(@NonNull clz: Class<*>, @NonNull options: Bundle) {
        val context = getActivityOrApp()
        startActivity(context, null, context.packageName, clz.name, options)
    }


    fun startActivity(context: Context, extras: Bundle? = null, pkg: String, clzName: String, options: Bundle? = null) {
        val intent = Intent(Intent.ACTION_VIEW)
        if (extras != null) {
            intent.putExtras(extras)
        }
        intent.component = ComponentName(pkg, clzName)
        startActivity(intent, context, options)
    }

    @SuppressLint("ObsoleteSdkInt")
    fun startActivity(intent: Intent, context: Context, options: Bundle? = null) {
        if (context !is Activity) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, options)
        } else {
            context.startActivity(intent)
        }
    }

    /**
     * 4
     */
    fun startActivity(@NonNull activity: Activity, @NonNull clz: Class<*>) {
        startActivity(activity, null, activity.packageName, clz.name, null)
    }

    /**
     * 5
     */
    fun startActivity(@NonNull activity: Activity, @NonNull clz: Class<*>, @NonNull options: Bundle) {
        startActivity(activity, null, activity.packageName, clz.name, options)
    }

    /**
     * 6 共享元素
     */
    fun startActivity(@NonNull activity: Activity, @NonNull clz: Class<*>, @NonNull vararg sharedElements: View) {
        startActivity(
            activity, null, activity.packageName, clz.name,
            getOptionsBundle(activity, sharedElements)
        )
    }

    /**
     * 启动 Activity
     *
     * @param activity  activity
     * @param clz       Activity 类
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    fun startActivity(@NonNull activity: Activity, @NonNull clz: Class<*>, @AnimRes enterAnim: Int, @AnimRes exitAnim: Int) {

        startActivity(activity, null, activity.packageName, clz.name, getOptionsBundle(activity, enterAnim, exitAnim))
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim)
        }
    }

    /**
     * 启动 Activity
     *
     * @param extras extras
     * @param clz    Activity 类
     */
    fun startActivity(@NonNull extras: Bundle, @NonNull clz: Class<*>) {
        val context = getActivityOrApp()
        startActivity(context, extras, context.packageName, clz.name, null)
    }

    /**
     * 启动 Activity
     *
     * @param extras  extras
     * @param clz     Activity 类
     * @param options 跳转动画
     */
    fun startActivity(@NonNull extras: Bundle, @NonNull clz: Class<*>, @NonNull options: Bundle) {
        val context = getActivityOrApp()
        startActivity(context, extras, context.packageName, clz.name, options)
    }

    /**
     * 启动 Activity
     *
     * @param extras    extras
     * @param clz       Activity 类
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    fun startActivity(@NonNull extras: Bundle, @NonNull clz: Class<*>, @AnimRes enterAnim: Int, @AnimRes exitAnim: Int) {
        val context = getActivityOrApp()
        startActivity(context, extras, context.packageName, clz.name, getOptionsBundle(context, enterAnim, exitAnim))
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context is Activity) {
            context.overridePendingTransition(enterAnim, exitAnim)
        }
    }

    /**
     *转场动画
     */
    fun getOptionsBundle(context: Context, enterAnim: Int, exitAnim: Int): Bundle? {
        return ActivityOptionsCompat.makeCustomAnimation(context, enterAnim, exitAnim).toBundle()
    }


    /**
     * 启动 Activity
     *
     * @param extras   extras
     * @param activity activity
     * @param clz      Activity 类
     */
    fun startActivity(
        @NonNull extras: Bundle,
        @NonNull activity: Activity,
        @NonNull clz: Class<*>
    ) {
        startActivity(activity, extras, activity.packageName, clz.name, null)
    }

    /**
     * 启动 Activity
     *
     * @param extras   extras
     * @param activity activity
     * @param clz      Activity 类
     * @param options  跳转动画
     */
    fun startActivity(
        @NonNull extras: Bundle,
        @NonNull activity: Activity,
        @NonNull clz: Class<*>,
        @NonNull options: Bundle
    ) {
        startActivity(activity, extras, activity.packageName, clz.name, options)
    }

    /**
     * 启动 Activity
     *
     * @param extras         extras
     * @param activity       activity
     * @param clz            Activity 类
     * @param sharedElements 共享元素
     */
    fun startActivity(
        @NonNull extras: Bundle,
        @NonNull activity: Activity,
        @NonNull clz: Class<*>,
        @NonNull vararg sharedElements: View
    ) {
        startActivity(
            activity, extras, activity.packageName, clz.name,
            getOptionsBundle(activity, sharedElements)
        )
    }

    /**
     * 启动 Activity
     *
     * @param extras    extras
     * @param activity  activity
     * @param clz       Activity 类
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    fun startActivity(
        @NonNull extras: Bundle,
        @NonNull activity: Activity,
        @NonNull clz: Class<*>,
        @AnimRes enterAnim: Int,
        @AnimRes exitAnim: Int
    ) {
        startActivity(
            activity, extras, activity.packageName, clz.name,
            getOptionsBundle(activity, enterAnim, exitAnim)
        )
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim)
        }
    }

    /**
     * 启动 Activity
     *
     * @param pkg 包名
     * @param cls 全类名
     */
    fun startActivity(
        @NonNull pkg: String,
        @NonNull cls: String
    ) {
        startActivity(getActivityOrApp(), null, pkg, cls, null)
    }

    /**
     * 启动 Activity
     *
     * @param pkg     包名
     * @param cls     全类名
     * @param options 动画
     */
    fun startActivity(
        @NonNull pkg: String,
        @NonNull cls: String,
        @NonNull options: Bundle
    ) {
        startActivity(getActivityOrApp(), null, pkg, cls, options)
    }

    /**
     * 启动 Activity
     *
     * @param pkg       包名
     * @param cls       全类名
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    fun startActivity(@NonNull pkg: String, @NonNull cls: String, @AnimRes enterAnim: Int, @AnimRes exitAnim: Int) {
        val context = getActivityOrApp()
        startActivity(context, null, pkg, cls, getOptionsBundle(context, enterAnim, exitAnim))
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context is Activity) {
            context.overridePendingTransition(enterAnim, exitAnim)
        }
    }

    /**
     * 启动 Activity
     *
     * @param activity activity
     * @param pkg      包名
     * @param cls      全类名
     */
    fun startActivity(
        @NonNull activity: Activity,
        @NonNull pkg: String,
        @NonNull cls: String
    ) {
        startActivity(activity, null, pkg, cls, null)
    }

    /**
     * 启动 Activity
     *
     * @param activity activity
     * @param pkg      包名
     * @param cls      全类名
     * @param options  动画
     */
    fun startActivity(
        @NonNull activity: Activity,
        @NonNull pkg: String,
        @NonNull cls: String,
        @NonNull options: Bundle
    ) {
        startActivity(activity, null, pkg, cls, options)
    }

    /**
     * 启动 Activity
     *
     * @param activity       activity
     * @param pkg            包名
     * @param cls            全类名
     * @param sharedElements 共享元素
     */
    fun startActivity(
        @NonNull activity: Activity,
        @NonNull pkg: String,
        @NonNull cls: String,
        @NonNull vararg sharedElements: View
    ) {
        startActivity(activity, null, pkg, cls, getOptionsBundle(activity, sharedElements))
    }

    /**
     * 启动 Activity
     *
     * @param activity  activity
     * @param pkg       包名
     * @param cls       全类名
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    fun startActivity(
        @NonNull activity: Activity,
        @NonNull pkg: String,
        @NonNull cls: String,
        @AnimRes enterAnim: Int,
        @AnimRes exitAnim: Int
    ) {
        startActivity(activity, null, pkg, cls, getOptionsBundle(activity, enterAnim, exitAnim))
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim)
        }
    }

    /**
     * 启动 Activity
     *
     * @param extras extras
     * @param pkg    包名
     * @param cls    全类名
     */
    fun startActivity(
        @NonNull extras: Bundle,
        @NonNull pkg: String,
        @NonNull cls: String
    ) {
        startActivity(getActivityOrApp(), extras, pkg, cls, null)
    }

    /**
     * 启动 Activity
     *
     * @param extras  extras
     * @param pkg     包名
     * @param cls     全类名
     * @param options 动画
     */
    fun startActivity(
        @NonNull extras: Bundle,
        @NonNull pkg: String,
        @NonNull cls: String,
        @NonNull options: Bundle
    ) {
        startActivity(getActivityOrApp(), extras, pkg, cls, options)
    }

    /**
     * 启动 Activity
     *
     * @param extras    extras
     * @param pkg       包名
     * @param cls       全类名
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    fun startActivity(
        @NonNull extras: Bundle,
        @NonNull pkg: String,
        @NonNull cls: String,
        @AnimRes enterAnim: Int,
        @AnimRes exitAnim: Int
    ) {
        val context = getActivityOrApp()
        startActivity(context, extras, pkg, cls, getOptionsBundle(context, enterAnim, exitAnim))
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context is Activity) {
            context.overridePendingTransition(enterAnim, exitAnim)
        }
    }

    /**
     * 启动 Activity
     *
     * @param activity activity
     * @param extras   extras
     * @param pkg      包名
     * @param cls      全类名
     */
    fun startActivity(
        @NonNull extras: Bundle,
        @NonNull activity: Activity,
        @NonNull pkg: String,
        @NonNull cls: String
    ) {
        startActivity(activity, extras, pkg, cls, null)
    }

    /**
     * 启动 Activity
     *
     * @param extras   extras
     * @param activity activity
     * @param pkg      包名
     * @param cls      全类名
     * @param options  动画
     */
    fun startActivity(
        @NonNull extras: Bundle,
        @NonNull activity: Activity,
        @NonNull pkg: String,
        @NonNull cls: String,
        @NonNull options: Bundle
    ) {
        startActivity(activity, extras, pkg, cls, options)
    }

    /**
     * 启动 Activity
     *
     * @param extras         extras
     * @param activity       activity
     * @param pkg            包名
     * @param cls            全类名
     * @param sharedElements 共享元素
     */
    fun startActivity(
        @NonNull extras: Bundle,
        @NonNull activity: Activity,
        @NonNull pkg: String,
        @NonNull cls: String,
        @NonNull vararg sharedElements: View
    ) {
        startActivity(activity, extras, pkg, cls, getOptionsBundle(activity, sharedElements))
    }

    /**
     * 启动 Activity
     *
     * @param extras    extras
     * @param pkg       包名
     * @param cls       全类名
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    fun startActivity(
        @NonNull extras: Bundle,
        @NonNull activity: Activity,
        @NonNull pkg: String,
        @NonNull cls: String,
        @AnimRes enterAnim: Int,
        @AnimRes exitAnim: Int
    ) {
        startActivity(activity, extras, pkg, cls, getOptionsBundle(activity, enterAnim, exitAnim))
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim)
        }
    }

    /**
     * 启动 Activity
     *
     * @param intent 意图
     */
    fun startActivity(@NonNull intent: Intent) {
        startActivity(intent, getActivityOrApp(), null)
    }

    /**
     * 启动 Activity
     *
     * @param intent  意图
     * @param options 跳转动画
     */
    fun startActivity(
        @NonNull intent: Intent,
        @NonNull options: Bundle
    ) {
        startActivity(intent, getActivityOrApp(), options)
    }

    /**
     * 启动 Activity
     *
     * @param intent    意图
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    fun startActivity(
        @NonNull intent: Intent,
        @AnimRes enterAnim: Int,
        @AnimRes exitAnim: Int
    ) {
        val context = getActivityOrApp()
        startActivity(intent, context, getOptionsBundle(context, enterAnim, exitAnim))
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context is Activity) {
            context.overridePendingTransition(enterAnim, exitAnim)
        }
    }

    /**
     * 启动 Activity
     *
     * @param activity activity
     * @param intent   意图
     */
    fun startActivity(
        @NonNull activity: Activity,
        @NonNull intent: Intent
    ) {
        startActivity(intent, activity, null)
    }

    /**
     * 启动 Activity
     *
     * @param activity activity
     * @param intent   意图
     * @param options  跳转动画
     */
    fun startActivity(
        @NonNull activity: Activity,
        @NonNull intent: Intent,
        @NonNull options: Bundle
    ) {
        startActivity(intent, activity, options)
    }

    /**
     * 启动 Activity
     *
     * @param activity       activity
     * @param intent         意图
     * @param sharedElements 共享元素
     */
    fun startActivity(
        @NonNull activity: Activity,
        @NonNull intent: Intent,
        @NonNull vararg sharedElements: View
    ) {
        startActivity(intent, activity, getOptionsBundle(activity, sharedElements))
    }

    /**
     * 启动 Activity
     *
     * @param activity  activity
     * @param intent    意图
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    fun startActivity(
        @NonNull activity: Activity,
        @NonNull intent: Intent,
        @AnimRes enterAnim: Int,
        @AnimRes exitAnim: Int
    ) {
        startActivity(intent, activity, getOptionsBundle(activity, enterAnim, exitAnim))
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim)
        }
    }

    /**
     * 启动多个 Activity
     *
     * @param intents 意图
     */
    fun startActivities(@NonNull intents: Array<Intent>) {
        startActivities(intents, getActivityOrApp(), null)
    }

    /**
     * 启动多个 Activity
     *
     * @param intents 意图
     * @param options 跳转动画
     */
    fun startActivities(
        @NonNull intents: Array<Intent>,
        @NonNull options: Bundle
    ) {
        startActivities(intents, getActivityOrApp(), options)
    }

    /**
     * 启动多个 Activity
     *
     * @param intents   意图
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    fun startActivities(
        @NonNull intents: Array<Intent>,
        @AnimRes enterAnim: Int,
        @AnimRes exitAnim: Int
    ) {
        val context = getActivityOrApp()
        startActivities(intents, context, getOptionsBundle(context, enterAnim, exitAnim))
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context is Activity) {
            context.overridePendingTransition(enterAnim, exitAnim)
        }
    }

    /**
     * 启动多个 Activity
     *
     * @param activity activity
     * @param intents  意图
     */
    fun startActivities(
        @NonNull activity: Activity,
        @NonNull intents: Array<Intent>
    ) {
        startActivities(intents, activity, null)
    }

    /**
     * 启动多个 Activity
     *
     * @param activity activity
     * @param intents  意图
     * @param options  跳转动画
     */
    fun startActivities(
        @NonNull activity: Activity,
        @NonNull intents: Array<Intent>,
        @NonNull options: Bundle?
    ) {
        startActivities(intents, activity, options)
    }

    /**
     * 启动多个 Activity
     *
     * @param activity  activity
     * @param intents   意图
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    fun startActivities(
        @NonNull activity: Activity,
        @NonNull intents: Array<Intent>,
        @AnimRes enterAnim: Int,
        @AnimRes exitAnim: Int
    ) {
        startActivities(intents, activity, getOptionsBundle(activity, enterAnim, exitAnim))
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim)
        }
    }

    private fun startActivities(
        intents: Array<Intent>,
        context: Context,
        options: Bundle?
    ) {
        if (context !is Activity) {
            for (intent in intents) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivities(intents, options)
        } else {
            context.startActivities(intents)
        }
    }

    /**
     * 回到桌面
     */
    fun startHomeActivity() {
        val homeIntent = Intent(Intent.ACTION_MAIN)
        homeIntent.addCategory(Intent.CATEGORY_HOME)
        startActivity(homeIntent)
    }


    /**
     * 共享元素
     */
    fun getOptionsBundle(activity: Activity, shareElements: Array<out View>): Bundle? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val len = shareElements.size
            val pairs = arrayOfNulls<Pair<View, String>>(len)
            for (i in shareElements.indices) {
                pairs[i] = Pair.create(shareElements[i], shareElements[i].transitionName)
            }
            return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *pairs).toBundle()
        }
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, null, null).toBundle()
    }
}