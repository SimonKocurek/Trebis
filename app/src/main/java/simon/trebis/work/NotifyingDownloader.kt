package simon.trebis.work

import android.content.Context
import simon.trebis.file.FileUtils

class NotifyingDownloader(context: Context) : Downloader(context) {

    override fun handle(entryId: Long, websiteId: Long, bitmap: ByteArray) {
        FileUtils(context).store(entryId, bitmap)
        Notifier(context).showNotification(entryId, websiteId)
    }

}
