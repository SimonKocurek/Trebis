package kocurek.simon.trebis.fragments.menu.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kocurek.simon.trebis.R;

public class LayoutPreviewViewHolder extends RecyclerView.ViewHolder {

    private final TextView name;
    private final ImageView image;

    public LayoutPreviewViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.layout_preview_layout_name);
        image = itemView.findViewById(R.id.layout_main_image);
    }

    public void bindTo(String name) {
        this.name.setText(name);
    }

}
