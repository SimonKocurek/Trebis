package simon.trebis.service

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.webkit.WebView
import simon.trebis.Const
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

        configuredWebView(1280, 720).let {
            it.webViewClient = DownloadWebViewClient(websiteId, databaseManager, this)
            it.loadUrl(url)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configuredWebView(width: Int, height: Int): WebView {
        val webView = WebView(downloadService)

        return webView.apply {
            // This is important, so that the webView will render and we don't get blank screenshot
            isDrawingCacheEnabled = true

            // width and height of your webView and the resulting screenshot
            measure(width, height)
            layout(0, 0, width, height)

            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                settings.allowFileAccessFromFileURLs = true
                settings.allowUniversalAccessFromFileURLs = true
            }
        }
    }

    fun stopService() {
        downloadService.stopSelf(startId)
    }

}
