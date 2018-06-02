package simon.trebis.data

import android.arch.persistence.room.*
import android.content.Context
import simon.trebis.data.convertors.Convertor
import simon.trebis.data.dao.EntryDao
import simon.trebis.data.dao.WebsiteDao
import simon.trebis.data.entity.Entry
import simon.trebis.data.entity.Website

@Database(
        entities = [Entry::class, Website::class],
        version = 1
)
@TypeConverters(Convertor::class)
abstract class TrebisDatabase : RoomDatabase() {

    abstract fun entryDao(): EntryDao
    abstract fun websiteDao(): WebsiteDao

    companion object {

        private var INSTANCE: TrebisDatabase? = null

        internal fun getDatabase(context: Context): TrebisDatabase {
            if (INSTANCE == null) {
                synchronized(TrebisDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                                context.applicationContext,
                                TrebisDatabase::class.java,
                                "trebis_database"
                        ).build()
                    }
                }
            }

            return INSTANCE!!
        }
    }

}