package simon.trebis.job

import android.content.Intent
import com.evernote.android.job.Job
import com.evernote.android.job.JobManager
import com.evernote.android.job.JobRequest
import com.evernote.android.job.util.support.PersistableBundleCompat
import simon.trebis.Const.Companion.WEBSITE_ID
import simon.trebis.Const.Companion.WEBSITE_URL
import simon.trebis.service.DownloadService
import java.util.concurrent.TimeUnit


class TrebisDownloadJob : Job() {

    companion object {
        const val TAG = "DOWNLOAD_JOB"
    }

    override fun onRunJob(params: Job.Params): Job.Result {
        val intent = Intent(context, DownloadService::class.java).apply {
            putExtra(WEBSITE_ID, params.extras.getInt(WEBSITE_ID, -1))
            putExtra(WEBSITE_URL, params.extras.getString(WEBSITE_URL, ""))
        }

        context.startService(intent)
        return Job.Result.SUCCESS
    }

    fun scheduleJob(websiteId: Int, url: String): Int {
        val extras = PersistableBundleCompat().apply {
            putInt(WEBSITE_ID, websiteId)
            putString(WEBSITE_URL, url)
        }

        return JobRequest.Builder(TAG)
                .setPeriodic(TimeUnit.HOURS.toMillis(12))
                .setRequiredNetworkType(JobRequest.NetworkType.UNMETERED)
                .setRequiresStorageNotLow(true)
                .setRequirementsEnforced(true)
                .setUpdateCurrent(false)
                .setExtras(extras)
                .build()
                .schedule()
    }

    private fun cancelJob(jobId: Int) {
        JobManager.instance().cancel(jobId)
    }

}
