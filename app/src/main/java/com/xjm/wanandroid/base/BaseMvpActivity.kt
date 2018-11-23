package com.xjm.wanandroid.base

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.xjm.wanandroid.common.BaseToolbarInterface
import org.jetbrains.anko.toast

/**
 * Created by xjm on 2018/11/2.
 */
abstract class BaseMvpActivity<T: BasePresenter<*>> : BaseActivity(), BaseView, BaseToolbarInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindPresenterView()
        mLoadingProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    }

    lateinit var mPresenter: T

    private val mLoadingProgressBar: ProgressDialog by lazy {
        ProgressDialog(this)
    }

    override fun showLoading() {
        mLoadingProgressBar.show()
    }

    override fun hideLoading() {
        mLoadingProgressBar.hide()
    }

    override fun onError(text: String) {
        toast(text)
    }

    abstract fun bindPresenterView()

    override fun initToolbar(toolbar: Toolbar, center: TextView, right: TextView, back: ImageView) {
    }
}