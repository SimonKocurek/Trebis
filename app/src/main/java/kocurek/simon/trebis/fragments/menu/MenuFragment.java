package kocurek.simon.trebis.fragments.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.google.android.flexbox.FlexboxLayout;

import kocurek.simon.trebis.MainActivity;
import kocurek.simon.trebis.R;

public class MenuFragment extends Fragment implements View.OnClickListener {

    private OnMenuInteractionListener interactionListener;

    private FlexboxLayout content;

    private AddFloatingActionButton fab;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        content = view.findViewById(R.id.menu_fragment_content);
//        content.addView();

        fab = view.findViewById(R.id.menu_fragment_add_fab);
        fab.setOnClickListener(this);

        return view;
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_fragment_add_fab:
                interactionListener.goToAddLayout();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
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

}
