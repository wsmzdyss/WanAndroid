package com.xjm.wanandroid.presenter

import com.xjm.wanandroid.base.BasePresenter
import com.xjm.wanandroid.base.BaseSubscriber
import com.xjm.wanandroid.bean.response.ArticleListResp
import com.xjm.wanandroid.bean.response.Guide
import com.xjm.wanandroid.net.ApiService
import com.xjm.wanandroid.net.RetrofitFactory
import com.xjm.wanandroid.utils.convert
import com.xjm.wanandroid.utils.execute
import com.xjm.wanandroid.view.GuideView
import com.xjm.wanandroid.view.KnowChildView

/**
 * Created by xjm on 2018/11/24.
 */
class GuidePresenter : BasePresenter<GuideView>() {

    fun getNaviTree(){
        mView.showLoading()
        RetrofitFactory.INSTANCE.create(ApiService::class.java)
            .getNaviTree()
            .convert()
            .execute(object : BaseSubscriber<List<Guide>>(mView){
                override fun onNext(t: List<Guide>) {
                    mView.onGuideResult(t)
                }
            }, lifecycle)
    }

}