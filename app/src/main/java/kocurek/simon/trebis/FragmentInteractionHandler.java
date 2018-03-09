package kocurek.simon.trebis;

import android.net.Uri;

import kocurek.simon.trebis.fragments.FragmentInteractionListener;
import kocurek.simon.trebis.fragments.add.layout.AddLayoutFragment;

public class FragmentInteractionHandler implements FragmentInteractionListener {

    private MainActivity mainActivity;

    FragmentInteractionHandler(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void goToAddLayout() {
        FragmentSwitcher fragmentSwitcher = mainActivity.getFragmentSwitcher();
        fragmentSwitcher.add(new AddLayoutFragment());
    }

    @Override
    public void onLoginInteraction(Uri uri) {

    }

    @Override
    public void onShareInteraction(Uri uri) {

    }

    @Override
    public void onLayoutInteraction(Uri uri) {

    }

    @Override
    public void onSettingInteraction(Uri uri) {

    }

    @Override
    public void onAddLayoutInteraction(Uri uri) {

    }

}
