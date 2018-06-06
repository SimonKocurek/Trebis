package simon.trebis.ui.main

import android.arch.lifecycle.ViewModel
import simon.trebis.data.entity.Website

class MainViewModel : ViewModel() {

    val layouts = ArrayList<Website>()
    var filter = ""
    var sortType: String? = null

}
