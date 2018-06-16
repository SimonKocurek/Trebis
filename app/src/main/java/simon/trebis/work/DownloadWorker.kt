package simon.trebis.work

import androidx.work.Worker
import com.mashape.unirest.http.Unirest
import simon.trebis.Const.Companion.ACCESS_TOKEN
import simon.trebis.Const.Companion.NO_ID
import simon.trebis.Const.Companion.WEBSITE_ID
import simon.trebis.Const.Companion.WEBSITE_URL
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

class DownloadWorker : Worker() {

    override fun doWork(): WorkerResult {
        val url = inputData.getString(WEBSITE_URL, "")
        val encodedUrl = URLEncoder.encode(url, "UTF-8").replace("\\+", "%20")
        val websiteId = inputData.getLong(WEBSITE_ID, NO_ID)
        val format = "png"
        val delay = TimeUnit.SECONDS.toMillis(5)
        val ttl = TimeUnit.HOURS.toMillis(12)

        val response = Unirest
                .post("https://restpack.io/api/screenshot/v4/capture")
                .header("x-access-token", ACCESS_TOKEN)
                .body("url=$encodedUrl&format=$format&delay=$delay&ttl=$ttl&wait=network")
                .asString()

        return WorkerResult.SUCCESS
    }

}
