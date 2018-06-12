package simon.trebis.ui.create

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import androidx.navigation.Navigation
import simon.trebis.R
import simon.trebis.data.DatabaseManager
import simon.trebis.data.entity.Website
import simon.trebis.ui.Consts.Companion.WEBSITE_ID_KEY


class CreateWebsiteFragment : Fragment() {

    companion object {
        fun newInstance() = CreateWebsiteFragment()
    }

    private lateinit var viewModel: CreateWebsiteViewModel
    private lateinit var websiteView: CreateWebsiteView
    private lateinit var databaseManager: DatabaseManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.create_website_fragment, container, false)

        databaseManager = DatabaseManager.instance(context!!)
        websiteView = CreateWebsiteView(view)
        websiteView.goBack = { goBack() }
        websiteView.update = { website: Website -> databaseManager.updateWebsite(website) }
        setHasOptionsMenu(true)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreateWebsiteViewModel::class.java)

        arguments.let {
            it?.getInt(WEBSITE_ID_KEY)?.let { id -> observeWebsite(id) }
        }
    }

    private fun observeWebsite(websiteId: Int) {
        val website = databaseManager.getWebsite(websiteId)
        website.observe(this, Observer { websiteView.updateWith(it) })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_settings -> {
                Navigation.findNavController(view!!).navigate(R.id.createWebsite_to_preference)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goBack() {
        Navigation.findNavController(view!!).popBackStack()
    }
}
