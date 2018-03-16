package kocurek.simon.trebis.storage.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import kocurek.simon.trebis.storage.entity.Layout;

GET,GET_ALL,GET_HISTORY,ADD,UPDATE,DELETE

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

    @Insert
    void insertBothUsers(User user1, User user2);

    @Insert
    void insertUsersAndFriends(User user, List<User> friends);

    @Update
    void updateUsers(User... users);

    @Delete
    void deleteUsers(User... users);


    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

}
