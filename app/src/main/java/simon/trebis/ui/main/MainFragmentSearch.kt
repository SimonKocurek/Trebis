package simon.trebis.ui.main

import android.view.MenuItem
import android.widget.SearchView

class MainFragmentSearch(val search: MenuItem) {

    private val searchView: SearchView = search.actionView as SearchView

    init {
        search.isVisible = true
        searchView.setOnQueryTextFocusChangeListener { v, hasFocus -> }
        searchView.setOnSearchClickListener { }
    }

    fun setQuery(query: String) {
        searchView.setQuery(query, true)
    }

}
