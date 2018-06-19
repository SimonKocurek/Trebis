package simon.trebis.work

import android.content.Context
import android.support.v7.preference.PreferenceManager
import androidx.work.*
import simon.trebis.Const.Companion.WEBSITE_ID
import simon.trebis.Const.Companion.WEBSITE_URL
import simon.trebis.R
import simon.trebis.data.entity.Website
import java.util.concurrent.TimeUnit

class DownloadManager(private val context: Context) {

    private val workManager: WorkManager = WorkManager.getInstance()

    fun schedule(website: Website) {
        Downloader(context).download(website.url, website.id!!)
        periodicWork(website).let { workManager.enqueue(it) }
    }

    fun unschedule(website: Website) {
        workManager.cancelAllWorkByTag(website.id.toString())
    }

    private fun periodicWork(website: Website): PeriodicWorkRequest {
        val repeatInterval = PreferenceManager
                .getDefaultSharedPreferences(context)
                .getInt(context.getString(R.string.snapshot_frequency), 12)
                .toLong()

        return PeriodicWorkRequestBuilder<DownloadWorker>(repeatInterval, TimeUnit.HOURS)
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
