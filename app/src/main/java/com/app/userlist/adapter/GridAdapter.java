package com.app.userlist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.userlist.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter {
    private List<String> list = new ArrayList<>();
    private Context mContext;
    private GridAdapter gridAdapter;

    /***
     * @param list = list of product list
     * @param mContext = activity context
     */
    public GridAdapter(Context mContext, List<String> list) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_child_list, parent, false);

        vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public int getItemCount() {
        return getLists().size();
    }


    private class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;

        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final String url = getLists().get(position);
        Glide.with(mContext).load(url)
                .asBitmap()
                .placeholder(R.drawable.ic_launcher_background)
                .dontAnimate().into(((GridAdapter.ViewHolder) holder).image);
    }

    public List<String> getLists() {
        return this.list;
    }

    public void setLists(List<String> keywordLists) {
        this.list = keywordLists;
    }
}
