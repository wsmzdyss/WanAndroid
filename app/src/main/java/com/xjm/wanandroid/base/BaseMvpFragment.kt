package com.xjm.wanandroid.base

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xjm.wanandroid.R
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by xjm on 2018/11/13.
 */
abstract class BaseMvpFragment<T : BasePresenter<*>> : BaseFragment(), BaseView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        bindPresenterView()
        swipeRefreshLayout = mRootView.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout?.apply {
            setColorSchemeColors(context!!.getColor(R.color.colorPrimary))
            setOnRefreshListener {
                refreshData()
            }
        }
        return mRootView
    }

    var swipeRefreshLayout: SwipeRefreshLayout? = null

    lateinit var mPresenter: T

    override fun showLoading() {
        swipeRefreshLayout?.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun onError(text: String) {
        toast(text)
    }

    abstract fun bindPresenterView()

    protected open fun refreshData() {}

}