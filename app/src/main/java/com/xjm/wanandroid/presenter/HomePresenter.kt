package com.xjm.wanandroid.presenter

import com.xjm.wanandroid.base.BasePresenter
import com.xjm.wanandroid.base.BaseSubscriber
import com.xjm.wanandroid.bean.response.ArticleListResp
import com.xjm.wanandroid.bean.response.BannerResp
import com.xjm.wanandroid.net.ApiService
import com.xjm.wanandroid.net.RetrofitFactory
import com.xjm.wanandroid.utils.convert
import com.xjm.wanandroid.utils.execute
import com.xjm.wanandroid.view.HomeView

/**
 * Created by xjm on 2018/11/12.
 */
class HomePresenter : BasePresenter<HomeView>() {

    fun getBannerList() {
        mView.showLoading()
        RetrofitFactory.INSTANCE.create(ApiService::class.java)
            .getBannerList()
            .convert()
            .execute(object : BaseSubscriber<List<BannerResp>>(mView) {
                override fun onNext(t: List<BannerResp>) {
                    mView.onBannerListResult(t)
                }
            }, lifecycle)
    }

    fun getArticleList(page : Int, isRefresh : Boolean) {
        mView.showLoading()
        RetrofitFactory.INSTANCE.create(ApiService::class.java)
            .getArticleList(page)
            .convert()
            .execute(object : BaseSubscriber<ArticleListResp>(mView) {
                override fun onNext(t: ArticleListResp) {
                    mView.onArticleListResult(t, isRefresh)
                }
            }, lifecycle)
    }

}