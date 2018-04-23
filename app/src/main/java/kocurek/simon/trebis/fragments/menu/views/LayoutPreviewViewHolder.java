package kocurek.simon.trebis.fragments.menu.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kocurek.simon.trebis.R;
import kocurek.simon.trebis.storage.entity.Layout;

public class LayoutPreviewViewHolder extends RecyclerView.ViewHolder {

    private final TextView name;
    private final ImageView image;
    private final LayoutPreviewListener listener;

    LayoutPreviewViewHolder(View itemView, LayoutPreviewListener listener) {
        super(itemView);

        this.name = itemView.findViewById(R.id.layout_preview_layout_name);
        this.image = itemView.findViewById(R.id.layout_main_image);
        this.listener = listener;
    }

    public void bindTo(Layout layout) {

//    public void onLayoutEdgeClick(final View view) {
//        PopupMenu popup = new PopupMenu(MainActivity.this, view);
//        //Inflating the Popup using xml file
//        popup.getMenuInflater().inflate(R.menu.layout_edge_menu, popup.getMenu());
//
//        //registering popup with OnMenuItemClickListener
//        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.one:
//                        createToast("Deleting");
//                        break;
//                    case R.id.two:
//                        goToShareLayout(view);
//                        break;
//                    case R.id.three:
//                        goToEditLayout(view);
//                        break;
//                }
//
//                return true;
//            }
//        });
//
//        popup.show(); //showing popup menu
//    }

    }

}
