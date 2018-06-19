package simon.trebis.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import simon.trebis.R

class Notifier(private val context: Context) {

    private val CHANNEL_ID = "simon.trebis.notification"

    init {
        createNotificationChannel()
    }

    fun showNotification(id: Long, websiteId: Long) {
        // TODO show website
        // TODO in preferences
        // TODO translate
        // TODO click intent
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(context.getString(R.string.snapshot_fetched))
                .setContentText(context.getString(R.string.downloadedanewsnapshot))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(context).apply {
            notify(id.toInt(), builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val description = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).also {
                it.description = description
            }

            context.getSystemService(NotificationManager::class.java).apply {
                createNotificationChannel(channel)
            }
        }
    }

}
