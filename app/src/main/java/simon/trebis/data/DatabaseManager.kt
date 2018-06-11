package simon.trebis.data

import android.arch.lifecycle.LiveData
import android.content.Context
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import simon.trebis.data.dao.EntryDao
import simon.trebis.data.dao.WebsiteDao
import simon.trebis.data.entity.Entry
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

    fun getEntriesForWebsite(websiteId: Int): LiveData<List<Entry>> {
        return entryDao.getAllForWebsite(websiteId)
    }

    fun createWebsite(): Deferred<Long?> {
        return async {
            websiteDao.insert(Website())
        }
    }

    fun getAllWebsites(): LiveData<List<Website>> {
        return websiteDao.getAll()
    }

    fun getWebsite(websiteId: Int): LiveData<Website> {
        return websiteDao.get(websiteId)
    }

    fun updateWebsite(website: Website) {
        return websiteDao.update(website)
    }

    fun deleteWebsite(website: Website) {
        launch {
            websiteDao.delete(website)
        }
    }

}
