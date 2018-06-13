package simon.trebis.ui.main

import android.arch.lifecycle.ViewModel
import simon.trebis.data.entity.Website
import simon.trebis.ui.SortType

class MainViewModel : ViewModel() {

    val layouts = ArrayList<Website>()
    var filter = ""
    var sortType: SortType = SortType.FIRST_CREATED

}
