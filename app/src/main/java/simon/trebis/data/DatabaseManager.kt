package simon.trebis.data

import android.arch.lifecycle.LiveData
import android.content.Context
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import simon.trebis.data.dao.EntryDao
import simon.trebis.data.dao.JobDao
import simon.trebis.data.dao.WebsiteDao
import simon.trebis.data.entity.Entry
import simon.trebis.data.entity.Job
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

    private val websiteDao: WebsiteDao
    private val entryDao: EntryDao
    private val jobDao: JobDao

    init {
        val database = TrebisDatabase.getDatabase(context)

        websiteDao = database.websiteDao()
        entryDao = database.entryDao()
        jobDao = database.jobDao()
    }

    fun createJob(websiteId: Long, schedulerId: Int): Deferred<Long?> {
        return async {
            jobDao.insert(Job(websiteId, schedulerId))
        }
    }

    fun getJobForWebsite(websiteId: Long): Deferred<Job?> {
        return async {
            jobDao.getWebsiteJob(websiteId)
        }
    }

    fun updateJob(job: Job) {
        launch {
            jobDao.update(job)
        }
    }

    fun deleteJob(job: Job) {
        launch {
            jobDao.delete(job)
        }
    }

    fun createEntry(websiteId: Long, snapshot: ByteArray): Deferred<Long?> {
        return async {
            entryDao.insert(Entry(websiteId, snapshot))
        }
    }

    fun getEntriesForWebsite(websiteId: Long): LiveData<List<Entry>> {
        return entryDao.getAllForWebsite(websiteId)
    }

    fun updateEntry(entry: Entry) {
        launch {
            entryDao.update(entry)
        }
    }

    fun deleteEntry(entry: Entry) {
        launch {
            entryDao.delete(entry)
        }
    }

    fun createWebsite(website: Website): Deferred<Long?> {
        return async {
            websiteDao.insert(website)
        }
    }

    fun getAllWebsites(): LiveData<List<Website>> {
        return websiteDao.getAll()
    }

    fun getWebsite(websiteId: Long): LiveData<Website> {
        return websiteDao.get(websiteId)
    }

    fun updateWebsite(website: Website) {
        launch {
            websiteDao.update(website)
        }
    }

    fun deleteWebsite(website: Website) {
        launch {
            websiteDao.delete(website)
        }
    }

}
