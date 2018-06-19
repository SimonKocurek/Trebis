package simon.trebis.ui.create

import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Bitmap
import android.os.Build
import android.support.design.widget.TextInputEditText
import android.support.v7.preference.PreferenceManager
import android.text.format.DateFormat
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import simon.trebis.MainActivity
import simon.trebis.R
import simon.trebis.data.entity.Website
import java.io.ByteArrayOutputStream


class CreateWebsiteView(val root: View, private val activity: MainActivity) {

    private val basicUrls = listOf("http://", "https://", "http://www.", "https://www.")

    private val dateFormat = DateFormat.getDateFormat(root.context)

    private val websiteUrl = root.findViewById<TextInputEditText>(R.id.website_edit_url)
    private val creationDate = root.findViewById<TextView>(R.id.website_edit_date)
    private val webView = root.findViewById<WebView>(R.id.website_edit_webview)
    private val confirm = root.findViewById<Button>(R.id.website_edit_confirm)

    private var website: Website? = null
    private var fragmentStopped: Boolean = false

    var onConfirm: (Website) -> Unit = {}

    init {
        confirm.setOnClickListener { onConfirm(website!!) }
        configureWebView()
        configureUrlField()
    }

    private fun configureWebView() {
        webView.apply {
            configureSettings()
            webViewClient = PreviewWebViewClient { onWebsiteNameChanged(it) }
            webChromeClient = PreviewChromeVebViewClient { onWebsiteIconChanged(it) }
        }
    }

    private fun WebView.configureSettings() {
        settings.apply {
            javaScriptEnabled = PreferenceManager
                    .getDefaultSharedPreferences(context)
                    .getBoolean(context.getString(R.string.javascriptenabled), true)
            domStorageEnabled = true

            loadWithOverviewMode = true
            useWideViewPort = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                allowFileAccessFromFileURLs = true
                allowUniversalAccessFromFileURLs = true
            }
        }
    }

    private fun configureUrlField() {
        websiteUrl.setOnEditorActionListener { _, actionId, _ -> unfocusUrl(actionId) }
        websiteUrl.onFocusChangeListener = View.OnFocusChangeListener { _, _ -> updateUrlText() }
    }

    private fun unfocusUrl(actionId: Int): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            val inputManager = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(activity.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            websiteUrl.clearFocus()
        }

        return actionId == EditorInfo.IME_ACTION_DONE
    }

    private fun updateUrlText() {
        website?.let {
            val url = websiteUrl.text.toString()
            it.url = withHttpPrefix(url)
            updateUrl(it)
        }
    }

    private fun withHttpPrefix(url: String): String {
        if (!url.startsWithBase()) {
            return "${defaultPrefix()}://www."
        }

        return url
    }

    private fun defaultPrefix() {
        PreferenceManager
                .getDefaultSharedPreferences(root.context)
                .getString(root.context.getString(R.string.default_prefix), "https")
    }

    private fun onWebsiteIconChanged(favicon: Bitmap) {
        website?.let {
            val byteStream = ByteArrayOutputStream()
            favicon.compress(Bitmap.CompressFormat.PNG, 100, byteStream)

            it.favicon = byteStream.toByteArray()
        }
    }

    private fun onWebsiteNameChanged(name: String) {
        if ("about:blank" == name) {
            return
        }

        website?.let {
            it.name = name
            updateName(it)
        }
    }

    fun updateWith(website: Website?) {
        website?.let {
            this.website = it

            updateName(it)
            updateUrl(it)
            updateCreationDate(it)
        }
    }

    private fun updateName(website: Website) {
        if (website.name.isNotBlank() && !fragmentStopped) {
            activity.setActionBarTitle(website.name)
        }
    }

    private fun updateUrl(website: Website) {
        websiteUrl.apply {
            if (website.url != text.toString()) {
                setText(website.url)
            }

            if (!text.toString().isBase()) {
                webView.loadUrl(website.url)
            }
        }
    }

    private fun updateCreationDate(website: Website) {
        creationDate.text = dateFormat.format(website.date)
    }

    fun fragmentStopped() {
        fragmentStopped = true
    }

    private fun String.isBase() = basicUrls.any { this == it }

    private fun String.startsWithBase() = basicUrls.any { startsWith(it) }

}
