package com.xjm.wanandroid.presenter

import com.xjm.wanandroid.base.BasePresenter
import com.xjm.wanandroid.base.BaseSubscriber
import com.xjm.wanandroid.bean.response.KnowChildren
import com.xjm.wanandroid.net.ApiService
import com.xjm.wanandroid.net.RetrofitFactory
import com.xjm.wanandroid.utils.convert
import com.xjm.wanandroid.utils.execute
import com.xjm.wanandroid.view.WechatView

/**
 * Created by xjm on 2018/11/24.
 */
class WechatPresenter : BasePresenter<WechatView>() {

    fun getWechatTree(){
        mView.showLoading()
        RetrofitFactory.INSTANCE.create(ApiService::class.java)
            .getWechatTree()
            .convert()
            .execute(object : BaseSubscriber<List<KnowChildren>>(mView){
                override fun onNext(t: List<KnowChildren>) {
                    mView.onWechatTreeResult(t)
                }
            }, lifecycle)
    }

}