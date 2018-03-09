package kocurek.simon.trebis;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import kocurek.simon.trebis.fragments.settings.SettingFragment;

class OptionsMenuHandler {

    private final MainActivity activity;

    OptionsMenuHandler(MainActivity activity) {
        this.activity = activity;
    }

    void setUp() {
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    boolean onCreate(Menu menu) {
        activity.getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    boolean onItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            FragmentSwitcher fragmentSwitcher = activity.getFragmentSwitcher();
            fragmentSwitcher.replace(new SettingFragment());
            return true;
        }

        return false;
    }

}
