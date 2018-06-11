package simon.trebis.ui.create

import android.view.View
import android.widget.EditText
import simon.trebis.R
import simon.trebis.data.entity.Website
import java.util.*

class CreateWebsiteView(root: View) {

//    private val dateFormat = DateFormat.getDateFormat(root.context.applicationContext)

    private val websiteName = root.findViewById<EditText>(R.id.website_edit_name)
    private val websiteUrl = root.findViewById<EditText>(R.id.website_edit_name)
    private val creationDate = root.findViewById<EditText>(R.id.website_edit_name)
    private val webView = root.findViewById<EditText>(R.id.website_edit_name)
    private val confirm = root.findViewById<EditText>(R.id.website_edit_name)

    private var website: Website? = null

    init {
//        confirm.setOnClickListener()
    }

    fun updateWith(it: Website?) {
        if (website != null) {
            website = it

            updateName(website!!.name)
            updateUrl(website!!.url)
            updateCreationDate(website!!.date)
            updateWebView(website!!.url)
        }
    }

    private fun updateName(name: String) {
        websiteName.setText(name)
        websiteName.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
//                viewDataChanged()
            }
        }
    }

    private fun updateUrl(url: String) {
        websiteUrl.setText(url)
        websiteUrl.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
//                viewDataChanged()
            }
        }
    }

    private fun updateCreationDate(date: Date) {
//        creationDate.setText(dateFormat.format(date))
        creationDate.setText(date.toString())
    }

    private fun updateWebView(url: String?) {
//        webView.
    }

}
