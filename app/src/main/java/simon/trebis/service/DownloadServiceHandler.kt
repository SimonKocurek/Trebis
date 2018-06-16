package simon.trebis.service

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import simon.trebis.Const.Companion.DEFAULT_HEIGHT
import simon.trebis.Const.Companion.DEFAULT_WIDHT
import simon.trebis.Const.Companion.DEVICE_HEIGHT
import simon.trebis.Const.Companion.DEVICE_WIDTH
import simon.trebis.Const.Companion.NO_ID
import simon.trebis.Const.Companion.WEBSITE_ID
import simon.trebis.Const.Companion.WEBSITE_URL
import simon.trebis.R
import simon.trebis.data.DatabaseManager
import simon.trebis.service.JavascriptBridge.Companion.JAVASCRIPT_APP

class DownloadServiceHandler(private val downloadService: DownloadService, intent: Intent) {

    private val url: String
    private val websiteId: Long
    private val width: Int
    private val height: Int

    init {
        val extras = intent.extras
        url = extras.getString(WEBSITE_URL)
        websiteId = extras.getLong(WEBSITE_ID, NO_ID)
        width = extras.getInt(DEVICE_WIDTH, DEFAULT_WIDHT)
        height = extras.getInt(DEVICE_HEIGHT, DEFAULT_HEIGHT)
    }

    fun handle() {
        downloadService
        val view = LayoutInflater.from(downloadService).inflate(R.layout.background_download, null)

        view.findViewById<WebView>(R.id.background_webview).apply {
            setupRendering()
            setupSettings()
            addClients()

            loadUrl(url)
        }
    }

    private fun WebView.setupRendering() {
        Toast.makeText(downloadService, context.getString(R.string.picturetaken), Toast.LENGTH_SHORT).show()

        isDrawingCacheEnabled = true

        measure(width, height * 2)
        layout(0, 0, width, height * 2)

        // Set software rendering
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun WebView.setupSettings() {
        settings.apply {
            setAppCacheEnabled(true)
            javaScriptEnabled = true
            domStorageEnabled = true
            loadsImagesAutomatically = true

            loadWithOverviewMode = true
            useWideViewPort = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                allowFileAccessFromFileURLs = true
                allowUniversalAccessFromFileURLs = true
            }
        }
    }

    @SuppressLint("AddJavascriptInterface")
    private fun WebView.addClients() {
        webViewClient = DownloadWebViewClient()
        webChromeClient = WebChromeClient()

        val databaseManager = DatabaseManager.instance(downloadService)
        addJavascriptInterface(JavascriptBridge(websiteId, databaseManager, this), JAVASCRIPT_APP)
    }

}
