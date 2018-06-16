package simon.trebis.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import simon.trebis.Const.Companion.DEVICE_HEIGHT
import simon.trebis.Const.Companion.DEVICE_WIDTH
import simon.trebis.Const.Companion.WEBSITE_ID
import simon.trebis.Const.Companion.WEBSITE_URL

class DownloadService : IntentService(SERVICE_NAME) {

    companion object {

        private const val SERVICE_NAME = "TrebisDownloadService"

        @JvmStatic
        fun startFetchAction(
                context: Context,
                url: String,
                websiteId: Long,
                deviceWidth: Int,
                deviceHeight: Int
        ) {
            val intent = Intent(context, DownloadService::class.java).apply {
                putExtra(WEBSITE_URL, url)
                putExtra(WEBSITE_ID, websiteId)
                putExtra(DEVICE_WIDTH, deviceWidth)
                putExtra(DEVICE_HEIGHT, deviceHeight)
            }

            context.startService(intent)
        }

    }

    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            DownloadServiceHandler(this, it).handle()
        }
    }

}
