package simon.trebis.file

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.support.v7.preference.PreferenceManager
import simon.trebis.R
import java.io.File

class FileUtils(private val context: Context) {

    fun store(entryId: Long, bitmap: ByteArray) {
        if (canUseExternalStorage()) {
            File(externalStorageDirectory(), entryId.toString()).outputStream().use { it.write(bitmap) }
        } else {
            context.openFileOutput(entryId.toString(), Context.MODE_PRIVATE).use { it.write(bitmap) }
        }
    }

    fun fileUri(entryId: Long): Uri {
        if (canUseExternalStorage()) {
            File(externalStorageDirectory(), entryId.toString()).let {
                if (it.isFile) {
                    return Uri.fromFile(it)
                }
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
        val hasPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        return sdCardMounted && preferSdCard && hasPermission
    }

    private fun externalStorageDirectory(): File {
        val externalStorage = Environment.getExternalStorageDirectory()
        File(externalStorage, context.getString(R.string.app_name)).let {
            it.mkdir()
            return it
        }
    }

}
