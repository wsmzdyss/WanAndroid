package com.xjm.wanandroid.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.widget.TextView
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.xjm.wanandroid.R
import com.xjm.wanandroid.base.BaseActivity
import com.xjm.wanandroid.bean.event.LoginEvent
import com.xjm.wanandroid.common.AppManager
import com.xjm.wanandroid.ui.fragment.HomeFragment
import com.xjm.wanandroid.ui.fragment.KnowFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*

/**
 * Created by xjm on 2018/11/15.
 */
class MainActivity : BaseActivity() {

    private var pressTime: Long = 0

    private val mStack = Stack<Fragment>()

    private val mHomeFragment by lazy { HomeFragment() }

    private val mKnowFragment by lazy { KnowFragment() }

    private val mGuidFragment by lazy { KnowFragment() }

    private val mProjectFragment by lazy { KnowFragment() }

    private val mMineFragment by lazy { KnowFragment() }

    private val tvUserName by lazy { naviView.getHeaderView(0).findViewById<TextView>(R.id.tvUsername) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        isEventBusRegister = true
        super.onCreate(savedInstanceState)
        initToolBar()
        initDrawer()
        initFragment()
        initBottomNav()
        initView()
        changeFragment(0)
    }

    private fun initView() {
        tvUserName?.apply {
            when (isLogin) {
                true -> {
                    text = username
                    setOnClickListener {
                        //TODO
                    }
                }
                false -> {
                    text = "登录"
                    setOnClickListener {
                        drawerLayout.closeDrawer(Gravity.START)
                        startActivity<LoginActivity>()
                    }
                }
            }
        }

        naviView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_collect -> {
                    startActivity<CollectActivity>()
                }
            }
            return@setNavigationItemSelectedListener false
        }

    }

    private fun initDrawer() {
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun initToolBar() {
        toolbar.apply {
            title = "Wan Android"
            setSupportActionBar(this)
        }
    }

    private fun initBottomNav() {
        bottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                changeFragment(position)
            }
        })
    }

    private fun changeFragment(position: Int) {
        val manager = supportFragmentManager.beginTransaction()
        for (it in mStack) {
            manager.hide(it)
        }

        manager.show(mStack[position]).commit()
    }

    private fun initFragment() {
        val manager = supportFragmentManager.beginTransaction()
        manager.apply {
            add(R.id.container, mHomeFragment)
            add(R.id.container, mKnowFragment)
            add(R.id.container, mGuidFragment)
            add(R.id.container, mProjectFragment)
            add(R.id.container, mMineFragment)
        }.commit()

        mStack.apply {
            add(mHomeFragment)
            add(mKnowFragment)
            add(mGuidFragment)
            add(mProjectFragment)
            add(mMineFragment)
        }

    }

    override fun onBackPressed() {
        val curTime = System.currentTimeMillis()
        if (curTime - pressTime > 2000) {
            toast("再按一次退出程序")
            pressTime = curTime
        } else {
            AppManager.instance.exitApp(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: LoginEvent) {
        tvUserName.text = event.username
    }

}