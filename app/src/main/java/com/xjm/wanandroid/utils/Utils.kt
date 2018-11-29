package com.xjm.wanandroid.utils

import com.xjm.wanandroid.base.BaseResponse
import com.xjm.wanandroid.net.ApiService
import com.xjm.wanandroid.net.RetrofitFactory
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by xjm on 2018/11/22.
 */
object Utils {
    fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies
            .map { cookie ->
                cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            }
            .forEach {
                it.filterNot { set.contains(it) }.forEach { set.add(it) }
            }
        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }
        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }
        return sb.toString()
    }

    fun encodeUsername(cookie: String): String {
        return cookie
            .split(";")
            .filter { it.contains("UserName") }[0]
            .split("=")[1]
    }

    fun addCollect(id: Int) {
        RetrofitFactory.INSTANCE.create(ApiService::class.java)
            .addCollect(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse<Any>> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseResponse<Any>) {
                }

                override fun onError(e: Throwable) {
                }

            })
    }

    fun cancelCollect(id: Int) {
        RetrofitFactory.INSTANCE.create(ApiService::class.java)
            .cancelCollect(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse<Any>> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseResponse<Any>) {
                }

                override fun onError(e: Throwable) {
                }

            })
    }

}