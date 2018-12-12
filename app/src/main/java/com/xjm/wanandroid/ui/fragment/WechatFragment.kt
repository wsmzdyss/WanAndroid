package com.xjm.wanandroid.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.View
import com.xjm.wanandroid.R
import com.xjm.wanandroid.base.BaseMvpFragment
import com.xjm.wanandroid.bean.response.KnowChildren
import com.xjm.wanandroid.presenter.WechatPresenter
import com.xjm.wanandroid.ui.activity.MainActivity
import com.xjm.wanandroid.view.WechatView
import kotlinx.android.synthetic.main.fragment_wechat.*

/**
 * Created by xjm on 2018/11/28.
 */
class WechatFragment : BaseMvpFragment<WechatPresenter>(), WechatView {
    override fun attachLayoutRes(): Int = R.layout.fragment_wechat

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        mPresenter.getWechatTree()
    }

    override fun initView() {
        viewPager.apply {
            adapter = WechatPagerAdapter(childList, childFragmentManager)
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