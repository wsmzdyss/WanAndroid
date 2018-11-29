package com.xjm.wanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xjm.wanandroid.R
import com.xjm.wanandroid.base.BaseMvpFragment
import com.xjm.wanandroid.bean.response.KnowChildren
import com.xjm.wanandroid.presenter.KnowPresenter
import com.xjm.wanandroid.ui.activity.KnowChildActivity
import com.xjm.wanandroid.view.KnowView
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.Serializable


/**
 * Created by xjm on 2018/11/15.
 */
class KnowFragment : BaseMvpFragment<KnowPresenter>(), KnowView {

    private lateinit var adapter: KnowAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_know, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        mPresenter.getKnowTree()
    }

    private fun initView() {
        //RecyclerView
        adapter = KnowAdapter()
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        recyclerView?.let {
            it.layoutManager = layoutManager
            it.adapter = adapter
            it.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        adapter.apply {
            setOnItemClickListener { _, _, position ->
                val intent = Intent(context, KnowChildActivity::class.java)
                intent.putExtra("childList", data[position].children as Serializable)
                startActivity(intent)
            }
        }

        swipeRefreshLayout.apply {
            setColorSchemeColors(resources.getColor(R.color.colorPrimary, null))
            setOnRefreshListener {
                mPresenter.getKnowTree()
            }
        }
    }

    override fun onKnowTreeResult(t: List<KnowChildren>) {
        adapter.replaceData(t)
        swipeRefreshLayout.isRefreshing = false
    }

    inner class KnowAdapter : BaseQuickAdapter<KnowChildren, BaseViewHolder>(R.layout.item_know) {
        override fun convert(helper: BaseViewHolder, item: KnowChildren) {
            helper.setText(R.id.tvTitle, item.name)
            val sb = StringBuilder()
            for (it in item.children) {
                sb.append("${it.name}  ")
            }
            helper.setText(R.id.tvName, sb)
        }
    }

    override fun bindPresenterView() {
        mPresenter = KnowPresenter()
        mPresenter.mView = this
        mPresenter.lifecycle = this
    }

}