package simon.trebis.job

import android.support.annotation.Nullable
import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator

class TrebisJobCreator : JobCreator {

    @Nullable
    override fun create(tag: String): Job? {
        return when (tag) {
            TrebisDownloadJob.TAG -> TrebisDownloadJob()
            else -> null
        }
    }

}

