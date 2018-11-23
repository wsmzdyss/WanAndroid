package com.xjm.wanandroid.net

import com.xjm.wanandroid.bean.BaseResponse
import com.xjm.wanandroid.bean.response.ArticleListResp
import com.xjm.wanandroid.bean.response.BannerResp
import com.xjm.wanandroid.bean.response.LoginResp
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by xjm on 2018/11/12.
 */
interface ApiService {

    @GET("/banner/json")
    fun getBannerList() : Observable<BaseResponse<List<BannerResp>>>

    @GET("/article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int) : Observable<BaseResponse<ArticleListResp>>

    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("username") username: String, @Field("password") password: String) : Observable<BaseResponse<LoginResp>>

    @FormUrlEncoded
    @POST("/user/register")
    fun register(@Field("username") username: String, @Field("password") password: String, @Field("repassword") repassword: String) : Observable<BaseResponse<LoginResp>>

    @GET("/lg/collect/list/{page}/json")
    fun getCollectList(@Path("page") page: Int) : Observable<BaseResponse<ArticleListResp>>
}