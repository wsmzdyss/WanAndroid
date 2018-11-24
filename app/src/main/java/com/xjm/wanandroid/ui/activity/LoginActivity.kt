package com.xjm.wanandroid.ui.activity

import android.os.Bundle
import android.view.View
import com.xjm.wanandroid.R
import com.xjm.wanandroid.base.BaseMvpActivity
import com.xjm.wanandroid.bean.event.LoginEvent
import com.xjm.wanandroid.bean.response.ArticleListResp
import com.xjm.wanandroid.bean.response.LoginResp
import com.xjm.wanandroid.presenter.LoginPresenter
import com.xjm.wanandroid.utils.textEnable
import com.xjm.wanandroid.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.toast

/**
 * Created by xjm on 2018/11/12.
 */
class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView {

    private var isLoginState = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        btnLogin.textEnable(edtUserName) { isBtnEnable() }
        btnLogin.textEnable(edtPassword) { isBtnEnable() }
        btnLogin.textEnable(edtPassword2) { isBtnEnable() }

        btnLogin.setOnClickListener {
            if (isLoginState) {
                mPresenter.login(edtUserName.text.toString(), edtPassword.text.toString())
            } else {
                mPresenter.register(
                    edtUserName.text.toString(),
                    edtPassword.text.toString(),
                    edtPassword2.text.toString()
                )
            }
        }

        tvRegister.setOnClickListener {
            if (isLoginState) {
                btnLogin.text = "注册"
                tvRegister.text = "已有账号？现在登录"
                edtPassword2.visibility = View.VISIBLE
            } else {
                btnLogin.text = "登录"
                tvRegister.text = "立即注册"
                edtPassword2.visibility = View.GONE
            }
            btnLogin.isEnabled = isBtnEnable()
            isLoginState = isLoginState.not()
        }
    }

    override fun bindPresenterView() {
        mPresenter = LoginPresenter()
        mPresenter.mView = this
        mPresenter.lifecycle = this
    }

    override fun onLoginResult(t: LoginResp) {
        toast("登录成功 + ${t.id}")
        isLogin = true
        EventBus.getDefault().post(LoginEvent(t.username))
        finish()
    }

    override fun onRegisterResult(t: LoginResp) {
        toast("注册成功 + ${t.id}")
        isLogin = true
        finish()
    }

    private fun isBtnEnable(): Boolean {
        return when (isLoginState) {
            true -> {
                edtUserName.text.isNullOrEmpty().not() &&
                        edtPassword.text.isNullOrEmpty().not()
            }
            false -> {
                edtUserName.text.isNullOrEmpty().not() &&
                        edtPassword.text.isNullOrEmpty().not() &&
                        edtPassword2.text.isNullOrEmpty().not()
            }
        }
    }

}