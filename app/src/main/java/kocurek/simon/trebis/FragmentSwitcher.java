package kocurek.simon.trebis;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentSwitcher {

    private final FragmentManager fragmentManager;

    public FragmentSwitcher(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void add(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.fragment, fragment);
        finishTransaction(transaction);
    }

    public void replace(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragment, fragment);
        finishTransaction(transaction);
    }

    private void finishTransaction(FragmentTransaction transaction) {
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
