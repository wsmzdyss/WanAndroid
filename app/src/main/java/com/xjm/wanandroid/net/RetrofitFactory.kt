package com.xjm.wanandroid.net

import com.xjm.wanandroid.common.MyApplication
import com.xjm.wanandroid.utils.Constant
import com.xjm.wanandroid.utils.Preference
import com.xjm.wanandroid.utils.Utils
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by xjm on 2018/11/2.
 */
class RetrofitFactory private constructor() {

    private val headerInterceptor: Interceptor
    private val cookieInterceptor: Interceptor
    private val retrofit: Retrofit

    companion object {
        val INSTANCE: RetrofitFactory by lazy { RetrofitFactory() }
    }

    init {

        headerInterceptor = Interceptor {
            val request = it.request()
            val builder = request.newBuilder()
            builder
                .addHeader("Content_Type", "application/json")
                .addHeader("charset", "UTF-8")

            //cookie
            val domain = request.url().host()
            val url = request.url().toString()
            if (domain.isNotEmpty() && (
                        url.contains(Constant.COLLECTIONS_WEBSITE)
                                || url.contains(Constant.UNCOLLECTIONS_WEBSITE)
                                || url.contains(Constant.TODO_WEBSITE)
                                || url.contains(Constant.ARTICLE_WEBSITE))
            ) {
                val cookie: String by Preference(domain, "")
                if (cookie.isNotEmpty()) {
                    builder.addHeader(Constant.COOKIE_NAME, cookie)
                }
            }
            it.proceed(builder.build())
        }

        cookieInterceptor = Interceptor {
            val request = it.request()
            val response = it.proceed(request)
            val domain = request.url().host()
            val url = request.url().toString()

            if (response.headers(Constant.SET_COOKIE_KEY).isNotEmpty() && (url.contains(Constant.SAVE_USER_LOGIN_KEY)
                        || url.contains(Constant.SAVE_USER_REGISTER_KEY) || url.contains(Constant.SAVE_USER_LOGOUT_KEY))
            ) {
                val cookies = response.headers(Constant.SET_COOKIE_KEY)
                val cookie = Utils.encodeCookie(cookies)
                var spUrl by Preference(url, "")
                spUrl = cookie
                var spDomain by Preference(domain, "")
                spDomain = cookie
                var spUsername by Preference(Constant.USERNAME, "")
                spUsername = Utils.encodeUsername(cookie)
            }

            response
        }

        retrofit = Retrofit.Builder()
            .baseUrl(Constant.REQUEST_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(initClient())
            .build()
    }

    private fun initClient(): OkHttpClient {
        val cacheFile = File(MyApplication.context.cacheDir, "cache")
        val cache = Cache(cacheFile, Constant.CACHE_SIZE)

        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(cookieInterceptor)
            .addInterceptor(initLogInterceptor())
            .cache(cache)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    private fun initLogInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

}