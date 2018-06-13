package simon.trebis.ui.create

import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebView


class PreviewChromeVebViewClient(private val onReceiveFavicon: (Bitmap) -> Unit) : WebChromeClient() {

    override fun onReceivedIcon(view: WebView, icon: Bitmap) {
        super.onReceivedIcon(view, icon)
        onReceiveFavicon(icon)
    }

}
