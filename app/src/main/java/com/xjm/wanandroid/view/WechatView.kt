package com.xjm.wanandroid.view

import com.xjm.wanandroid.base.BaseView
import com.xjm.wanandroid.bean.response.ArticleListResp
import com.xjm.wanandroid.bean.response.KnowChildren

/**
 * Created by xjm on 2018/11/24.
 */
interface WechatView : BaseView {
    fun onWechatTreeResult(t : List<KnowChildren>)
}