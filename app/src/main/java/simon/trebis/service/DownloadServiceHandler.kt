package simon.trebis.service

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.DisplayMetrics
import android.webkit.WebView
import simon.trebis.Const
import simon.trebis.Const.Companion.JAVASCRIPT_APP
import simon.trebis.data.DatabaseManager


class DownloadServiceHandler(
        looper: Looper,
        private val downloadService: DownloadService
) : Handler(looper) {

    private var startId = 1

    override fun handleMessage(message: Message) {
        message.apply {
            startId = arg1

            val intent = obj as Intent
            val url = intent.getStringExtra(Const.WEBSITE_URL)
            val websiteId = intent.getLongExtra(Const.WEBSITE_ID, -1)

            handleFetchAction(url, websiteId)
        }
    }

    private fun handleFetchAction(url: String, websiteId: Long) {
        val databaseManager = DatabaseManager.instance(downloadService)

        configuredWebView().let {
            it.webViewClient = DownloadWebViewClient(websiteId, databaseManager, this, it)
            it.loadUrl(url)
        }
    }

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface", "AddJavascriptInterface")
    private fun configuredWebView(): WebView {
        return WebView(downloadService).apply {
            // This is important, so that the webView will render and we don't get blank screenshot
            isDrawingCacheEnabled = true

            // width and height of your webView and the resulting screenshot
            dimensions()?.let {
                measure(it.widthPixels, it.heightPixels)
                layout(0, 0, it.widthPixels, it.heightPixels)
            }

            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                settings.allowFileAccessFromFileURLs = true
                settings.allowUniversalAccessFromFileURLs = true
            }

            addJavascriptInterface(this, JAVASCRIPT_APP)
        }
    }

    fun dimensions(): DisplayMetrics? {
        return downloadService.resources.displayMetrics
    }

    fun stopService() {
        downloadService.stopSelf(startId)
    }

}