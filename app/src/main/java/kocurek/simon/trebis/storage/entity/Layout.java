package kocurek.simon.trebis.storage.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

@Entity
public class Layout {

    @PrimaryKey
    public int id;

    public String name;

    public Bitmap picture;

    public

}
