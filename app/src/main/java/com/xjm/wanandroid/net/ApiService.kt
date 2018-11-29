package com.xjm.wanandroid.net

import com.xjm.wanandroid.base.BaseResponse
import com.xjm.wanandroid.bean.response.*
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

    @POST("/lg/collect/{id}/json")
    fun addCollect(@Path("id") id: Int) : Observable<BaseResponse<Any>>

    @POST("/lg/uncollect_originId/{id}/json")
    fun cancelCollect(@Path("id") id: Int) : Observable<BaseResponse<Any>>

    @GET("/tree/json")
    fun getKnowTree() : Observable<BaseResponse<List<KnowChildren>>>

    @GET("/article/list/{page}/json")
    fun getKnowList(@Path("page") page: Int, @Query("cid") cid: Int) : Observable<BaseResponse<ArticleListResp>>

    @GET("/wxarticle/chapters/json")
    fun getWechatTree() : Observable<BaseResponse<List<KnowChildren>>>

    @GET("/wxarticle/list/{cid}/{page}/json")
    fun getWechatList(@Path("page") page: Int, @Path("cid") cid: Int) : Observable<BaseResponse<ArticleListResp>>
}