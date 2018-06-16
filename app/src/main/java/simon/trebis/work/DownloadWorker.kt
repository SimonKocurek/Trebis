package simon.trebis.work

import androidx.work.Worker
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import simon.trebis.Const.Companion.ACCESS_TOKEN
import simon.trebis.Const.Companion.NO_ID
import simon.trebis.Const.Companion.WEBSITE_ID
import simon.trebis.Const.Companion.WEBSITE_URL
import simon.trebis.data.DatabaseManager
import java.util.concurrent.TimeUnit

class DownloadWorker : Worker() {

    companion object {
        private const val baseUrl = "https://restpack.io/api/screenshot/v4/capture"
        private val delay = TimeUnit.SECONDS.toMillis(5)
    }

    override fun doWork(): WorkerResult {
        val url = inputData.getString(WEBSITE_URL, "")
        val websiteId = inputData.getLong(WEBSITE_ID, NO_ID)
        val format = "png"
        val ttl = TimeUnit.HOURS.toMillis(12)

        sendRequest(url, format, ttl, websiteId)

        return WorkerResult.SUCCESS
    }

    private fun sendRequest(url: String, format: String, ttl: Long, websiteId: Long) {
        val requestUrl = createRequestUrl(url, format, ttl)
        val queue = Volley.newRequestQueue(applicationContext)

        queue.add(StringRequest(
                Request.Method.GET,
                requestUrl,
                responseListener(queue, websiteId),
                errorListener(queue)
        ))
    }

    private fun errorListener(queue: RequestQueue) = Response.ErrorListener { queue.stop() }

    private fun responseListener(queue: RequestQueue, websiteId: Long) = Response.Listener<String> { response ->
        val bitmap = response.toByteArray()
        DatabaseManager.instance(applicationContext).createEntry(websiteId, bitmap)
        queue.stop()
    }

    private fun createRequestUrl(url: String, format: String, ttl: Long): String {
        return "$baseUrl?" +
                "url=$url&" +
                "format=$format&" +
                "delay=$delay&" +
                "ttl=$ttl&" +
                "wait=network&" +
                "access_token=$ACCESS_TOKEN"
    }

}
