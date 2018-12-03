package com.xjm.wanandroid.adapter

import android.text.Html
import android.widget.CheckBox
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xjm.wanandroid.R
import com.xjm.wanandroid.bean.response.Article
import com.xjm.wanandroid.utils.Constant
import com.xjm.wanandroid.utils.Utils

class ArticleAdapter(list: List<Article>) : BaseMultiItemQuickAdapter<Article, BaseViewHolder>(list) {

    init {
        addItemType(Constant.MULTIPLEITEM_ARTICLE, R.layout.item_artical_list)
        addItemType(Constant.MULTIPLEITEM_PROJECT, R.layout.item_project_list)
    }

    override fun convert(helper: BaseViewHolder, item: Article) {
        if (helper.itemViewType == Constant.MULTIPLEITEM_PROJECT) {
            helper.setText(R.id.tvContent, item.desc)
            Glide.with(mContext).load(item.envelopePic).into(helper.getView(R.id.imgPic))
        }

        helper.setText(R.id.tvTitle, Html.fromHtml(item.title))
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