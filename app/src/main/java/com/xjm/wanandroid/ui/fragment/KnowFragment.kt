package com.xjm.wanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xjm.wanandroid.R
import com.xjm.wanandroid.base.BaseMvpFragment
import com.xjm.wanandroid.bean.response.KnowChildren
import com.xjm.wanandroid.presenter.KnowPresenter
import com.xjm.wanandroid.ui.activity.KnowChildActivity
import com.xjm.wanandroid.view.KnowView
import kotlinx.android.synthetic.main.fragment_know.*
import java.io.Serializable


/**
 * Created by xjm on 2018/11/15.
 */
class KnowFragment : BaseMvpFragment<KnowPresenter>(), KnowView {
    override fun attachLayoutRes(): Int = R.layout.fragment_know

    private lateinit var adapter: KnowAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        mPresenter.getKnowTree()
    }

    override fun initView() {
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
    }

    override fun onKnowTreeResult(t: List<KnowChildren>) {
        adapter.replaceData(t)
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

    override fun refreshData() {
        initData()
    }

}