package com.xjm.wanandroid.view

import com.xjm.wanandroid.base.BaseView
import com.xjm.wanandroid.bean.response.ArticleListResp
import com.xjm.wanandroid.bean.response.BannerResp

/**
 * Created by xjm on 2018/11/12.
 */
interface HomeView : BaseView {
    fun onBannerListResult(t : List<BannerResp>)

    fun onArticleListResult(t : ArticleListResp, isRefresh : Boolean)

    fun onAddCollectResult()

    fun onCancelCollectResult()
}