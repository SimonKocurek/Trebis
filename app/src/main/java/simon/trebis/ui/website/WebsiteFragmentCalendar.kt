package simon.trebis.ui.website

import android.app.DatePickerDialog
import android.content.Context
import android.view.MenuItem
import android.widget.DatePicker
import java.util.*

class WebsiteFragmentCalendar(
        context: Context,
        calendarItem: MenuItem,
        private val viewModel: WebsiteViewModel?
) : DatePickerDialog.OnDateSetListener {

    private val calendarDialog: DatePickerDialog

    init {
        calendarDialog = Calendar.getInstance().let {
            val year = it.get(Calendar.YEAR)
            val month = it.get(Calendar.MONTH)
            val day = it.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(context, this, year, month, day)
        }

        calendarItem.isVisible = true
        calendarItem.setOnMenuItemClickListener { calendarDialog.show(); true }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

    }

}
