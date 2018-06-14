package simon.trebis.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import simon.trebis.data.entity.Job


@Dao
interface JobDao {

    @Query("SELECT * FROM job WHERE website_id = :websiteId")
    fun getWebsiteJob(websiteId: Int): LiveData<Job>

    @Update
    fun update(job: Job)

    @Insert
    fun insert(job: Job): Long?

    @Delete
    fun delete(job: Job)

}