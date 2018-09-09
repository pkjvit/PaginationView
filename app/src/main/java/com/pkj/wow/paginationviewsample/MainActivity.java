package com.pkj.wow.paginationviewsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pkj.wow.paginationview.PaginationView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PaginationView mPaginationView;
    private String[] mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mPaginationView = findViewById(R.id.pagination_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        int size = 10505;
        int pageSize = 50;
        mDataset = new String[size];
        for (int i=0 ; i<size ; i++) {
            mDataset[i] = "Data "+(i+1);
        }

        mAdapter = new MyAdapter(getDataset(0, pageSize));
        mRecyclerView.setAdapter(mAdapter);

        mPaginationView.setPager(size);
        mPaginationView.setDefaultPageSize(PaginationView.PAGE_SIZE_20);
        mPaginationView.setOnPagerUpdate(new PaginationView.OnPagerUpdate() {
            @Override
            public void onUpdate(int pageNumber, int pageSize) {
                mAdapter.setDataset(getDataset(pageNumber, pageSize));
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private String[] getDataset(int pageNumber, int pageSize){
        String[] strings = new String[pageSize];
        int limit = pageNumber*pageSize + pageSize;
        if(limit>mDataset.length){
            limit = mDataset.length;
        }
        for(int i=pageNumber*pageSize,j=0 ; i<limit ; i++,j++){
            strings[j] = mDataset[i];
        }
        return strings;
    }
}
