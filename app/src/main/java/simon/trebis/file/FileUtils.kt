package simon.trebis.file

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.support.v7.preference.PreferenceManager
import simon.trebis.R
import java.io.File

class FileUtils(private val context: Context) {

    fun store(entryId: Long, bitmap: ByteArray) {
        val fileStream = if (canUseExternalStorage()) {
            File(externalStorageDirectory(), entryId.toString()).outputStream()
        } else {
            context.openFileOutput(entryId.toString(), Context.MODE_PRIVATE)
        }

        fileStream.use {
            it.write(bitmap)
        }
    }

    fun fileUri(entryId: Long): Uri {
        if (canUseExternalStorage()) {
            val externalFile = File(externalStorageDirectory(), entryId.toString())
            if (externalFile.exists()) {
                return Uri.fromFile(externalFile)
            }
        }

        return Uri.fromFile(File(context.filesDir, entryId.toString()))
    }

    fun remove(entryId: Long) {
        context.deleteFile(entryId.toString())

        if (canUseExternalStorage()) {
            File(externalStorageDirectory(), entryId.toString()).delete()
        }
    }

    private fun canUseExternalStorage(): Boolean {
        val sdCardMounted = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        val preferSdCard = PreferenceManager
                .getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.prefer_sd), true)
        return sdCardMounted && preferSdCard
    }

    private fun externalStorageDirectory(): File {
        val externalStorage = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val directory = File(externalStorage, context.getString(R.string.app_name))
        directory.mkdirs()

        return directory
    }

}
