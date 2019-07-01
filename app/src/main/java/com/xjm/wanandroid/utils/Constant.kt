package com.xjm.wanandroid.utils

/**
 * Created by xjm on 2018/11/13.
 */
object Constant {
    /**
     * baseUrl
     */
    const val REQUEST_BASE_URL = "https://www.wanandroid.com"
    /**
     * Share preferences name
     */
    const val SHARED_NAME = "_preferences"
    const val IS_LOGIN = "is_login"
    const val USERNAME = "username"
    const val SAVE_USER_LOGIN_KEY = "user/login"
    const val SAVE_USER_LOGOUT_KEY = "user/logout"
    const val SAVE_USER_REGISTER_KEY = "user/register"

    const val CACHE_SIZE: Long = 50 * 1024 * 1024

    const val COLLECTIONS_WEBSITE = "lg/collect"
    const val UNCOLLECTIONS_WEBSITE = "lg/uncollect"
    const val ARTICLE_WEBSITE = "article"
    const val TODO_WEBSITE = "lg/todo"

    const val COOKIE_NAME = "Cookie"
    const val SET_COOKIE_KEY = "set-cookie"

    const val CONTENT_CID_KEY = "cid"

    const val MULTIPLEITEM_ARTICLE = 0
    const val MULTIPLEITEM_PROJECT = 1
}