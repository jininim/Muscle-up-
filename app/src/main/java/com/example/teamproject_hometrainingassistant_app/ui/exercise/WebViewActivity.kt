package com.example.teamproject_hometrainingassistant_app.ui.exercise

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewbinding.ViewBinding
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityWebviewBinding

class WebViewActivity: AppCompatActivity() {
    private lateinit var binding: ActivityWebviewBinding
    private lateinit var url: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        url = intent.getStringExtra("url").toString()

        initViews()
    }

    @SuppressLint("SetJavaScriptEnabled") // 보안상의 이슈 때문에 자바스크립트 기능을 제공하지 않았음.
    private fun initViews(){
        binding.webView.webViewClient = WebViewClient() // 웹을 외부에서 열지않고 앱 내에서 바로 열기
        binding.webView.settings.javaScriptEnabled = true // 웹에서 제공하는 자바스크립트 기능을 수행
        binding.webView.loadUrl(url)
    }
}