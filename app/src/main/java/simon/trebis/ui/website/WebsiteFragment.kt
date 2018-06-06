package simon.trebis.ui.website

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import simon.trebis.R
import simon.trebis.data.DatabaseManager
import java.util.*

class WebsiteFragment : Fragment() {

    companion object {
        fun newInstance() = WebsiteFragment()
    }

    private val WEBSITE_ID_KEY = "website_id"

    private lateinit var viewModel: WebsiteViewModel
    private lateinit var databaseManager: DatabaseManager

    private lateinit var expandToggle: ConstraintLayout
    private lateinit var expandToggleButton: ImageView
    private lateinit var expandToggleText: TextView
    private lateinit var calendar: CalendarView
    private lateinit var missingImageText: TextView
    private lateinit var image: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.website_fragment, container, false)

        setInstanceVariables(view)
        expandToggle.setOnClickListener(onExpandToggleClick())
        calendar.setOnDateChangeListener(onCalendarDateChanged())
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

        arguments.let {
            it?.getInt(WEBSITE_ID_KEY)?.let { id -> observeEntries(id) }
        }

        calendar.date = viewModel.selectedDay.time
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
        calendar.visibility = View.GONE
    }

    private fun setCalendarExpanded() {
        val drawable = ContextCompat.getDrawable(context!!, R.drawable.ic_expand_less_black_24dp)
        expandToggleButton.setImageDrawable(drawable)
        expandToggleText.text = resources.getString(R.string.collapse)
        calendar.visibility = View.VISIBLE
    }

}
