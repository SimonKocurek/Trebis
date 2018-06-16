package simon.trebis.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import simon.trebis.data.converters.Converter
import simon.trebis.data.dao.EntryDao
import simon.trebis.data.dao.WebsiteDao
import simon.trebis.data.entity.Entry
import simon.trebis.data.entity.Website

@Database(
        entities = [Entry::class, Website::class],
        version = 1
)
@TypeConverters(Converter::class)
abstract class TrebisDatabase : RoomDatabase() {

    abstract fun entryDao(): EntryDao
    abstract fun websiteDao(): WebsiteDao

    companion object {

        private const val DB_NAME = "trebis_database"

        @Volatile
        private var INSTANCE: TrebisDatabase? = null

        internal fun getDatabase(context: Context): TrebisDatabase {
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }

            return INSTANCE!!
        }

        private fun buildDatabase(context: Context) = Room
                .databaseBuilder(context.applicationContext, TrebisDatabase::class.java, DB_NAME)
                .build()
    }

}
