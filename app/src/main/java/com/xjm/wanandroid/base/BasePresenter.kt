package com.xjm.wanandroid.base

import com.trello.rxlifecycle2.LifecycleProvider


/**
 * Created by xjm on 2018/11/2.
 */
abstract class BasePresenter<T: BaseView> {
    lateinit var mView: T

    lateinit var lifecycle: LifecycleProvider<*>
}