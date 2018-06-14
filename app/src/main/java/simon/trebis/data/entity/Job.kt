package simon.trebis.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import org.jetbrains.annotations.NotNull

@Entity(
        tableName = "job",
        foreignKeys = [
            ForeignKey(
                    entity = Website::class,
                    parentColumns = ["website_id"],
                    childColumns = ["website_id"],
                    onDelete = CASCADE
            )
        ]
)
class Job(
        @NotNull
        @ColumnInfo(name = "website_id")
        val website: Long,

        @NotNull
        @ColumnInfo(name = "scheduler_id")
        var schedulerId: Int
) {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "job_id")
    var id: Long? = null
}