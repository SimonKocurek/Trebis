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
        @field:PrimaryKey
        @field:NonNull
        @field:ColumnInfo(name = "entry_id")
        val id: Int,

        @field:NotNull
        @field:ColumnInfo(name = "website_id")
        val website: Int,

        @field:NotNull
        @field:ColumnInfo(name = "icon_path")
        val iconPath: String,

        @field:NotNull
        @field:ColumnInfo(name = "time_created")
        val date: Date = Date()
)