package com.xjm.wanandroid.view

import com.xjm.wanandroid.base.BaseView
import com.xjm.wanandroid.bean.response.ArticleListResp
import com.xjm.wanandroid.bean.response.LoginResp

/**
 * Created by xjm on 2018/11/12.
 */
interface LoginView : BaseView {
    fun onLoginResult(t : LoginResp)

    fun onRegisterResult(t : LoginResp)
}