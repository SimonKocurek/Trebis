package simon.trebis.ui.website

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.*
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import simon.trebis.R
import simon.trebis.data.DatabaseManager
import simon.trebis.ui.Consts.Companion.WEBSITE_ID_KEY
import java.util.*


class WebsiteFragment : Fragment() {

    companion object {
        fun newInstance() = WebsiteFragment()
    }

    private lateinit var viewModel: WebsiteViewModel
    private lateinit var databaseManager: DatabaseManager

    private lateinit var expandToggle: ConstraintLayout
    private lateinit var expandToggleButton: ImageView
    private lateinit var expandToggleText: TextView
    private lateinit var calendar: CompactCalendarView
    private lateinit var missingImageText: TextView
    private lateinit var image: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.website_fragment, container, false)

        setHasOptionsMenu(true)

        setInstanceVariables(view)
        expandToggle.setOnClickListener(onExpandToggleClick())

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        val ev1 = Event(Color.GREEN, 1433701251000L, "Some extra data that I want to store.")
        calendar.addEvent(ev1)

//        calendar.setListener(onCalendarDateChanged())
        return view
    }

    private fun setInstanceVariables(view: View) {
        expandToggle = view.findViewById(R.id.website_expand_toggle)
        expandToggleText = view.findViewById(R.id.website_expand_toggle_text)
        expandToggleButton = view.findViewById(R.id.website_expand_toggle_button)
        calendar = view.findViewById(R.id.website_calendar)
        missingImageText = view.findViewById(R.id.website_missing_image_text)
        image = view.findViewById(R.id.website_image)
        databaseManager = DatabaseManager.instance(context!!)
    }

    private fun onCalendarDateChanged(): (CalendarView, Int, Int, Int) -> Unit {
        return { _, year, month, dayOfMonth ->
            viewModel.selectedDay = GregorianCalendar(year + 1900, month, dayOfMonth).time
        }
    }

    private fun onExpandToggleClick(): (View) -> Unit {
        return {
            viewModel.toggleExpanded()
            refreshCalendarExpansion()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WebsiteViewModel::class.java)
        refreshCalendarExpansion()

        arguments?.getInt(WEBSITE_ID_KEY)?.let { id -> observeEntries(id) }

        calendar.setCurrentDate(viewModel.selectedDay)
    }

    private fun observeEntries(websiteId: Int) {
        viewModel.websiteId = websiteId
        val entries = databaseManager.getEntriesForWebsite(websiteId)
        entries.observe(this, Observer {
            if (it != null) {
                viewModel.entries = it
            }
        })
    }

    private fun refreshCalendarExpansion() {
        if (viewModel.expanded) {
            setCalendarExpanded()
        } else {
            setCalendarCollapsed()
        }
    }

    private fun setCalendarCollapsed() {
        val drawable = ContextCompat.getDrawable(context!!, R.drawable.ic_expand_more_black_24dp)
        expandToggleButton.setImageDrawable(drawable)
        expandToggleText.text = resources.getString(R.string.expand)
        calendar.hideCalendar()
    }

    private fun setCalendarExpanded() {
        val drawable = ContextCompat.getDrawable(context!!, R.drawable.ic_expand_less_black_24dp)
        expandToggleButton.setImageDrawable(drawable)
        expandToggleText.text = resources.getString(R.string.collapse)
        calendar.showCalendar()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_settings -> {
                Navigation.findNavController(view!!).navigate(R.id.website_to_preferences)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
