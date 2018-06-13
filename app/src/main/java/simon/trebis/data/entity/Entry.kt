package simon.trebis.data.entity

import android.arch.persistence.room.*
import android.support.annotation.NonNull
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity(
        tableName = "entry",
        indices = [Index(value = ["website_id", "time_created"], unique = true)],
        foreignKeys = [ForeignKey(entity = Website::class, parentColumns = ["website_id"], childColumns = ["website_id"])]
)
class Entry(
        @NotNull
        @ColumnInfo(name = "website_id")
        val website: Int,

        @NotNull
        @ColumnInfo(name = "snapshot", typeAffinity = ColumnInfo.BLOB)
        val snapshot: ByteArray,

        @NotNull
        @ColumnInfo(name = "time_created")
        val date: Date = Date()
) {
        @PrimaryKey(autoGenerate = true)
        @NonNull
        @ColumnInfo(name = "entry_id")
        var id: Int? = null
}