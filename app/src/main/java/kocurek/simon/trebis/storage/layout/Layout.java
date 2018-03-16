package kocurek.simon.trebis.storage.layout;

import java.util.ArrayList;
import java.util.List;

import kocurek.simon.trebis.storage.subpage.Subpage;

public class Layout {

    public final int id;

    public final String name;

    public final int imageId;

    public final List<Subpage> content;

    public Layout(int id, String name, int imageId) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
        this.content = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Layout layout = (Layout) o;

        return id == layout.id;
    }

    @Override
    public String toString() {
        return "Layout{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageId=" + imageId +
                '}';
    }

}
