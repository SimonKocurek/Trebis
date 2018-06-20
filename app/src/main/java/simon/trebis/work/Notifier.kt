package simon.trebis.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.preference.PreferenceManager
import kotlinx.coroutines.experimental.launch
import simon.trebis.MainActivity
import simon.trebis.R
import simon.trebis.data.DatabaseManager
import simon.trebis.data.entity.Website


class Notifier(private val context: Context) {

    private val CHANNEL_ID = "simon.trebis.notification"

    init {
        createNotificationChannel()
    }

    fun showNotification(id: Long, websiteId: Long) {
        val database = DatabaseManager.instance(context)

        launch {
            val website = database.getWebsite(websiteId).await()
            showNotificationWithWebsiteInfo(id, website!!)
        }
    }

    private fun showNotificationWithWebsiteInfo(id: Long, website: Website) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(context.getString(R.string.snapshot_fetched))
                .setContentText(website.url)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(startActivityIntent())
                .setAutoCancel(true)

        if (areNotificationsEnabled()) {
            NotificationManagerCompat.from(context).apply {
                notify(id.toInt(), builder.build())
            }
        }
    }

    private fun startActivityIntent(): PendingIntent? {
        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        return PendingIntent.getActivity(context, 0, notificationIntent, 0)
    }

    private fun areNotificationsEnabled(): Boolean {
        return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.notifications_enabled), true)
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
