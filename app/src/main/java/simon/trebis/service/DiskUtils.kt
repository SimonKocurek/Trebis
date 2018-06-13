package simon.trebis.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.io.File


class DiskUtils {

    companion object {

        fun fileFor(id: Int, name: String, context: Context): File {
            return File(context.filesDir, "$id/$name")
        }

        fun saveBitmapToFile(bitmap: Bitmap, name: String, context: Context) {
            launch {
                context.openFileOutput(name, Context.MODE_PRIVATE).use {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, it)
                }
            }
        }

        fun getBitmapFromFile(file: File, context: Context): Deferred<Bitmap> {
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888

            return async { BitmapFactory.decodeFile(file.path, options) }
        }

        /* Checks if external storage is available for read and write */
//        fun isExternalStorageWritable(): Boolean {
//            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
//        }
//
//        /* Checks if external storage is available to at least read */
//        fun isExternalStorageReadable(): Boolean {
//            return Environment.getExternalStorageState() in
//                    setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
//        }
//
//        fun getPrivateAlbumStorageDir(context: Context, albumName: String): File? {
//            // Get the directory for the app's private pictures directory.
//            val file = File(context.getExternalFilesDir(
//                    Environment.DIRECTORY_PICTURES), albumName)
//            if (!file?.mkdirs()) {
//                Log.e(LOG_TAG, "Directory not created")
//            }
//            return file
//        }
    }
}