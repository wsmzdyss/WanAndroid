package com.xjm.wanandroid.ui.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import com.xjm.wanandroid.R
import com.xjm.wanandroid.base.BaseActivity
import com.xjm.wanandroid.common.BaseToolbarInterface
import kotlinx.android.synthetic.main.activity_article.*

/**
 * Created by xjm on 2018/11/26.
 */
class ArticleActivity : BaseActivity(), BaseToolbarInterface {

    private val url: String by lazy { intent.getStringExtra("webUrl") }

    private val webTitle: String by lazy { intent.getStringExtra("webTitle") }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_article)
        super.onCreate(savedInstanceState)

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.loadUrl(url)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
    }

    override fun initToolbar(toolbar: Toolbar, center: TextView, right: TextView, back: ImageView) {
        center.text = webTitle
    }
}