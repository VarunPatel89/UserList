package com.app.userlist.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.userlist.R;
import com.app.userlist.network.responsemodel.ClsGetListResponse;
import com.app.userlist.recyclerview.SpacesItemDecoration;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ListAdapter extends RecyclerView.Adapter
{
    private List<ClsGetListResponse.DataBean.UsersBean> list = new ArrayList<>();
    private Context mContext;
    private GridAdapter gridAdapter;

    /***
     * @param list = list of product list
     * @param mContext = activity context
     */
    public ListAdapter(Context mContext, List<ClsGetListResponse.DataBean.UsersBean> list)
    {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType)
    {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_list, parent, false);

        vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public int getItemCount()
    {
        return getLists().size();
    }


    public void setLists(ArrayList<ClsGetListResponse.DataBean.UsersBean> usersBeans)
    {
        list = usersBeans;
    }


    private class ViewHolder extends RecyclerView.ViewHolder
    {
        private RecyclerView recycleViewChild;
        private TextView txtName;
        private CircleImageView thumb;

        public ViewHolder(View v)
        {
            super(v);
            recycleViewChild = (RecyclerView) v.findViewById(R.id.recycleViewChild);
            txtName = (TextView) v.findViewById(R.id.txtName);
            thumb = (CircleImageView) v.findViewById(R.id.thumb);
        }
    }


    private void setAdapter(final List<String> items, RecyclerView recyclerView)
    {
        if (items != null && items.size() > 0)
        {
            GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
            {
                @Override
                public int getSpanSize(int position)
                {
                    if (items.size() % 2 == 0)
                    {
                        return 1;
                    }
                    else if (position == 0)
                        return 2;
                    else
                        return 1;
                }
            });
            gridAdapter = new GridAdapter(mContext, items);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(gridAdapter);
            int spacingInPixels = mContext.getResources().getDimensionPixelSize(R.dimen.spacing);
            recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
//            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position)
    {
        final ClsGetListResponse.DataBean.UsersBean usersBean = getLists().get(position);
        ((ViewHolder) holder).txtName.setText(usersBean.getName());

        Glide.with(mContext).load(usersBean.getImage()).asBitmap()
                .placeholder(R.drawable.bg_gray)
                .centerCrop()
                .into(new BitmapImageViewTarget(((ViewHolder) holder).thumb)
                {
                    @Override
                    protected void setResource(Bitmap resource)
                    {
                        if (resource != null)
                        {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            (((ViewHolder) holder).thumb).setImageDrawable(circularBitmapDrawable);
                        }
                    }
                });
        setAdapter(usersBean.getItems(), ((ViewHolder) holder).recycleViewChild);

    }

    public List<ClsGetListResponse.DataBean.UsersBean> getLists()
    {
        return this.list;
    }

    public void setLists(List<ClsGetListResponse.DataBean.UsersBean> keywordLists)
    {
        this.list = keywordLists;
    }
}
