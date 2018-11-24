package com.xjm.wanandroid.ui.activity

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xjm.wanandroid.R
import com.xjm.wanandroid.base.BaseMvpActivity
import com.xjm.wanandroid.bean.response.Article
import com.xjm.wanandroid.bean.response.ArticleListResp
import com.xjm.wanandroid.presenter.CollectPresenter
import com.xjm.wanandroid.view.CollectView
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by xjm on 2018/11/24.
 */
class CollectActivity : BaseMvpActivity<CollectPresenter>(), CollectView {

    private lateinit var adapter: CollectAdapter

    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_collect)
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        //RecyclerView
        adapter = CollectAdapter()
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        recyclerView?.let {
            it.layoutManager = layoutManager
            it.adapter = adapter
            it.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        }
        adapter.apply {
            setOnLoadMoreListener({
                page++
                mPresenter.getCollectList(page, false)
            }, recyclerView)
        }

        //SwipeRefreshLayout
        swipeRefreshLayout.apply {
            setColorSchemeColors(resources.getColor(R.color.colorPrimary, null))
            setOnRefreshListener {
                initData()
            }
        }
    }

    private fun initData() {
        page = 0
        mPresenter.getCollectList(page)
    }

    override fun bindPresenterView() {
        mPresenter = CollectPresenter()
        mPresenter.mView = this
        mPresenter.lifecycle = this
    }

    override fun onCollectListResult(t: ArticleListResp, isRefresh: Boolean) {
        t.datas.let {
            adapter.run {
                if (isRefresh) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                val size = it.size
                if (size < t.size) {
                    loadMoreEnd(isRefresh)
                } else {
                    loadMoreComplete()
                }
            }
        }
        swipeRefreshLayout.isRefreshing = false
    }

    inner class CollectAdapter : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_artical_list) {
        override fun convert(helper: BaseViewHolder, item: Article) {
            helper.setText(R.id.tvTitle, item.title)
            helper.setText(R.id.tvAuthor, item.author)
            helper.setText(R.id.tvTime, item.niceDate)
            helper.setGone(R.id.checkBox, false)
            val chapterName = when {
                item.superChapterName.isNullOrEmpty().not() and item.chapterName.isNullOrEmpty().not() -> "${item.superChapterName} / ${item.chapterName}"
                item.superChapterName.isNullOrEmpty().not() -> item.superChapterName
                item.chapterName.isNullOrEmpty().not() -> item.chapterName
                else -> ""
            }
            helper.setText(R.id.tvChapter, chapterName)
        }
    }
}