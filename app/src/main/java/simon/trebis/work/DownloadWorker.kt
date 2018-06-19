package simon.trebis.work

import androidx.work.Worker
import simon.trebis.Const

class DownloadWorker : Worker() {

    override fun doWork(): WorkerResult {
        val url: String = inputData.getString(Const.WEBSITE_URL, "")
        val websiteId: Long = inputData.getLong(Const.WEBSITE_ID, Const.NO_ID)

        Downloader(applicationContext).download(url, websiteId)

        return WorkerResult.SUCCESS
    }

}
