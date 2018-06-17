package simon.trebis.file

import android.content.Context
import android.os.Environment

class FileUtils(private val applicationContext: Context) {

    fun store(entryId: Long, bitmap: ByteArray) {
        applicationContext.openFileOutput(entryId.toString(), Context.MODE_PRIVATE).use {
            it.write(bitmap)
        }
    }

    fun read(entryId: Long): ByteArray {
        return applicationContext.openFileInput(entryId.toString()).readBytes()
    }

    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    private fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

}
