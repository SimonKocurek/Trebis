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
import java.io.ByteArrayOutputStream

class CreateWebsiteView(root: View, private val activity: MainActivity) {

    private val dateFormat = DateFormat.getDateFormat(root.context.applicationContext)

    private val websiteUrl = root.findViewById<TextInputEditText>(R.id.website_edit_url)
    private val creationDate = root.findViewById<TextView>(R.id.website_edit_date)
    private val webView = root.findViewById<WebView>(R.id.website_edit_webview)

    private val confirm = root.findViewById<Button>(R.id.website_edit_confirm)

    private var website: Website? = null

    var onConfirm: (Website) -> Unit = {}
    var onUpdate: (Website) -> Unit = {}

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
        website?.let {
            val byteStream = ByteArrayOutputStream()
            favicon.compress(Bitmap.CompressFormat.PNG, 100, byteStream)

            it.favicon = byteStream.toByteArray()
            onUpdate(it)
        }
    }

    private fun onWebsiteNameChanged(name: String) {
        website?.let {
            it.name = name
            onUpdate(it)
            updateName(it)
        }
    }

    fun updateWith(website: Website?) {
        website?.let {
            this.website = it

            updateName(it)
            updateUrl(it)
            updateCreationDate(it)
            updateWebView(it)
        }
    }

    private fun updateName(website: Website) {
        activity.setActionBarTitle(website.name)
    }

    private fun updateUrl(website: Website) {
        websiteUrl.also {
            it.setText(website.url)
            it.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
                website.url = websiteUrl.text.toString()
                updateWebView(website)
            }
        }
    }

    private fun updateCreationDate(website: Website) {
        creationDate.text = dateFormat.format(website.date)
    }

    private fun updateWebView(website: Website) {
        webView.loadUrl(website.url)
    }

}
