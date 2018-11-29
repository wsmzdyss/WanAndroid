package com.xjm.wanandroid.presenter

import com.xjm.wanandroid.base.BasePresenter
import com.xjm.wanandroid.base.BaseSubscriber
import com.xjm.wanandroid.bean.response.ArticleListResp
import com.xjm.wanandroid.net.ApiService
import com.xjm.wanandroid.net.RetrofitFactory
import com.xjm.wanandroid.utils.convert
import com.xjm.wanandroid.utils.execute
import com.xjm.wanandroid.view.KnowChildView

/**
 * Created by xjm on 2018/11/24.
 */
class KnowChildPresenter : BasePresenter<KnowChildView>() {

    fun getKnowList(page: Int, cid: Int, isRefresh: Boolean = true){
        mView.showLoading()
        RetrofitFactory.INSTANCE.create(ApiService::class.java)
            .getKnowList(page, cid)
            .convert()
            .execute(object : BaseSubscriber<ArticleListResp>(mView){
                override fun onNext(t: ArticleListResp) {
                    mView.onKnowListResult(t, isRefresh)
                }
            }, lifecycle)
    }

}