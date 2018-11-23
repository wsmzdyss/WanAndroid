package com.xjm.wanandroid.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.xjm.wanandroid.R
import com.xjm.wanandroid.utils.Preference
import org.jetbrains.anko.dip
import java.util.prefs.Preferences

/**
 * Created by xjm on 2018/11/12.
 */
class MyApplication : Application(), Application.ActivityLifecycleCallbacks {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        registerActivityLifecycleCallbacks(this)
        Preference.setContext(this)
    }

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

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is BaseToolbarInterface)
            initToolbar(activity)
    }

    //Bar 默认高度为 56 dp
    private val ACTION_BAR_SIZE_DIP = 56

    fun initToolbar(activity: Activity) {

        //Toolbar 的 layout
        val linearLayout = LayoutInflater.from(activity).inflate(R.layout.view_base_toolbar, null) as LinearLayout
        //Activity 的根布局
        val rootLayout = activity.findViewById<FrameLayout>(android.R.id.content)
        //Toolbar 实例
        val toolbar = linearLayout.findViewById<Toolbar>(R.id.toolbar_base)

        //没有 removeView 将不能 rootLayout.addView()
        linearLayout.removeView(toolbar)

        rootLayout.addView(toolbar)
        //获取到 activity 根布局下的第一个子布局(也就是 xml 中的根布局)并设置 paddingTop,否则 Toolbar 会遮住下面的布局
        rootLayout.getChildAt(0).setPadding(0, dip(ACTION_BAR_SIZE_DIP), 0, 0)

        if (toolbar != null) {
            //找到 Toolbar 并且替换 Actionbar
            if (activity is AppCompatActivity) {
                activity.setSupportActionBar(toolbar)
                activity.supportActionBar!!.setDisplayShowTitleEnabled(false)
            } else {
                //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    activity.setActionBar(activity.findViewById(R.id.toolbar_base))
                    activity.actionBar!!.setDisplayShowTitleEnabled(false)
                //}
            }
        }
        //初始化标题
        val center = activity.findViewById<TextView>(R.id.title_center_base)
        if (center != null) {
            (activity.findViewById<TextView>(R.id.title_center_base)).text = activity.title
        }
        val right = activity.findViewById<TextView>(R.id.title_right_base)
//        if (right != null) {
//            (activity.findViewById<TextView>(R.id.title_right_base)).text = activity.title
//        }
        //初始化返回按钮
        //找到 Toolbar 的返回按钮,并且设置点击事件,点击关闭这个 Activity
        val back = activity.findViewById<ImageView>(R.id.button_back_base)
        back.setOnClickListener { activity.onBackPressed() }

        //放在最后防止在 initToolbar 的设置被上面的覆盖
        if (toolbar != null && right != null && center != null)
            (activity as BaseToolbarInterface).initToolbar(toolbar, center, right, back)
    }
}