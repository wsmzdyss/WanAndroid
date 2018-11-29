package com.xjm.wanandroid.bean.response

import java.io.Serializable

data class KnowChildren(
    val children: List<KnowChildren>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
): Serializable