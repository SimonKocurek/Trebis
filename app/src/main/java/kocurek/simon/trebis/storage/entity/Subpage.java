package kocurek.simon.trebis.storage.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Layout.class,
                parentColumns = "id",
                childColumns = "layoutId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Subpage {

    @PrimaryKey
    public int id;

    public int layoutId;

    public String url;

}
