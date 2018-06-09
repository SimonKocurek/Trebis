package simon.trebis.ui.preferences

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import simon.trebis.R

class PreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

}
