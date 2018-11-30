package com.xjm.wanandroid.base

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.xjm.wanandroid.R
import com.xjm.wanandroid.common.BaseToolbarInterface
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.toast

/**
 * Created by xjm on 2018/11/2.
 */
abstract class BaseMvpActivity<T: BasePresenter<*>> : BaseActivity(), BaseView, BaseToolbarInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindPresenterView()
        swipeRefreshLayout = contentView.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout?.apply {
            setColorSchemeColors(getColor(R.color.colorPrimary))
            setOnRefreshListener {
                refreshData()
            }
        }
    }

    var swipeRefreshLayout : SwipeRefreshLayout? = null

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

    override fun initToolbar(toolbar: Toolbar, center: TextView, right: TextView, back: ImageView) {
    }
}