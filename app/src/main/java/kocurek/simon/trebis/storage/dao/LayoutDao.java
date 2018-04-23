package kocurek.simon.trebis.storage.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import kocurek.simon.trebis.storage.entity.Layout;

@Dao
public interface LayoutDao {

    @Query("SELECT * FROM layout WHERE id = :id")
    Layout get(int id);

    @Query("SELECT * FROM layout")
    List<Layout> getAll();

    @Query("SELECT * FROM layout Where id = :id")
    List<Layout> getHistory(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Layout layout);

    @Update
    void update(Layout layout);

    @Delete
    void delete(Layout layout);

}