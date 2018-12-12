package com.xjm.wanandroid.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.xjm.wanandroid.R
import com.xjm.wanandroid.adapter.ArticleAdapter
import com.xjm.wanandroid.base.BaseMvpFragment
import com.xjm.wanandroid.bean.response.Article
import com.xjm.wanandroid.bean.response.ArticleListResp
import com.xjm.wanandroid.bean.response.BannerResp
import com.xjm.wanandroid.presenter.HomePresenter
import com.xjm.wanandroid.ui.activity.ArticleActivity
import com.xjm.wanandroid.utils.Constant
import com.xjm.wanandroid.view.HomeView
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.startActivity


/**
 * Created by xjm on 2018/11/15.
 */
class HomeFragment : BaseMvpFragment<HomePresenter>(), HomeView {

    override fun attachLayoutRes(): Int = R.layout.fragment_home

    private var titleList = arrayListOf<String>()
    private var imageList = arrayListOf<String>()
    private var bannerList = arrayListOf<BannerResp>()
    private var articleList = arrayListOf<Article>()

    private lateinit var banner: Banner
    private lateinit var parentView: View

    private lateinit var adapter: ArticleAdapter

    private var page = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    override fun initView() {
        //Banner
        parentView = LayoutInflater.from(context).inflate(R.layout.item_home_banner, null)
        banner = parentView.findViewById(R.id.banner)
        banner.setOnBannerListener {
            startActivity<ArticleActivity>("webUrl" to bannerList[it].url, "webTitle" to bannerList[it].title)
        }

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
            addHeaderView(parentView)
            setOnLoadMoreListener({
                page++
                mPresenter.getArticleList(page, false)
            }, recyclerView)
            setOnItemClickListener { _, _, position ->
                startActivity<ArticleActivity>("webUrl" to data[position].link, "webTitle" to data[position].title)
            }
        }

    }

    private fun initData() {
        mPresenter.getBannerList()
    }

    private fun initBanner() {
        banner.apply {
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
            setImageLoader(GlideImageLoader())
            setImages(imageList)
            setBannerTitles(titleList)
        }.start()
        mPresenter.getArticleList(page)
    }

    override fun bindPresenterView() {
        mPresenter = HomePresenter()
        mPresenter.mView = this
        mPresenter.lifecycle = this
    }

    override fun onBannerListResult(t: List<BannerResp>) {
        page = 0
        titleList.clear()
        imageList.clear()
        bannerList.clear()
        bannerList.addAll(t)
        for (it in t) {
            titleList.add(it.title)
            imageList.add(it.imagePath)
        }
        initBanner()
    }

    override fun onArticleListResult(t: ArticleListResp, isRefresh: Boolean) {
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

    inner class GlideImageLoader : ImageLoader() {
        override fun displayImage(context: Context, path: Any, imageView: ImageView) {
            Glide.with(context).load(path).into(imageView)
        }
    }

    override fun refreshData() {
        initData()
    }

}