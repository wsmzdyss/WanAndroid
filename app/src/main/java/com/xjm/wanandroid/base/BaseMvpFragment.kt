package com.xjm.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.support.v4.toast

/**
 * Created by xjm on 2018/11/13.
 */
abstract class BaseMvpFragment<T : BasePresenter<*>> : BaseFragment(), BaseView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindPresenterView()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    lateinit var mPresenter: T

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError(text: String) {
        toast(text)
    }

    abstract fun bindPresenterView()


}