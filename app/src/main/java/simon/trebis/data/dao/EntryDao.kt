package simon.trebis.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import simon.trebis.data.entity.Entry


@Dao
interface EntryDao {

    @Query("SELECT * FROM entry WHERE website_id = :websiteId")
    fun getAll(websiteId: Long): LiveData<List<Entry>>

    @Update
    fun update(entry: Entry)

    @Insert
    fun insert(entry: Entry): Long?

    @Delete
    fun delete(entry: Entry)

}
