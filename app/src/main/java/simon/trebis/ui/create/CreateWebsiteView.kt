package simon.trebis.ui.create

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.support.design.widget.TextInputEditText
import android.text.format.DateFormat
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import simon.trebis.MainActivity
import simon.trebis.R
import simon.trebis.data.entity.Website
import simon.trebis.service.DiskUtils


class CreateWebsiteView(root: View, private val activity: MainActivity) {

    private val dateFormat = DateFormat.getDateFormat(root.context.applicationContext)

    private val websiteUrl = root.findViewById<TextInputEditText>(R.id.website_edit_url)
    private val creationDate = root.findViewById<TextView>(R.id.website_edit_date)
    private val webView = root.findViewById<WebView>(R.id.website_edit_webview)

    private val confirm = root.findViewById<Button>(R.id.website_edit_confirm)

    private var website: Website? = null

    var onConfirm: (Website) -> Unit = {}

    init {
        confirm.setOnClickListener { onConfirm(website!!) }
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

            webViewClient = PreviewWebViewClient(context) { onWebsiteNameChanged(it) }
            webChromeClient = PreviewChromeVebViewClient { onWebsiteIconChanged(it) }
        }
    }

    private fun onWebsiteIconChanged(favicon: Bitmap) {
        if (website != null && website?.id != null) {
            val path = "${website!!.id}/favicon"

            DiskUtils.saveBitmapToFile(favicon, path, activity.applicationContext)
            website!!.iconPath = path
        }
    }

    private fun onWebsiteNameChanged(name: String) {
        if (website != null) {
            website!!.name = name
            updateName()
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
        activity.setActionBarTitle(website!!.name)
    }

    private fun updateUrl() {
        websiteUrl.setText(website?.url)
        websiteUrl.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && website != null) {
                website!!.url = websiteUrl.text.toString()
                updateWebView()
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
