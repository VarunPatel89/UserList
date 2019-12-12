package com.app.userlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	private RecyclerView recycleViewList;
    private SwipeRefreshLayout swipeLayout = null;
    private TextView txtTitle;
	public RelativeLayout relLoadMore;
	private int offsetBrands = 1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                
            }
        });

		relLoadMore = (RelativeLayout) findViewById(R.id.relLoadMore);
		removeFooterView();
		callGetListWS();
    }
	
	public static void setFooterVisibility(RelativeLayout relLoadMore, int visibility){
		if (relLoadMore != null)
            relLoadMore.setVisibility(visibility);
	}
	
	  public void removeFooterView()
    {
        if (relLoadMore != null)
            setFooterVisibility(relLoadMore, View.GONE);
    }
	
	private void callGetListWS(){
		
	}
}
