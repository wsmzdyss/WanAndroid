package com.xjm.wanandroid.base

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscriber
import java.util.function.Consumer


/**
 * Created by xjm on 2018/11/2.
 */
open class BaseSubscriber<T>(private val baseView: BaseView): Observer<T> {

    private lateinit var mDisposable : Disposable
    override fun onComplete() {
        baseView.hideLoading()
    }

    override fun onSubscribe(d: Disposable) {
        mDisposable = d
    }

    override fun onNext(t: T) {}

    override fun onError(e: Throwable) {
        baseView.hideLoading()
        baseView.onError(e.message.toString())
    }
}