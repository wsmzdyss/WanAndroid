package com.xjm.wanandroid.utils

import android.widget.Button
import android.widget.EditText
import com.trello.rxlifecycle2.LifecycleProvider
import com.xjm.wanandroid.base.BaseSubscriber
import com.xjm.wanandroid.base.DefaultTextWatcher
import com.xjm.wanandroid.base.BaseResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Created by xjm on 2018/11/2.
 */
fun <T> Observable<BaseResponse<T>>.convert(): Observable<T> {
    return this.flatMap(object : Function<BaseResponse<T>, Observable<T>> {
        override fun apply(t: BaseResponse<T>): Observable<T> {
            return if (t.errorCode != 0) {
                Observable.error(Throwable(t.errorMsg))
            } else {
                Observable.just(t.data)
            }
        }
    })
}


fun <T> Observable<T>.execute(subscriber: BaseSubscriber<T>, lifecycle: LifecycleProvider<*>) {
    this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(lifecycle.bindToLifecycle())
        .subscribe(subscriber)
}

fun Button.textEnable(et: EditText, method: () -> Boolean) {
    val btn = this
    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            btn.isEnabled = method()
        }
    })
}
