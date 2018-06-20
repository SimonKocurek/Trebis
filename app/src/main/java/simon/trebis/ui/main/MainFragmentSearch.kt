package simon.trebis.ui.main

import android.support.v7.widget.SearchView
import android.view.MenuItem

class MainFragmentSearch(search: MenuItem, private val viewModel: MainViewModel) {

    private val searchView: SearchView = search.actionView as SearchView

    var onFilterChange: (String) -> Unit = {}

    init {
        search.isVisible = true
        searchView.setOnSearchClickListener { searchView.setQuery(viewModel.filter, true) }
        searchView.setOnQueryTextListener(OnQueryTextListener { query -> onFilterChange(query) })
    }

}
