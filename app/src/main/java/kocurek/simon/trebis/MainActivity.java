package kocurek.simon.trebis;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import kocurek.simon.trebis.fragments.FragmentInteractionListener;
import kocurek.simon.trebis.fragments.menu.MenuFragment;
import kocurek.simon.trebis.fragments.settings.SettingFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentSwitcher fragmentSwitcher;

    private FragmentInteractionHandler interactionHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpDependencies();
        setUpMenu();
        setUpToolbar();
    }

    private void setUpDependencies() {
        this.fragmentSwitcher = new FragmentSwitcher(getSupportFragmentManager());
        this.interactionHandler = new FragmentInteractionHandler();
    }

    private void setUpMenu() {
        fragmentSwitcher.add(new MenuFragment());
        setContentView(R.layout.main_activity);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            fragmentSwitcher.replace(new SettingFragment());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }

    public FragmentInteractionListener getInteractionHandler() {
        return interactionHandler;
    }

}
