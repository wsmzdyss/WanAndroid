package com.xjm.wanandroid.widget

import android.content.Context
import android.util.AttributeSet
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.xjm.wanandroid.R

/*
    底部导航
 */
class BottomNavBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationBar(context, attrs, defStyleAttr) {

    init {
        //首页
        val homeItem = BottomNavigationItem(R.drawable.nav_home_selector,resources.getString(R.string.nav_bar_home))
                .setInactiveIconResource(R.drawable.nav_home_selector)
                .setActiveColorResource(R.color.colorPrimary)
                .setInActiveColorResource(R.color.gray)

        //知识体系
        val knowItem = BottomNavigationItem(R.drawable.nav_know_selector,resources.getString(R.string.nav_bar_knowledge))
                .setInactiveIconResource(R.drawable.nav_know_selector)
                .setActiveColorResource(R.color.colorPrimary)
                .setInActiveColorResource(R.color.gray)

        //导航
        val guideItem = BottomNavigationItem(R.drawable.nav_guide_selector,resources.getString(R.string.nav_bar_guide))
                .setInactiveIconResource(R.drawable.nav_guide_selector)
                .setActiveColorResource(R.color.colorPrimary)
                .setInActiveColorResource(R.color.gray)

        //项目
        val projectItem = BottomNavigationItem(R.drawable.nav_project_selector,resources.getString(R.string.nav_bar_project))
                .setInactiveIconResource(R.drawable.nav_project_selector)
                .setActiveColorResource(R.color.colorPrimary)
                .setInActiveColorResource(R.color.gray)

        //公众号
        val wechatItem = BottomNavigationItem(R.drawable.nav_wechat_selector,resources.getString(R.string.nav_bar_wechat))
            .setInactiveIconResource(R.drawable.nav_wechat_selector)
            .setActiveColorResource(R.color.colorPrimary)
            .setInActiveColorResource(R.color.gray)

        //设置底部导航模式及样式
        setMode(BottomNavigationBar.MODE_FIXED)
        setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        setBarBackgroundColor(R.color.white)
        //添加Tab
        addItem(homeItem)
                .addItem(knowItem)
                .addItem(guideItem)
                .addItem(projectItem)
                .addItem(wechatItem)
                .setFirstSelectedPosition(0)
                .initialise()
    }
}
