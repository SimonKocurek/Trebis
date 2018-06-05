package simon.trebis.service

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Environment.getExternalStorageDirectory
import android.text.format.DateFormat
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.concurrent.TimeUnit

class DownloadWebViewClient : WebViewClient() {

    override fun onReceivedError(webView: WebView, errorCode: Int, description: String, failingUrl: String) {
        Log.e(ContentValues.TAG, "Recieved error from WebView, description: $description, Failing url: $failingUrl")
        //without this method, your app may crash...
    }

    override fun onPageFinished(webView: WebView, url: String) {
        val root = getExternalStorageDirectory()
        val dir = File("${root.absolutePath}/$url")
        dir.mkdirs()

        val creationDate = DateFormat.format("yyyy-MM-dd HH:mm:ss", Date())
        val file = File(dir, creationDate.toString())

        takeWebViewScreenshot(file, webView)
    }

    private fun takeWebViewScreenshot(outputFile: File, webView: WebView) {
        try {
            //allow webView to render, otherwise screenshot may be blank or partial
            TimeUnit.MILLISECONDS.sleep(5000)
            saveBitmapToFile(webView.drawingCache, outputFile)

        } catch (e: InterruptedException) {
            Log.e(ContentValues.TAG, "InterruptedException when taking webView screenshot ", e)
        }
    }

    private fun saveBitmapToFile(bitmap: Bitmap, outputFile: File) {
        BufferedOutputStream(FileOutputStream(outputFile)).use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, it)
            it.flush()
        }
    }

}