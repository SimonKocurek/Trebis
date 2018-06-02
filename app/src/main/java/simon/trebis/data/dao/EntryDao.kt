package simon.trebis.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import simon.trebis.data.entity.Entry


@Dao
interface EntryDao {

    @Query("SELECT * FROM entry")
    fun getAll(): LiveData<List<Entry>>

    @Query("SELECT * FROM entry WHERE website_id = :websiteId")
    fun getAllForWebsite(websiteId: Int): LiveData<List<Entry>>

    @Insert
    fun insert(entry: Entry)

    @Delete
    fun delete(entry: Entry)

}