package kocurek.simon.trebis.fragments.menu.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kocurek.simon.trebis.R;
import kocurek.simon.trebis.storage.entity.Layout;

public class LayoutPreviewAdapter extends RecyclerView.Adapter<LayoutPreviewViewHolder> {

    private final List<Layout> layouts;
    private final LayoutPreviewListener listener;

    public LayoutPreviewAdapter(List<Layout> layouts, LayoutPreviewListener listener) {
        this.layouts = layouts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LayoutPreviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutPreview = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layout_preview, parent, false);

        return new LayoutPreviewViewHolder(layoutPreview, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull LayoutPreviewViewHolder holder, int position) {
        holder.bindTo(layouts.get(position));
    }

    @Override
    public int getItemCount() {
        return this.layouts.size();
    }

}
