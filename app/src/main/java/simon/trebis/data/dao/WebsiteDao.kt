package simon.trebis.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import simon.trebis.data.entity.Website


@Dao
interface WebsiteDao {

    @Query("SELECT * FROM website")
    fun getAll(): LiveData<List<Website>>

    @Query("SELECT * FROM website WHERE name LIKE :name OR url LIKE :name")
    fun findByName(name: String): LiveData<List<Website>>

    @Insert
    fun insert(website: Website)

    @Delete
    fun delete(website: Website)

}