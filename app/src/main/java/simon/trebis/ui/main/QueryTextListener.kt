package simon.trebis.ui.main

import android.support.v7.widget.SearchView

class OnQueryTextListener(private val onQuerySubmit: (String) -> Unit) :
        SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            onQuerySubmit(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText.isNullOrBlank()) {
            onQuerySubmit("")
        }
        return true
    }

}
