package com.xjm.wanandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.xjm.wanandroid.R
import com.xjm.wanandroid.adapter.ArticleAdapter
import com.xjm.wanandroid.adapter.ProjectAdapter
import com.xjm.wanandroid.base.BaseMvpFragment
import com.xjm.wanandroid.bean.response.Article
import com.xjm.wanandroid.bean.response.ArticleListResp
import com.xjm.wanandroid.presenter.ProjectChildPresenter
import com.xjm.wanandroid.ui.activity.ArticleActivity
import com.xjm.wanandroid.utils.Constant
import com.xjm.wanandroid.view.ProjectChildView
import kotlinx.android.synthetic.main.fragment_project_child_list.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by xjm on 2018/11/28.
 */
class ProjectChildFragment : BaseMvpFragment<ProjectChildPresenter>(), ProjectChildView {
    override fun attachLayoutRes(): Int = R.layout.fragment_project_child_list

    private var articleList = arrayListOf<Article>()

    companion object {
        fun getInstance(cid: Int) : ProjectChildFragment {
            val fragment = ProjectChildFragment()
            val args = Bundle()
            args.putInt(Constant.CONTENT_CID_KEY, cid)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var adapter: ArticleAdapter

    private var page = 1

    private var cid = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        cid = arguments?.getInt(Constant.CONTENT_CID_KEY) ?: 0
        initData()
    }

    private fun initView() {
        //RecyclerView
        adapter = ArticleAdapter(articleList)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        recyclerView?.let {
            it.layoutManager = layoutManager
            it.adapter = adapter
            it.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        adapter.apply {
            setOnLoadMoreListener({
                page++
                mPresenter.getProjectList(page, cid, false)
            }, recyclerView)
            setOnItemClickListener { _, _, position ->
                startActivity<ArticleActivity>("webUrl" to data[position].link, "webTitle" to data[position].title)
            }
        }

    }

    private fun initData() {
        page = 1
        mPresenter.getProjectList(page, cid)
    }

    override fun bindPresenterView() {
        mPresenter = ProjectChildPresenter()
        mPresenter.mView = this
        mPresenter.lifecycle = this
    }

    override fun onProjectListResult(t: ArticleListResp, isRefresh: Boolean) {
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
    }

    override fun refreshData() {
        initData()
    }
}