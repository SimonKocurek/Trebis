package simon.trebis.ui

import simon.trebis.data.entity.Website

enum class SortType(val idx: Int, val comparator: Comparator<Website>) {

    ALPHABETICAL(0, Comparator { a, b -> a.name.compareTo(b.name) }),
    FIRST_CREATED(1, Comparator { a, b -> a.date.compareTo(b.date) }),
    LAST_CREATED(2, Comparator { a, b -> b.date.compareTo(a.date) });

    companion object {
        fun withIndex(idx: Int): SortType? {
            return values().find { it.idx == idx }
        }
    }

}
