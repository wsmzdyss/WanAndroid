package com.xjm.wanandroid.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xjm.wanandroid.R
import com.xjm.wanandroid.base.BaseMvpFragment
import com.xjm.wanandroid.bean.response.Article
import com.xjm.wanandroid.bean.response.Guide
import com.xjm.wanandroid.presenter.GuidePresenter
import com.xjm.wanandroid.ui.activity.ArticleActivity
import com.xjm.wanandroid.utils.Utils
import com.xjm.wanandroid.view.GuideView
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.fragment_guide.*
import org.jetbrains.anko.support.v4.dip
import org.jetbrains.anko.support.v4.startActivity
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView
import q.rorbin.verticaltablayout.widget.TabView

/**
 * Created by xjm on 2018/12/4.
 */
class GuideFragment : BaseMvpFragment<GuidePresenter>(), GuideView {

    private val guideList = arrayListOf<Guide>()

    private lateinit var adapter : GuideAdapter

    private val layoutManager by lazy { LinearLayoutManager(context) }

    private var bScroll: Boolean = false

    private var bClickTab: Boolean = false

    private var currentIndex = 0

    override fun bindPresenterView() {
        mPresenter = GuidePresenter()
        mPresenter.mView = this
        mPresenter.lifecycle = this
    }

    override fun onGuideResult(t: List<Guide>) {
        guideList.addAll(t)
        initView()
    }

    private fun initView() {
        vTabLayout.apply {
            setTabAdapter(NavigationTabAdapter(context, guideList))
            addOnTabSelectedListener(object : VerticalTabLayout.OnTabSelectedListener {
                override fun onTabReselected(tabView: TabView?, position: Int) {

                }

                override fun onTabSelected(tabView: TabView?, position: Int) {
                    bClickTab = true
                    selectTab(position)
                }
            })
        }

        //RecyclerView
        adapter = GuideAdapter()

        recyclerView?.let {
            it.layoutManager = layoutManager
            it.adapter = adapter
            it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (bScroll && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
                        scrollRecyclerView()
                    }
                    rightLinkLeft(newState)
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (bScroll) {
                        scrollRecyclerView()
                    }
                }
            })
        }

    }

    /**
     * Right RecyclerView link Left TabLayout
     *
     * @param newState RecyclerView Scroll State
     */
    private fun rightLinkLeft(newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (bClickTab) {
                bClickTab = false
                return
            }
            val firstPosition: Int = layoutManager.findFirstVisibleItemPosition()
            if (firstPosition != currentIndex) {
                currentIndex = firstPosition
                setChecked(currentIndex)
            }
        }
    }

    /**
     * Smooth Right RecyclerView to Select Left TabLayout
     *
     * @param position checked position
     */
    private fun setChecked(position: Int) {
        if (bClickTab) {
            bClickTab = false
        } else {
            vTabLayout.setTabSelected(currentIndex)
        }
        currentIndex = position
    }

    /**
     * Select Left TabLayout to Smooth Right RecyclerView
     */
    private fun selectTab(position: Int) {
        currentIndex = position
        recyclerView.stopScroll()
        smoothScrollToPosition(position)
    }

    private fun scrollRecyclerView() {
        bScroll = false
        val indexDistance: Int = currentIndex - layoutManager.findFirstVisibleItemPosition()
        if (indexDistance > 0 && indexDistance < recyclerView!!.childCount) {
            val top: Int = recyclerView.getChildAt(indexDistance).top
            recyclerView.smoothScrollBy(0, top)
        }
    }

    private fun smoothScrollToPosition(position: Int) {
        val firstPosition: Int = layoutManager.findFirstVisibleItemPosition()
        val lastPosition: Int = layoutManager.findLastVisibleItemPosition()
        when {
            position <= firstPosition -> {
                recyclerView.smoothScrollToPosition(position)
            }
            position <= lastPosition -> {
                val top: Int = recyclerView.getChildAt(position - firstPosition).top
                recyclerView.smoothScrollBy(0, top)
            }
            else -> {
                recyclerView.smoothScrollToPosition(position)
                bScroll = true
            }
        }
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_guide

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.getNaviTree()
    }


    class NavigationTabAdapter(context: Context?, list: List<Guide>) : TabAdapter {

        private var context: Context = context!!
        private var list = arrayListOf<Guide>()

        init {
            this.list = list as ArrayList<Guide>
        }

        override fun getIcon(position: Int): ITabView.TabIcon? = null


        override fun getBadge(position: Int): ITabView.TabBadge? = null

        override fun getBackground(position: Int): Int = -1

        override fun getTitle(position: Int): ITabView.TabTitle {
            return ITabView.TabTitle.Builder()
                .setContent(list[position].name)
                .setTextColor(
                    ContextCompat.getColor(context, R.color.colorPrimary),
                    ContextCompat.getColor(context, R.color.gray8))
                .build()
        }

        override fun getCount(): Int = list.size

    }

    inner class GuideAdapter : BaseQuickAdapter<Guide, BaseViewHolder>(R.layout.item_guide_list, guideList) {
        override fun convert(helper: BaseViewHolder, item: Guide) {
            helper.setText(R.id.tvTitle, item.name)
            val flowLayout = helper.getView<TagFlowLayout>(R.id.flowLayout)
            flowLayout.adapter = object : TagAdapter<Article>(item.articles) {
                override fun getView(parent: FlowLayout?, position: Int, article: Article): View {
                    val tv: TextView = LayoutInflater.from(parent?.context).inflate(R.layout.flow_layout_tv,
                        flowLayout, false) as TextView

                    val padding: Int = dip(10F)
                    tv.setPadding(padding, padding, padding, padding)
                    tv.text = article.title
                    tv.setTextColor(Utils.randomColor())

                    flowLayout.setOnTagClickListener { _, _, _ ->
                        startActivity<ArticleActivity>("webUrl" to article.link, "webTitle" to article.title)
                        true
                    }
                    return tv
                }
            }
        }
    }
}