package simon.trebis.data.converters

import android.arch.persistence.room.TypeConverter
import java.util.*

class Converter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun fromUUID(value: String?): UUID? {
        return if (value == null) null else UUID.fromString(value)
    }

    @TypeConverter
    fun UUIDToString(uuid: UUID): String {
        return uuid.toString()
    }

}