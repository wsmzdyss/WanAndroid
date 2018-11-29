package com.xjm.wanandroid.view

import com.xjm.wanandroid.base.BaseView
import com.xjm.wanandroid.bean.response.ArticleListResp

/**
 * Created by xjm on 2018/11/24.
 */
interface WechatChildView : BaseView {
    fun onWechatListResult(t : ArticleListResp, isRefresh: Boolean)
}