package simon.trebis.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import org.jetbrains.annotations.NotNull
import java.time.Instant
import java.util.*

@Entity(tableName = "website")
class Website(
        @field:PrimaryKey
        @field:NonNull
        @field:ColumnInfo(name = "website_id")
        val id: Int,

        @field:NotNull
        @field:ColumnInfo(name = "name")
        val name: String = "",

        @field:NotNull
        @field:ColumnInfo(name = "icon_path")
        val iconPath: String = "",

        @field:NotNull
        @field:ColumnInfo(name = "time_created")
        val date: Date = Date(),

        @field:NotNull
        @field:ColumnInfo(name = "url")
        val url: String = ""
)