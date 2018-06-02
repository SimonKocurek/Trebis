package simon.trebis.ui.layout

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import simon.trebis.R


class LayoutFragment : Fragment() {

    companion object {
        fun newInstance() = LayoutFragment()
    }

    private lateinit var viewModel: LayoutViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LayoutViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
