package simon.trebis

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

private const val ACTION_FETCH_WEBSITE = "simon.trebis.action.FETCH_WEBSITE"
private const val WEBSITE_URL = "simon.trebis.extra.WEBSITE_URL"

class DownloadService : IntentService("DownloadService") {

    private lateinit var webview: WebView

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_FETCH_WEBSITE -> {
                val url = intent.getStringExtra(WEBSITE_URL)
                handleFetchAction(url)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun handleFetchAction(url: String) {
        webview = WebView(this@DownloadService)

        // without this toast message, screenshot will be blank, dont ask me why...
        Toast.makeText(this@DownloadService, "Picture taken.", Toast.LENGTH_SHORT).show()

        // This is important, so that the webview will render and we don't get blank screenshot
        webview.isDrawingCacheEnabled = true

        //width and height of your webview and the resulting screenshot
        webview.measure(1280, 720)
        webview.layout(0, 0, 1280, 720)

        webview.settings.javaScriptEnabled = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webview.settings.allowFileAccessFromFileURLs = true
            webview.settings.allowUniversalAccessFromFileURLs = true
        }

        webview.loadUrl(url)
        webview.webViewClient = object : WebViewClient() {

            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                Log.e(TAG, "Recieved error from WebView, description: $description, Failing url: $failingUrl")
                //without this method, your app may crash...
            }

            override fun onPageFinished(view: WebView, url: String) {
                val root = android.os.Environment.getExternalStorageDirectory()
                val dir = File(root.absolutePath + "/download")
                dir.mkdirs()
                val file = File(dir, url + ".png") // TODO(TIMESTAMP)!!!!!!!

                takeWebviewScreenshot(file)
            }
        }
    }

    private fun takeWebviewScreenshot(outputFile: File) {
        try {
            //allow webview to render, otherwise screenshot may be blank or partial
            TimeUnit.MILLISECONDS.sleep(5000)
            saveBitmapToFile(webview.drawingCache, outputFile)

        } catch (e: InterruptedException) {
            Log.e(TAG, "InterruptedException when taking webview screenshot ", e)
        }
    }

    private fun saveBitmapToFile(bitmap: Bitmap, outputFile: File) {
        BufferedOutputStream(FileOutputStream(outputFile)).use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, it)
            it.flush()
        }
    }

    companion object {

        @JvmStatic
        fun startFetchAction(context: Context, url: String) {
            val intent = Intent(context, DownloadService::class.java).apply {
                action = ACTION_FETCH_WEBSITE
                putExtra(WEBSITE_URL, url)
            }
            context.startService(intent)
        }

    }
}
