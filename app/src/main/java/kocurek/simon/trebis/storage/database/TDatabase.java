package kocurek.simon.trebis.storage.database;

import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import kocurek.simon.trebis.storage.converters.Converters;
import kocurek.simon.trebis.storage.dao.LayoutDao;
import kocurek.simon.trebis.storage.dao.SubpageDao;
import kocurek.simon.trebis.storage.entity.Layout;
import kocurek.simon.trebis.storage.entity.Subpage;

@android.arch.persistence.room.Database(entities = {Layout.class, Subpage.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class TDatabase extends RoomDatabase {

    public abstract LayoutDao layoutDao();

    public abstract SubpageDao subpageDao();

}