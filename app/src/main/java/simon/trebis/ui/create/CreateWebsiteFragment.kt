package simon.trebis.ui.create

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import androidx.navigation.Navigation
import simon.trebis.R


class CreateWebsiteFragment : Fragment() {

    companion object {
        fun newInstance() = CreateWebsiteFragment()
    }

    private lateinit var viewModel: CreateWebsiteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.create_website_fragment, container, false)

        setHasOptionsMenu(true)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreateWebsiteViewModel::class.java)

        arguments.let {
            it?.getInt(WEBSITE_ID_KEY)?.let { id -> observeEntries(id) }
        }
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

}
