package simon.trebis.ui.website

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import simon.trebis.R
import simon.trebis.data.DatabaseManager
import java.util.*

class WebsiteView(root: View, val context: Context, val databaseManager: DatabaseManager) {

    private val expandToggle: ConstraintLayout = root.findViewById(R.id.website_expand_toggle)
    private val expandToggleButton: ImageView = root.findViewById(R.id.website_expand_toggle_button)
    private val expandToggleText: TextView = root.findViewById(R.id.website_expand_toggle_text)
    private val calendar: CompactCalendarView = root.findViewById(R.id.website_calendar)
    private val missingImageText: TextView = root.findViewById(R.id.website_missing_image_text)
    private val image: ImageView = root.findViewById(R.id.website_image)

    var viewModel: WebsiteViewModel? = null

    init {
        expandToggle.setOnClickListener {
            viewModel?.toggleExpanded()
            refreshCalendarExpansion()
        }

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        val ev1 = Event(Color.GREEN, 1433701251000L, "Some extra data that I want to store.")
        calendar.addEvent(ev1)

//        calendar.setListener(onCalendarDateChanged())
    }

    private fun onCalendarDateChanged(): (CalendarView, Int, Int, Int) -> Unit {
        return { _, year, month, dayOfMonth ->
            viewModel?.selectedDay = GregorianCalendar(year + 1900, month, dayOfMonth).time
        }
    }

    private fun refreshCalendarExpansion() {
        if (viewModel != null && viewModel!!.expanded) {
            setCalendarExpanded()
        } else {
            setCalendarCollapsed()
        }
    }

    private fun setCalendarCollapsed() {
        ContextCompat.getDrawable(context, R.drawable.ic_expand_more_black_24dp).let {
            expandToggleButton.setImageDrawable(it)
        }
        expandToggleText.text = context.resources.getString(R.string.expand)
        calendar.hideCalendar()
    }

    private fun setCalendarExpanded() {
        ContextCompat.getDrawable(context, R.drawable.ic_expand_less_black_24dp).let {
            expandToggleButton.setImageDrawable(it)
        }
        expandToggleText.text = context.resources.getString(R.string.collapse)
        calendar.showCalendar()
    }

    fun refresh() {
        refreshCalendarExpansion()
        calendar.setCurrentDate(viewModel?.selectedDay)

        viewModel?.entries?.let { entries ->
            if (entries.isNotEmpty()) {
                entries.last().snapshot.let {
                    val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                    image.setImageBitmap(bitmap)
                }
            }
        }
    }

}
