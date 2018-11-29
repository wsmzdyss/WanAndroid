package com.xjm.wanandroid.adapter

import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xjm.wanandroid.R
import com.xjm.wanandroid.bean.response.Article
import com.xjm.wanandroid.utils.Utils

class ArticleAdapter : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_artical_list) {
        override fun convert(helper: BaseViewHolder, item: Article) {
            helper.setText(R.id.tvTitle, item.title)
            helper.setText(R.id.tvAuthor, item.author)
            helper.setText(R.id.tvTime, item.niceDate)
            val chapterName = when {
                item.superChapterName.isNullOrEmpty().not() and item.chapterName.isNullOrEmpty().not() -> "${item.superChapterName} / ${item.chapterName}"
                item.superChapterName.isNullOrEmpty().not() -> item.superChapterName
                item.chapterName.isNullOrEmpty().not() -> item.chapterName
                else -> ""
            }
            helper.setText(R.id.tvChapter, chapterName)
            helper.setChecked(R.id.checkBox, item.collect)
            val checkBox = helper.getView<CheckBox>(R.id.checkBox)
            checkBox.setOnClickListener {
                if (checkBox.isChecked) {
                    Utils.addCollect(item.id)
                } else {
                    Utils.cancelCollect(item.id)
                }
            }
        }
    }