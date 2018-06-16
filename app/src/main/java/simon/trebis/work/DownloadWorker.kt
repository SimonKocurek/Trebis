package simon.trebis.work

import androidx.work.Worker
import simon.trebis.Const.Companion.DEFAULT_HEIGHT
import simon.trebis.Const.Companion.DEFAULT_WIDHT
import simon.trebis.Const.Companion.DEVICE_HEIGHT
import simon.trebis.Const.Companion.DEVICE_WIDTH
import simon.trebis.Const.Companion.NO_ID
import simon.trebis.Const.Companion.WEBSITE_ID
import simon.trebis.Const.Companion.WEBSITE_URL
import simon.trebis.service.DownloadService

class DownloadWorker : Worker() {

    override fun doWork(): WorkerResult {
        DownloadService.startFetchAction(
                applicationContext,
                inputData.getString(WEBSITE_URL, ""),
                inputData.getLong(WEBSITE_ID, NO_ID),
                inputData.getInt(DEVICE_WIDTH, DEFAULT_WIDHT),
                inputData.getInt(DEVICE_HEIGHT, DEFAULT_HEIGHT)
        )

        return WorkerResult.SUCCESS
    }

}
