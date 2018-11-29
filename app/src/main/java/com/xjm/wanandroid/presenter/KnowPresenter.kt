package com.xjm.wanandroid.presenter

import com.xjm.wanandroid.base.BasePresenter
import com.xjm.wanandroid.base.BaseSubscriber
import com.xjm.wanandroid.bean.response.KnowChildren
import com.xjm.wanandroid.net.ApiService
import com.xjm.wanandroid.net.RetrofitFactory
import com.xjm.wanandroid.utils.convert
import com.xjm.wanandroid.utils.execute
import com.xjm.wanandroid.view.KnowView

/**
 * Created by xjm on 2018/11/24.
 */
class KnowPresenter : BasePresenter<KnowView>() {

    fun getKnowTree(){
        mView.showLoading()
        RetrofitFactory.INSTANCE.create(ApiService::class.java)
            .getKnowTree()
            .convert()
            .execute(object : BaseSubscriber<List<KnowChildren>>(mView){
                override fun onNext(t: List<KnowChildren>) {
                    mView.onKnowTreeResult(t)
                }
            }, lifecycle)
    }

}