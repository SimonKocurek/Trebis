package kocurek.simon.trebis.fragments.add.layout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kocurek.simon.trebis.R;
import kocurek.simon.trebis.main.MainActivity;

public class AddLayoutFragment extends Fragment {

    private OnAddLayoutInteractionListener interactionListener;

    public AddLayoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_layout, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        MainActivity activity = (MainActivity) context;
        interactionListener = activity.getInteractionHandler();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

}
