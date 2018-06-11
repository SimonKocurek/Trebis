package simon.trebis.ui.create

import android.annotation.SuppressLint
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.format.DateFormat
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import simon.trebis.R
import simon.trebis.data.entity.Website


class CreateWebsiteView(root: View) {

    private val dateFormat = DateFormat.getDateFormat(root.context.applicationContext)

    private val websiteName = root.findViewById<EditText>(R.id.website_edit_name)
    private val websiteUrl = root.findViewById<EditText>(R.id.website_edit_url)
    private val creationDate = root.findViewById<TextView>(R.id.website_edit_date)
    @SuppressLint("SetJavaScriptEnabled")
    private val webView = root.findViewById<WebView>(R.id.website_edit_webview).apply {
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.allowFileAccessFromFileURLs = true
            settings.allowUniversalAccessFromFileURLs = true
        }

        webViewClient = object : WebViewClient() {

            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                Toast.makeText(context, description, Toast.LENGTH_SHORT).show()
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView, req: WebResourceRequest, rerr: WebResourceError) {
                onReceivedError(view, rerr.errorCode, rerr.description.toString(), req.url.toString())
            }
        }
    }

    private val confirm = root.findViewById<Button>(R.id.website_edit_confirm)

    private var website: Website? = null

    var goBack: () -> Unit = {}
    var update: (website: Website) -> Unit = {}

    init {
        confirm.setOnClickListener({ goBack() })
    }

    fun updateWith(website: Website?) {
        if (website != null) {
            this.website = website

            updateName()
            updateUrl()
            updateCreationDate()
            updateWebView()
        }
    }

    private fun updateName() {
        websiteName.setText(website?.name)
        websiteName.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                website?.let {
                    it.name = websiteName.text.toString()
                    update(it)
                }
            }
        }
    }

    private fun updateUrl() {
        websiteUrl.setText(website?.url)
        websiteUrl.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                website?.let {
                    it.url = websiteUrl.text.toString()
                    updateWebView()
                    update(it)
                }
            }
        }
    }

    private fun updateCreationDate() {
        creationDate.text = dateFormat.format(website?.date)
    }

    private fun updateWebView() {
        webView.loadUrl(website?.url)
    }

}
