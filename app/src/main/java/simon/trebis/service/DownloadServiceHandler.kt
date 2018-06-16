package simon.trebis.service

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import simon.trebis.Const.Companion.DEFAULT_HEIGHT
import simon.trebis.Const.Companion.DEFAULT_WIDHT
import simon.trebis.Const.Companion.DEVICE_HEIGHT
import simon.trebis.Const.Companion.DEVICE_WIDTH
import simon.trebis.Const.Companion.NO_ID
import simon.trebis.Const.Companion.WEBSITE_ID
import simon.trebis.Const.Companion.WEBSITE_URL
import simon.trebis.data.DatabaseManager
import simon.trebis.service.JavascriptBridge.Companion.JAVASCRIPT_APP


class DownloadServiceHandler(
        looper: Looper,
        private val downloadService: DownloadService
) : Handler(looper) {

    private var startId = 1

    var url = ""
    var websiteId = NO_ID
    var width = DEFAULT_WIDHT
    var height = DEFAULT_HEIGHT

    override fun handleMessage(message: Message) {
        message.apply {
            startId = arg1

            (obj as Intent).apply {
                url = getStringExtra(WEBSITE_URL)
                websiteId = getLongExtra(WEBSITE_ID, NO_ID)
                width = getIntExtra(DEVICE_WIDTH, DEFAULT_WIDHT)
                height = getIntExtra(DEVICE_HEIGHT, DEFAULT_HEIGHT)
            }

            handleFetchAction()
        }
    }

    @SuppressLint("AddJavascriptInterface", "NewApi")
    private fun handleFetchAction() {
        val databaseManager = DatabaseManager.instance(downloadService)

        configuredWebView().let {
            it.addJavascriptInterface(
                    JavascriptBridge(websiteId, databaseManager, this, it),
                    JAVASCRIPT_APP
            )
            it.loadUrl(url)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configuredWebView(): WebView {
        return WebView(downloadService).apply {
            // This is important, so that the webView will render and we don't get blank screenshot
            isDrawingCacheEnabled = true

            // width and height of your webView and the resulting screenshot
            measure(width, height * 2)
            layout(0, 0, width, height * 2)

            // Set software rendering
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)

            settings.setAppCacheEnabled(true)
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadsImagesAutomatically = true

            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                settings.allowFileAccessFromFileURLs = true
                settings.allowUniversalAccessFromFileURLs = true
            }

            webViewClient = DownloadWebViewClient()
            webChromeClient = WebChromeClient()
        }
    }

    fun stopService() {
        downloadService.stopSelf(startId)
    }

}
