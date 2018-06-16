package simon.trebis.work

import android.content.Context
import androidx.work.*
import kotlinx.coroutines.experimental.launch
import simon.trebis.Const.Companion.WEBSITE_ID
import simon.trebis.Const.Companion.WEBSITE_URL
import simon.trebis.data.DatabaseManager
import simon.trebis.data.entity.Website
import java.util.concurrent.TimeUnit

class DownloadManager(context: Context) {

    private val databaseManager: DatabaseManager = DatabaseManager.instance(context)
    private val workManager: WorkManager = WorkManager.getInstance()

    fun schedule(website: Website) {
        immediateWork(website).let { workManager.enqueue(it) }
        periodicWork(website).let {
            workManager.enqueue(it)
            databaseManager.createWork(website.id!!, it.id)
        }
    }

    fun unschedule(websiteId: Long) {
        workManager.cancelAllWorkByTag(websiteId)
        launch {
            databaseManager
                    .getWork(websiteId)
                    .await()
                    ?.let {
                        workManager.cancelWorkById(it.id)
                        databaseManager.deleteWork(it)
                    }
        }
    }

    private fun immediateWork(website: Website): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<DownloadWorker>()
                .setInputData(inputData(website))
                .setConstraints(constraints())
                .build()
    }

    private fun periodicWork(website: Website): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<DownloadWorker>(12, TimeUnit.HOURS)
                .setInputData(inputData(website))
                .setConstraints(constraints())
                .build()
    }

    private fun inputData(website: Website): Data {
        return mapOf(WEBSITE_ID to website.id, WEBSITE_URL to website.url).toWorkData()
    }

    private fun constraints(): Constraints {
        return Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .build()
    }

}
