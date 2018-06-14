package simon.trebis.ui.main

import android.view.MenuItem
import android.widget.SearchView

class MainFragmentSearch(search: MenuItem, private val viewModel: MainViewModel) {

    private val searchView: SearchView = search.actionView as SearchView

    var onFilterChange: (String) -> Unit = {}

    init {
        search.isVisible = true
        searchView.setOnSearchClickListener { searchView.setQuery(viewModel.filter, false) }
        searchView.setOnQueryTextListener(QueryTextListener { query -> onFilterChange(query) })
    }

}
