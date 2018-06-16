package simon.trebis.data.dao

import android.arch.persistence.room.*
import simon.trebis.data.entity.Work

@Dao
interface WorkDao {

    @Query("SELECT * FROM work WHERE website_id = :websiteId")
    fun get(websiteId: Long): Work?

    @Update
    fun update(work: Work)

    @Insert
    fun insert(work: Work): Long?

    @Delete
    fun delete(work: Work)

    @Query("DELETE FROM work WHERE website_id = :websiteId")
    fun delete(websiteId: Long)

}