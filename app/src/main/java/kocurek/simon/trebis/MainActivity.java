package kocurek.simon.trebis;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import kocurek.simon.trebis.fragments.AddLayoutFragment;
import kocurek.simon.trebis.fragments.LayoutFragment;
import kocurek.simon.trebis.fragments.MenuFragment;
import kocurek.simon.trebis.fragments.SettingFragment;
import kocurek.simon.trebis.fragments.ShareLayoutFragment;

public class MainActivity extends AppCompatActivity implements FragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpMenu();
        setUpToolbar();
    }

    private void setUpMenu() {
        addFragment(new MenuFragment());
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
            goToSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.fragment, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void goToMenu() {
        // Create fragment and give it an argument specifying the article it should show
        MenuFragment newFragment = new MenuFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    public void goToAddLayout(View view) {
        // Create fragment and give it an argument specifying the article it should show
        AddLayoutFragment newFragment = new AddLayoutFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    public void goToSettings() {
        // Create fragment and give it an argument specifying the article it should show
        SettingFragment newFragment = new SettingFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    public void goToLayout(View view) {
        // Create fragment and give it an argument specifying the article it should show
        LayoutFragment newFragment = new LayoutFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStackImmediate();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    public void onLayoutEdgeClick(final View view) {
        PopupMenu popup = new PopupMenu(MainActivity.this, view);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.layout_edge_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.one:
                        createToast("Deleting");
                        break;
                    case R.id.two:
                        goToShareLayout(view);
                        break;
                    case R.id.three:
                        goToEditLayout(view);
                        break;
                }

                return true;
            }
        });

        popup.show(); //showing popup menu
    }

    public void createToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

    public void goToEditLayout(View view) {
// Create fragment and give it an argument specifying the article it should show
        AddLayoutFragment newFragment = new AddLayoutFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    public void goToShareLayout(View view) {
// Create fragment and give it an argument specifying the article it should show
        ShareLayoutFragment newFragment = new ShareLayoutFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
