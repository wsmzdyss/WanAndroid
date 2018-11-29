package com.xjm.wanandroid.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.xjm.wanandroid.R
import com.xjm.wanandroid.base.BaseActivity
import com.xjm.wanandroid.bean.response.KnowChildren
import com.xjm.wanandroid.common.BaseToolbarInterface
import com.xjm.wanandroid.ui.fragment.KnowChildFragment
import kotlinx.android.synthetic.main.fragment_know_child.*

/**
 * Created by xjm on 2018/11/28.
 */
class KnowChildActivity : BaseActivity(), BaseToolbarInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.fragment_know_child)
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        val childList: List<KnowChildren> = intent.getSerializableExtra("childList") as List<KnowChildren>
        viewPager.apply {
            adapter = KnowChildPagerAdapter(childList, supportFragmentManager)
            offscreenPageLimit = childList.size
        }
        tabLayout.setupWithViewPager(viewPager)
    }

    inner class KnowChildPagerAdapter(val list: List<KnowChildren>, fm: FragmentManager) :
        FragmentStatePagerAdapter(fm) {
        private val fragments = arrayListOf<Fragment>()

        init {
            fragments.clear()
            list.forEach {
                fragments.add(KnowChildFragment.getInstance(it.id))
            }
        }

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = list.size

        override fun getPageTitle(position: Int): CharSequence = list[position].name

        override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE
    }

    override fun initToolbar(toolbar: Toolbar, center: TextView, right: TextView, back: ImageView) {

    }
}