package simon.trebis.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity(
        tableName = "work",
        foreignKeys = [
            ForeignKey(
                    entity = Website::class,
                    parentColumns = ["website_id"],
                    childColumns = ["website_id"],
                    onDelete = CASCADE
            )
        ]
)
class Work(
        @NotNull
        @ColumnInfo(name = "website_id")
        val website: Long,

        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "work_id")
        val id: UUID
)
