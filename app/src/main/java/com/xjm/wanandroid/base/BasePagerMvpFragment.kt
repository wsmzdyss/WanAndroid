package com.xjm.wanandroid.base

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xjm.wanandroid.R
import org.jetbrains.anko.support.v4.dip
import org.jetbrains.anko.support.v4.toast

/**
 * Created by xjm on 2018/11/13.
 */
abstract class BasePagerMvpFragment<T : BasePresenter<*>> : BaseMvpFragment<T>(), BaseView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCreateView = true
        preLazyLoad()
    }

    private var isCreateView = false
    private var isUserVisible = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            isUserVisible = true
            preLazyLoad()
        } else {
            isUserVisible = false
        }
    }

    private fun preLazyLoad() {
        if (isCreateView && isUserVisible) {
            lazyLoad()

            isUserVisible = false
            isCreateView = false
        }
    }

    abstract fun lazyLoad()

}