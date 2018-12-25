package com.xjm.wanandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xjm.wanandroid.R
import com.xjm.wanandroid.adapter.ArticleAdapter
import com.xjm.wanandroid.base.BaseMvpFragment
import com.xjm.wanandroid.base.BasePagerMvpFragment
import com.xjm.wanandroid.bean.response.Article
import com.xjm.wanandroid.bean.response.ArticleListResp
import com.xjm.wanandroid.presenter.WechatChildPresenter
import com.xjm.wanandroid.ui.activity.ArticleActivity
import com.xjm.wanandroid.utils.Constant
import com.xjm.wanandroid.utils.Utils
import com.xjm.wanandroid.view.WechatChildView
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by xjm on 2018/11/28.
 */
class WechatChildFragment : BasePagerMvpFragment<WechatChildPresenter>(), WechatChildView {
    override fun attachLayoutRes(): Int = R.layout.fragment_wechat_child_list

    private var articleList = arrayListOf<Article>()

    companion object {
        fun getInstance(cid: Int) : WechatChildFragment {
            val fragment = WechatChildFragment()
            val args = Bundle()
            args.putInt(Constant.CONTENT_CID_KEY, cid)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var adapter: ArticleAdapter

    private var page = 1

    private var cid = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        cid = arguments?.getInt(Constant.CONTENT_CID_KEY) ?: 0
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {
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
                mPresenter.getWechatList(page, cid, false)
            }, recyclerView)
            setOnItemClickListener { _, _, position ->
                startActivity<ArticleActivity>("webUrl" to data[position].link, "webTitle" to data[position].title)
            }
        }

    }

    override fun lazyLoad() {
        initData()
    }

    private fun initData() {
        page = 1
        mPresenter.getWechatList(page, cid)
    }

    override fun bindPresenterView() {
        mPresenter = WechatChildPresenter()
        mPresenter.mView = this
        mPresenter.lifecycle = this
    }

    override fun onWechatListResult(t: ArticleListResp, isRefresh: Boolean) {
        Utils.refreshAndLoadArticle(t, isRefresh, adapter)
    }

    override fun refreshData() {
        initData()
    }
}