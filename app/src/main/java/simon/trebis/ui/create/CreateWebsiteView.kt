package simon.trebis.ui.create

import android.annotation.SuppressLint
import android.os.Build
import android.support.design.widget.TextInputEditText
import android.text.format.DateFormat
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import simon.trebis.R
import simon.trebis.data.entity.Website


class CreateWebsiteView(root: View) {

    private val dateFormat = DateFormat.getDateFormat(root.context.applicationContext)

    private val websiteName = root.findViewById<TextInputEditText>(R.id.website_edit_name)
    private val websiteUrl = root.findViewById<TextInputEditText>(R.id.website_edit_url)
    private val creationDate = root.findViewById<TextView>(R.id.website_edit_date)
    private val webView = root.findViewById<WebView>(R.id.website_edit_webview)

    private val confirm = root.findViewById<Button>(R.id.website_edit_confirm)

    private var website: Website? = null

    var goBack: () -> Unit = {}
    var update: (website: Website) -> Unit = {}

    init {
        confirm.setOnClickListener({ goBack() })
        configureWebView()
    }

    private fun configureWebView() {
        webView.apply {
            @SuppressLint("SetJavaScriptEnabled")
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                settings.allowFileAccessFromFileURLs = true
                settings.allowUniversalAccessFromFileURLs = true
            }

            webViewClient = LoggingWebViewClient(context)
        }
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
