package simon.trebis.work

import androidx.work.Worker
import simon.trebis.Const
import simon.trebis.service.DownloadService

class DownloadWorker : Worker() {

    override fun doWork(): WorkerResult {
        DownloadService.startFetchAction(
                applicationContext,
                inputData.getString(Const.WEBSITE_URL, ""),
                inputData.getLong(Const.WEBSITE_ID, -1)
        )

        return WorkerResult.SUCCESS
    }

}
