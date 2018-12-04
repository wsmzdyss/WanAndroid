package com.xjm.wanandroid.bean.response

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by xjm on 2018/11/12.
 */
data class ArticleListResp(
    val curPage: Int,
    val datas: List<Article>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class Article(
    val apkLink: String,
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val projectLink: String,
    val publishTime: Long,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<Tag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
) : MultiItemEntity {
    override fun getItemType(): Int = if (envelopePic.isNotBlank() && desc.isNotBlank()) 1 else 0
}

data class Tag(
    val name: String,
    val url: String
)

data class Guide(val cid: Int, val name: String, val articles: List<Article>)