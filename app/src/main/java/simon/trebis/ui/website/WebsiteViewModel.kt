package simon.trebis.ui.website

import android.arch.lifecycle.ViewModel
import simon.trebis.data.entity.Entry
import java.util.*

class WebsiteViewModel : ViewModel() {

    var entries: List<Entry> = ArrayList()
    var selectedDate = Date()

}
