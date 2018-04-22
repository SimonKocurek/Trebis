package kocurek.simon.trebis.fragments.menu.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kocurek.simon.trebis.R;

public class LayoutPreviewAdapter extends RecyclerView.Adapter<LayoutPreviewViewHolder> {

    private String textArr[];

    public LayoutPreviewAdapter(String[] textArr) {
        this.textArr = textArr;
    }

    @NonNull
    @Override
    public LayoutPreviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutPreview = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layout_preview, parent, false);

        return new LayoutPreviewViewHolder(layoutPreview);
    }

    @Override
    public void onBindViewHolder(@NonNull LayoutPreviewViewHolder holder, int position) {
        holder.bindTo(textArr[position]);
    }

    @Override
    public int getItemCount() {
        return this.textArr.length;
    }

}
