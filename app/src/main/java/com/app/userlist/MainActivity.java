package com.app.userlist;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.imagegrid.recyclerview.EndlessRecyclerViewScrollListener;
import com.app.userlist.adapter.ListAdapter;
import com.app.userlist.network.ApiCallBack;
import com.app.userlist.network.ApiClient;
import com.app.userlist.network.responsemodel.ClsGetListResponse;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{

    private RecyclerView recycleViewList;
    private SwipeRefreshLayout swipeLayout = null;
    private TextView txtTitle;
    public RelativeLayout relLoadMore;
    private int offsetBrands = 1;
    private LinearLayoutManager linearLayoutManager = null;

    private ClsGetListResponse clsListAPINewsResponse = null;
    ArrayList<ClsGetListResponse.DataBean.UsersBean> usersBeans = new ArrayList<>();
    private ListAdapter cultureListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycleViewList = findViewById(R.id.recycleViewList);
        swipeLayout = findViewById(R.id.swipeLayout);
        txtTitle = findViewById(R.id.txtTitle);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                swipeLayout.setRefreshing(false);
                offsetBrands = 1;
                if (usersBeans != null && usersBeans.size() > 0)
                    usersBeans.clear();
                callGetListWS();
            }
        });

        relLoadMore = (RelativeLayout) findViewById(R.id.relLoadMore);
        removeFooterView();
        callGetListWS();
    }

    public static void setFooterVisibility(RelativeLayout relLoadMore, int visibility)
    {
        if (relLoadMore != null)
            relLoadMore.setVisibility(visibility);
    }

    public void removeFooterView()
    {
        if (relLoadMore != null)
            setFooterVisibility(relLoadMore, View.GONE);
    }

    private void callGetListWS()
    {
        if (offsetBrands > 1)
        {
            setFooterVisibility(relLoadMore, View.VISIBLE);
        }
        else
        {
            Utility.showProgress(this, false);
        }

        Call<ClsGetListResponse> call = ApiClient.getRestApiMethods().getList(offsetBrands, BuildConfig.PAGE_LIMIT);
        call.enqueue(new ApiCallBack<ClsGetListResponse>()
        {
            @Override
            public void onRefresh()
            {

            }

            @Override
            public void onResponse(Call<ClsGetListResponse> call, Response<ClsGetListResponse> response)
            {
                clsListAPINewsResponse = response.body();
                Utility.cancelProgress();
                removeFooterView();
                setAdapter();
            }

            @Override
            public void onFailure(Call<ClsGetListResponse> call, Throwable t)
            {
                Utility.cancelProgress();
                removeFooterView();
            }
        });

    }

    private void setAdapter()
    {
        if (clsListAPINewsResponse != null && clsListAPINewsResponse.getData().getUsers() != null &&
                clsListAPINewsResponse.getData().getUsers().size() > 0)
        {
            offsetBrands++;
            usersBeans.addAll(clsListAPINewsResponse.getData().getUsers());
            if (cultureListAdapter == null)
            {
                recycleViewList.setLayoutManager(getLinearLayoutManager());
                cultureListAdapter = new ListAdapter(this, usersBeans);
                recycleViewList.setAdapter(cultureListAdapter);
                recycleViewList.addItemDecoration(new DividerItemDecoration(recycleViewList.getContext(), DividerItemDecoration.VERTICAL));
                recycleViewList.addOnScrollListener(endlessRecyclerViewScrollListener);
            }
            else
            {
                cultureListAdapter.setLists(usersBeans);
                cultureListAdapter.notifyDataSetChanged();
            }
        }
        else
        {
            offsetBrands = 0;
        }
    }

    private LinearLayoutManager getLinearLayoutManager()
    {
        if (linearLayoutManager == null)
            linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        return linearLayoutManager;
    }

    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(getLinearLayoutManager(), this)
    {
        @Override
        public void onLoadMore(int page, int totalItemsCount)
        {
            Utility.log("Total item count : " + totalItemsCount);
            if (offsetBrands == 0)
            {
                removeFooterView();
            }
            else
            {
                if (Utility.isNetworkAvailable(MainActivity.this))
                    callGetListWS();
                else
                    Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.internet_connection_not_available), Toast.LENGTH_SHORT).show();
            }
        }
    };
}
