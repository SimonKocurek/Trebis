package kocurek.simon.trebis.storage.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import kocurek.simon.trebis.storage.entity.Subpage;

@Dao
public interface SubpageDao {

    @Query("SELECT * FROM subpage WHERE layoutId = :id")
    List<Subpage> getForLayout(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Subpage subpage);

    @Update
    void update(Subpage subpage);

    @Delete
    void delete(Subpage subpage);

}
