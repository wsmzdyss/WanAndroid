package com.xjm.wanandroid.presenter

import com.xjm.wanandroid.base.BasePresenter
import com.xjm.wanandroid.base.BaseSubscriber
import com.xjm.wanandroid.bean.response.ArticleListResp
import com.xjm.wanandroid.bean.response.LoginResp
import com.xjm.wanandroid.net.ApiService
import com.xjm.wanandroid.net.RetrofitFactory
import com.xjm.wanandroid.utils.convert
import com.xjm.wanandroid.utils.execute
import com.xjm.wanandroid.view.LoginView

/**
 * Created by xjm on 2018/11/12.
 */
class LoginPresenter : BasePresenter<LoginView>() {

    fun login(username: String, password: String) {
        mView.showLoading()
        RetrofitFactory.INSTANCE.create(ApiService::class.java)
            .login(username, password)
            .convert()
            .execute(object : BaseSubscriber<LoginResp>(mView) {
                override fun onNext(t: LoginResp) {
                    mView.onLoginResult(t)
                }
            }, lifecycle)
    }

    fun register(username: String, password: String, repassword: String) {
        mView.showLoading()
        RetrofitFactory.INSTANCE.create(ApiService::class.java)
            .register(username, password, repassword)
            .convert()
            .execute(object : BaseSubscriber<LoginResp>(mView) {
                override fun onNext(t: LoginResp) {
                    mView.onRegisterResult(t)
                }
            }, lifecycle)
    }

    fun getCollectList() {
        mView.showLoading()
        RetrofitFactory.INSTANCE.create(ApiService::class.java)
            .getCollectList(0)
            .convert()
            .execute(object : BaseSubscriber<ArticleListResp>(mView) {
                override fun onNext(t: ArticleListResp) {
                    mView.onArticleResult(t)
                }
            }, lifecycle)
    }
}