package com.xjm.wanandroid.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.xjm.wanandroid.R
import com.xjm.wanandroid.base.BaseMvpFragment
import com.xjm.wanandroid.bean.response.ArticleListResp
import com.xjm.wanandroid.bean.response.KnowChildren
import com.xjm.wanandroid.presenter.WechatPresenter
import com.xjm.wanandroid.ui.activity.MainActivity
import com.xjm.wanandroid.view.WechatView
import kotlinx.android.synthetic.main.fragment_know_child.*

/**
 * Created by xjm on 2018/11/28.
 */
class WechatFragment : BaseMvpFragment<WechatPresenter>(), WechatView {

    private val childList = arrayListOf<KnowChildren>()

    override fun bindPresenterView() {
        mPresenter = WechatPresenter()
        mPresenter.mView = this
        mPresenter.lifecycle = this
    }

    override fun onWechatTreeResult(t: List<KnowChildren>) {
        childList.clear()
        childList.addAll(t)
        initView()
    }

    override fun onWechatListResult(t: ArticleListResp) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_wechat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.getWechatTree()
    }

    private fun initView() {
        viewPager.apply {
            adapter = WechatPagerAdapter(childList, (context as MainActivity).supportFragmentManager)
            offscreenPageLimit = childList.size
        }
        tabLayout.setupWithViewPager(viewPager)
    }

    inner class WechatPagerAdapter(val list: List<KnowChildren>, fm: FragmentManager) :
        FragmentStatePagerAdapter(fm) {
        private val fragments = arrayListOf<Fragment>()

        init {
            fragments.clear()
            list.forEach {
                fragments.add(WechatChildFragment.getInstance(it.id))
            }
        }

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = list.size

        override fun getPageTitle(position: Int): CharSequence = list[position].name

        override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE
    }

}