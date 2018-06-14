package simon.trebis.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import simon.trebis.Const.Companion.ACTION_FETCH_WEBSITE
import simon.trebis.Const.Companion.WEBSITE_ID
import simon.trebis.Const.Companion.WEBSITE_URL

class DownloadService : Service() {

    companion object {

        @JvmStatic
        fun startFetchAction(context: Context, url: String, websiteId: Long) {
            val intent = Intent(context, DownloadService::class.java).apply {
                action = ACTION_FETCH_WEBSITE
                putExtra(WEBSITE_URL, url)
                putExtra(WEBSITE_ID, websiteId)
            }

            context.startService(intent)
        }

    }

    var serviceHandler: DownloadServiceHandler? = null

    override fun onCreate() {
        super.onCreate()
        serviceHandler = DownloadServiceHandler(Looper.getMainLooper(), this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceHandler?.obtainMessage()?.apply {
            arg1 = startId
            obj = intent

            serviceHandler?.sendMessage(this)
        }

        return START_NOT_STICKY;
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}
