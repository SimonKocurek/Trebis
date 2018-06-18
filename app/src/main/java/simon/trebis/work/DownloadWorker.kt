package simon.trebis.work

import android.support.v7.preference.PreferenceManager
import androidx.work.Worker
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.experimental.launch
import simon.trebis.Const.Companion.ACCESS_TOKEN
import simon.trebis.Const.Companion.NO_ID
import simon.trebis.Const.Companion.WEBSITE_ID
import simon.trebis.Const.Companion.WEBSITE_URL
import simon.trebis.R
import simon.trebis.data.DatabaseManager
import simon.trebis.file.FileUtils
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

class DownloadWorker : Worker() {

    companion object {
        private const val baseUrl = "https://restpack.io/api/screenshot/v4/capture"
        private val delay = TimeUnit.SECONDS.toMillis(5)
    }

    override fun doWork(): WorkerResult {
        applicationContext.let {
            val preferences = PreferenceManager.getDefaultSharedPreferences(it)

            val url: String = inputData.getString(WEBSITE_URL, "")
            val websiteId: Long = inputData.getLong(WEBSITE_ID, NO_ID)
            val format: String = preferences.getString(it.getString(R.string.snapshot_format), "png")
            val ttl = TimeUnit.HOURS.toMillis(preferences.getInt(it.getString(R.string.snapshot_frequency), 12).toLong())
            val width = preferences.getInt(it.getString(R.string.snapshot_width), 768)
            val userAgent = preferences.getString(it.getString(R.string.snapshot_browser), "Mozilla/5.0 (X11; CrOS x86_64 8172.45.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.64 Safari/537.36")

            sendRequest(url, format, ttl, websiteId, width, userAgent)
        }

        return WorkerResult.SUCCESS
    }

    private fun sendRequest(
            url: String,
            format: String,
            ttl: Long,
            websiteId: Long,
            width: Int,
            userAgent: String
    ) {
        val requestUrl = createRequestUrl(url, format, ttl, width, userAgent)
        val queue = Volley.newRequestQueue(applicationContext)

        queue.add(ByteRequest(
                Request.Method.GET,
                requestUrl,
                responseListener(queue, websiteId),
                errorListener(queue)
        ))
    }

    private fun errorListener(queue: RequestQueue) = Response.ErrorListener { queue.stop() }

    private fun responseListener(queue: RequestQueue, websiteId: Long): Response.Listener<ByteArray> {
        return Response.Listener { bitmap ->
            launch {
                storeResponse(websiteId, bitmap)
                queue.stop()
            }
        }
    }

    private suspend fun storeResponse(websiteId: Long, bitmap: ByteArray) {
        DatabaseManager
                .instance(applicationContext)
                .createEntry(websiteId)
                .await()
                ?.let { entryId -> FileUtils(applicationContext).store(entryId, bitmap) }
    }

    private fun createRequestUrl(
            url: String,
            format: String,
            ttl: Long,
            width: Int,
            userAgent: String
    ): String {
        return "$baseUrl?" +
                "url=${URLEncoder.encode(url, "UTF-8")}&" +
                "format=$format&" +
                "delay=$delay&" +
                "ttl=$ttl&" +
                "wait=network&" +
                "width=$width&" +
                "user_agent=${URLEncoder.encode(userAgent, "UTF-8")}&" +
                "access_token=$ACCESS_TOKEN"
    }
}
