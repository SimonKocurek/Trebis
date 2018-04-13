package kocurek.simon.trebis.main;

import android.app.FragmentManager;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import kocurek.simon.trebis.R;
import kocurek.simon.trebis.fragments.FragmentInteractionListener;
import kocurek.simon.trebis.fragments.menu.MenuFragment;
import kocurek.simon.trebis.storage.database.TDatabase;
import kocurek.simon.trebis.util.FragmentSwitcher;

public class MainActivity extends AppCompatActivity {

    private FragmentSwitcher fragmentSwitcher;

    private FragmentInteractionHandler interactionHandler;

    private OptionsMenuHandler optionsMenuHandler;

    private TDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpDependencies();
        setUpMainFragment();
        setUpToolbar();
    }

    private void setUpDependencies() {
        this.fragmentSwitcher = new FragmentSwitcher(getSupportFragmentManager());
        this.interactionHandler = new FragmentInteractionHandler(this);
        this.optionsMenuHandler = new OptionsMenuHandler(this);
        database = Room.databaseBuilder(getApplicationContext(), TDatabase.class, "trebis-db")
                .build();
    }

    private void setUpMainFragment() {
        fragmentSwitcher.add(new MenuFragment());
        setContentView(R.layout.main_activity);
    }

    private void setUpToolbar() {
        this.optionsMenuHandler.setUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return optionsMenuHandler.onCreate(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (optionsMenuHandler.onItemSelected(item)) {
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

    public FragmentSwitcher getFragmentSwitcher() {
        return fragmentSwitcher;
    }

    @Override
    protected void onStop() {
        super.onStop();
        database.close();
    }

}
