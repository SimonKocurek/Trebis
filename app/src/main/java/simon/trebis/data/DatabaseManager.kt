package simon.trebis.data

import android.arch.lifecycle.LiveData
import android.content.Context
import simon.trebis.data.dao.EntryDao
import simon.trebis.data.dao.WebsiteDao
import simon.trebis.data.entity.Website

class DatabaseManager private constructor(context: Context) {

    companion object {

        @Volatile
        private var INSTANCE: DatabaseManager? = null

        internal fun instance(context: Context): DatabaseManager {
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DatabaseManager(context).also {
                    INSTANCE = it
                }
            }

            return INSTANCE!!
        }

    }

    private var websiteDao: WebsiteDao
    private var entryDao: EntryDao

    init {
        val database = TrebisDatabase.getDatabase(context)

        websiteDao = database.websiteDao()
        entryDao = database.entryDao()
    }

    fun getAllWebsites(): LiveData<List<Website>> {
        return websiteDao.getAll()
    }

    fun createWebsite() {
        Thread {
            websiteDao.insert(Website())
        }.start()
    }

    fun deleteWebsite(website: Website) {
        Thread {
            websiteDao.delete(website)
        }.start()
    }

}
