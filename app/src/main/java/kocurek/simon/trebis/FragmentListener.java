package kocurek.simon.trebis;

import kocurek.simon.trebis.fragments.AddLayoutFragment;
import kocurek.simon.trebis.fragments.LayoutFragment;
import kocurek.simon.trebis.fragments.LoginFragment;
import kocurek.simon.trebis.fragments.SettingFragment;
import kocurek.simon.trebis.fragments.ShareLayoutFragment;

interface FragmentListener extends
        SettingFragment.OnSettingsFragmentInteractionListener,
        AddLayoutFragment.OnAddLayoutFragmentInteractionListener,
        ShareLayoutFragment.onFragmentInteractionListener,
        LayoutFragment.OnLayoutFragmentInteractionListener,
        LoginFragment.OnFragmentInteractionListener {
}
