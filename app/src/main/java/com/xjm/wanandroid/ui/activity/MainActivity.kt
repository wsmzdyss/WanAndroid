package com.xjm.wanandroid.ui.activity

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.widget.TextView
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.xjm.wanandroid.R
import com.xjm.wanandroid.base.BaseActivity
import com.xjm.wanandroid.bean.event.LoginEvent
import com.xjm.wanandroid.common.AppManager
import com.xjm.wanandroid.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by xjm on 2018/11/15.
 */
class MainActivity : BaseActivity() {

    private var pressTime: Long = 0

    private var mHomeFragment: HomeFragment? = null

    private var mKnowFragment: KnowFragment? = null

    private var mGuideFragment: GuideFragment? = null

    private var mProjectFragment: ProjectFragment? = null

    private var mWechatFragment: WechatFragment? = null

    private val tvUserName by lazy { naviView.getHeaderView(0).findViewById<TextView>(R.id.tvUsername) }

    private val navBarStrings: Array<String> by lazy { resources.getStringArray(R.array.nav_bar) }

    private var mIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        isEventBusRegister = true
        super.onCreate(savedInstanceState)
        initToolBar()
        initDrawer()
        initBottomNav()
        initView()
        showFragment(mIndex)
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
                    drawerLayout.closeDrawer(Gravity.START)
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
        appBar.stateListAnimator = null
        toolbar.apply {
            title = navBarStrings[0]
            setSupportActionBar(this)
        }
    }

    private fun initBottomNav() {
        bottomNavBar.apply {
            setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
                override fun onTabReselected(position: Int) {
                }

                override fun onTabUnselected(position: Int) {
                }

                override fun onTabSelected(position: Int) {
                    showFragment(position)
                }
            })
        }
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START)
            return
        }
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

    /**
     * 展示Fragment
     * @param index
     */
    private fun showFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        mIndex = index
        toolbar.title = navBarStrings[index]
        when (index) {
            0 // 首页
            -> {
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment()
                    transaction.add(R.id.container, mHomeFragment!!, "home")
                } else {
                    transaction.show(mHomeFragment!!)
                }
            }
            1 // 知识体系
            -> {
                if (mKnowFragment == null) {
                    mKnowFragment = KnowFragment()
                    transaction.add(R.id.container, mKnowFragment!!, "know")
                } else {
                    transaction.show(mKnowFragment!!)
                }
            }
            2 // 导航
            -> {
                if (mGuideFragment == null) {
                    mGuideFragment = GuideFragment()
                    transaction.add(R.id.container, mGuideFragment!!, "guide")
                } else {
                    transaction.show(mGuideFragment!!)
                }
            }
            3 // 项目
            -> {
                if (mProjectFragment == null) {
                    mProjectFragment = ProjectFragment()
                    transaction.add(R.id.container, mProjectFragment!!, "project")
                } else {
                    transaction.show(mProjectFragment!!)
                }
            }
            4 // 公众号
            -> {
                if (mWechatFragment == null) {
                    mWechatFragment = WechatFragment()
                    transaction.add(R.id.container, mWechatFragment!!, "wechat")
                } else {
                    transaction.show(mWechatFragment!!)
                }
            }
        }
        transaction.commit()
    }

    /**
     * 隐藏所有的Fragment
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mKnowFragment?.let { transaction.hide(it) }
        mGuideFragment?.let { transaction.hide(it) }
        mProjectFragment?.let { transaction.hide(it) }
        mWechatFragment?.let { transaction.hide(it) }
    }

}