package com.xjm.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.components.support.RxFragment
import com.xjm.wanandroid.utils.Constant
import com.xjm.wanandroid.utils.Preference
import org.greenrobot.eventbus.EventBus

/**
 * Created by xjm on 2018/11/13.
 */
abstract class BaseFragment : RxFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(attachLayoutRes(), container, false)
        return mRootView
    }

    protected lateinit var mRootView : View

    abstract fun attachLayoutRes(): Int

    protected var isLogin : Boolean by Preference(Constant.IS_LOGIN, false)

    protected val username : String by Preference(Constant.USERNAME, "")

    //是否注册EventBus
    protected var isEventBusRegister : Boolean = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (isEventBusRegister) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isEventBusRegister) {
            EventBus.getDefault().unregister(this)
        }
    }
}