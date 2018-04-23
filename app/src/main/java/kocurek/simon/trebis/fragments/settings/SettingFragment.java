package kocurek.simon.trebis.fragments.settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

public class SettingFragment extends PreferenceFragmentCompat {

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey);
    }

}
