package simon.trebis.ui.create

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import simon.trebis.MainActivity
import simon.trebis.R
import simon.trebis.data.DatabaseManager
import simon.trebis.data.entity.Website
import simon.trebis.ui.Consts.Companion.NO_ID
import simon.trebis.ui.Consts.Companion.WEBSITE_ID_KEY


class CreateWebsiteFragment : Fragment() {

    companion object {
        fun newInstance() = CreateWebsiteFragment()
    }

    private lateinit var viewModel: CreateWebsiteViewModel
    private lateinit var websiteView: CreateWebsiteView
    private lateinit var databaseManager: DatabaseManager
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.create_website_fragment, container, false)

        databaseManager = DatabaseManager.instance(context!!)
        websiteView = CreateWebsiteView(view, activity as MainActivity)
        websiteView.onConfirm = { createWebsite() }
        websiteView.onUpdate = { website: Website -> databaseManager.updateWebsite(website) }
        setHasOptionsMenu(true)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CreateWebsiteViewModel::class.java)
        navController = Navigation.findNavController(view!!)

        arguments?.getInt(WEBSITE_ID_KEY)?.let { id ->
            if (id == NO_ID) {

            } else {
                observeWebsite(id)
            }
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

    private fun createWebsite() {
        launch(UI) {
            databaseManager.createWebsite().await()
            navController.popBackStack()
        }
    }

}
