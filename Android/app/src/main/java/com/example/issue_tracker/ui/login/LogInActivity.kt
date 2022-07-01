package com.example.issue_tracker.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.issue_tracker.MainActivity
import com.example.issue_tracker.R
import com.example.issue_tracker.common.AccessToken
import com.example.issue_tracker.common.Constants
import com.example.issue_tracker.databinding.ActivityLogInBinding
import com.example.issue_tracker.ui.common.LoginUser
import com.example.issue_tracker.ui.common.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import org.jsoup.Jsoup

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private val viewModel:LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferenceManager.initSharedPreferences(getSharedPreferences("LoginSharedPreference", AppCompatActivity.MODE_PRIVATE))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        binding.lifecycleOwner = this

        binding.tvWithoutLoginBtnTitle.setOnClickListener {
            moveToMain()
        }
        binding.btnGithubLogin.setOnClickListener {
            setWebView()
        }
    }

    private fun moveToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun setWebView() {
        switchToLoginMode()
        binding.wvLogin.webViewClient = CustomWebViewClient()
        binding.wvLogin.settings.javaScriptEnabled = true
        binding.wvLogin.loadUrl(Constants.GITHUB_LOGIN)
        binding.wvLogin.addJavascriptInterface(MyJavaScriptInterface(),"HTMLOUT");
    }

    private fun switchToLoginMode() {
        binding.wvLogin.isVisible = true
        binding.btnGithubLogin.isVisible = false
        binding.btnGoogleLogin.isVisible = false
    }

    inner class CustomWebViewClient() : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            Log.d("AppTest", "webView Error!!!!!")
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            val code = Uri.parse(url).getQueryParameters("code")
            if(!code.isNullOrEmpty()) {
                AccessToken.code = code[0]
                binding.wvLogin.loadUrl("javascript:window.HTMLOUT.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                moveToMain()
            }
        }



    }
    internal class MyJavaScriptInterface {
        @JavascriptInterface
        fun showHTML(html: String?) {
            val doc= Jsoup.parse(html)
            val data = doc.body().text()
            val json = JSONObject(data)
            val token= json.get("jwtAccessToken").toString()
            val refreshToken= json.get("jwtRefreshToken").toString()
            val useId= json.get("id").toString()
            val loginMethod = json.get("resourceServer").toString()
            LoginUser.id = useId
            LoginUser.method= loginMethod
            SharedPreferenceManager.putString(Constants.ACCESS_TOKEN_KEY, token)
            SharedPreferenceManager.putString(Constants.ACCESS_REFRESH_KEY, refreshToken)
            AccessToken.token= token
        }
    }
}
