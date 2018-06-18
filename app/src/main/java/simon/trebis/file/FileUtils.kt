package simon.trebis.file

import android.content.Context
import android.net.Uri
import java.io.File

class FileUtils(private val applicationContext: Context) {

    fun store(entryId: Long, bitmap: ByteArray) {
        applicationContext.openFileOutput(entryId.toString(), Context.MODE_PRIVATE).use {
            it.write(bitmap)
        }
    }

    fun fileUri(entryId: Long): Uri {
        return Uri.fromFile(File(applicationContext.filesDir, entryId.toString()))
    }

    fun read(entryId: Long): ByteArray {
        return applicationContext.openFileInput(entryId.toString()).readBytes()
    }

    fun remove(entryId: Long) {
        applicationContext.deleteFile(entryId.toString())
    }

}
