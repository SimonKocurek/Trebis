package simon.trebis.work

import android.content.Context
import android.support.v7.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.experimental.launch
import simon.trebis.Const
import simon.trebis.R
import simon.trebis.data.DatabaseManager
import simon.trebis.file.FileUtils
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

class Downloader(val context: Context) {

    companion object {
        private const val RETRTY_LIMIT = 5

        private const val baseUrl = "https://restpack.io/api/screenshot/v4/capture"
        private val delay = TimeUnit.SECONDS.toMillis(5)
        private val ttl = TimeUnit.HOURS.toMillis(4)
    }

    private val queue = Volley.newRequestQueue(context)
    private var request: ByteRequest? = null
    private var retries = 0

    fun download(url: String, websiteId: Long) {
        context.let {
            val preferences = PreferenceManager.getDefaultSharedPreferences(it)

            val format: String = preferences.getString(it.getString(R.string.snapshot_format), "png")
            val width = preferences.getInt(it.getString(R.string.snapshot_width), 768)
            val userAgent = preferences.getString(it.getString(R.string.snapshot_browser), "Mozilla/5.0 (X11; CrOS x86_64 8172.45.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.64 Safari/537.36")

            sendRequest(url, format, websiteId, width, userAgent)
        }
    }

    private fun sendRequest(
            url: String,
            format: String,
            websiteId: Long,
            width: Int,
            userAgent: String
    ) {
        val requestUrl = createRequestUrl(url, format, width, userAgent)

        request = ByteRequest(
                Request.Method.GET,
                requestUrl,
                responseListener(websiteId),
                errorListener()
        )
        retries = 0

        queue.add(request)
    }

    private fun errorListener() = Response.ErrorListener {
        if (retries++ > RETRTY_LIMIT) {
            queue.stop()
        } else {
            queue.add(request)
        }
    }

    private fun responseListener(websiteId: Long): Response.Listener<ByteArray> {
        return Response.Listener { bitmap ->
            launch {
                storeResponse(websiteId, bitmap)
                queue.stop()
            }
        }
    }

    private suspend fun storeResponse(websiteId: Long, bitmap: ByteArray) {
        DatabaseManager
                .instance(context)
                .createEntry(websiteId)
                .await()
                ?.let { entryId -> handle(entryId, websiteId, bitmap) }
    }

    private fun handle(entryId: Long, websiteId: Long, bitmap: ByteArray) {
        FileUtils(context).store(entryId, bitmap)
        Notifier(context).showNotification(entryId, websiteId)
    }

    private fun createRequestUrl(url: String, format: String, width: Int, userAgent: String): String {
        return "$baseUrl?" +
                "url=${URLEncoder.encode(url, "UTF-8")}&" +
                "format=$format&" +
                "delay=$delay&" +
                "ttl=$ttl&" +
                "wait=network&" +
                "width=$width&" +
                "user_agent=${URLEncoder.encode(userAgent, "UTF-8")}&" +
                "access_token=${Const.ACCESS_TOKEN}"
    }

}
