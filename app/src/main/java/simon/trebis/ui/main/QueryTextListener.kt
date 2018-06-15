package simon.trebis.ui.main

import android.widget.SearchView

class QueryTextListener(private val onQuerySubmit: (String) -> Unit) :
        SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query == null) {
            onQuerySubmit("")
        } else {
            onQuerySubmit(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

}
