package kocurek.simon.trebis.fragments;

import kocurek.simon.trebis.fragments.add.layout.OnAddLayoutInteractionListener;
import kocurek.simon.trebis.fragments.layout.OnLayoutInteractionListener;
import kocurek.simon.trebis.fragments.login.OnLoginInteractionListener;
import kocurek.simon.trebis.fragments.menu.OnMenuInteractionListener;
import kocurek.simon.trebis.fragments.settings.OnSettingInteractionListener;
import kocurek.simon.trebis.fragments.share.OnShareInteractionListener;

public interface FragmentInteractionListener extends
        OnAddLayoutInteractionListener,
        OnLayoutInteractionListener,
        OnLoginInteractionListener,
        OnMenuInteractionListener,
        OnSettingInteractionListener,
        OnShareInteractionListener {
}
