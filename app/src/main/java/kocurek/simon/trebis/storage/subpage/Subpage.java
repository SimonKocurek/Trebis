package kocurek.simon.trebis.storage.subpage;

public class Subpage {

    public final int id;

    public final String url;

    public Subpage(int id, String url) {
        this.id = id;
        this.url = url;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subpage subpage = (Subpage) o;

        return id == subpage.id;
    }

    @Override
    public String toString() {
        return "Subpage{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }

}
