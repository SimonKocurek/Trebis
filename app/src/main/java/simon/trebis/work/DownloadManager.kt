package simon.trebis.work

import androidx.work.*
import simon.trebis.Const.Companion.WEBSITE_ID
import simon.trebis.Const.Companion.WEBSITE_URL
import simon.trebis.data.entity.Website
import java.util.concurrent.TimeUnit

class DownloadManager {

    private val workManager: WorkManager = WorkManager.getInstance()

    fun schedule(website: Website) {
        periodicWork(website).let { workManager.enqueue(it) }
    }

    fun unschedule(website: Website) {
        workManager.cancelAllWorkByTag(website.id.toString())
    }

    private fun periodicWork(website: Website): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<DownloadWorker>(1, TimeUnit.HOURS)
                .setInputData(inputData(website))
                .setConstraints(constraints())
                .addTag(website.id.toString())
                .build()
    }

    private fun inputData(website: Website): Data {
        return mapOf(
                WEBSITE_ID to website.id,
                WEBSITE_URL to website.url
        ).toWorkData()
    }

    private fun constraints(): Constraints {
        return Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .build()
    }

}
