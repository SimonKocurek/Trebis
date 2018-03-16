package kocurek.simon.trebis.storage.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;

@Entity
public class Layout {

    @PrimaryKey
    public int id;

    public String name;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] picture;

    public Date creationDate;

}
