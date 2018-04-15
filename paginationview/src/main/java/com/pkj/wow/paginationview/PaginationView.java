package com.pkj.wow.paginationview;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.TextViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


public class PaginationView extends RelativeLayout {

    private SeekBar mSeekBar;
    private TextView mPagerTV;
    private ImageButton mLeftBtn;
    private ImageButton mRightBtn;
    private int mPageCount;
    private int mTotalCount;
    private int mPageSize;
    private OnPagerUpdate mOnPagerUpdate;

    public PaginationView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public PaginationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PaginationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View v = mInflater.inflate(R.layout.layout_pagination_view, this, true);
        mSeekBar = v.findViewById(R.id.seekbar);
        mPagerTV = v.findViewById(R.id.tv_current_page);
        mLeftBtn = v.findViewById(R.id.left_arrow);
        mRightBtn = v.findViewById(R.id.right_arrow);
        this.post(new Runnable() {
            @Override
            public void run() {
//                TextViewCompat.setAutoSizeTextTypeWithDefaults(mPagerTV, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                updatePosition(mSeekBar.getProgress());
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updatePosition(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mOnPagerUpdate != null){
                    mOnPagerUpdate.onUpdate(mSeekBar.getProgress(), mPageSize);
                }
            }
        });

        mLeftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePage(false);
            }
        });

        mRightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePage(true);
            }
        });
    }

    private void setPageCount(int pageCount){
        mPageCount = pageCount;
        mSeekBar.setMax(pageCount);
    }

    public int getPageCount() {
        return mPageCount;
    }

    public int getTotalCount() {
        return mTotalCount;
    }

    public void setPager(int totalCount, int pageSize) {
        mTotalCount = totalCount;
        mPageSize = pageSize;
        setPageCount((mTotalCount%mPageSize==0) ? (mTotalCount/mPageSize)-1 : mTotalCount/mPageSize);
    }

    public void setOnPagerUpdate(OnPagerUpdate onPagerUpdate) {
        mOnPagerUpdate = onPagerUpdate;
    }

    private void updatePosition(int progress){
        mPagerTV.setText((progress+1)+"");
        Rect bounds = mSeekBar.getThumb().getBounds();
        mPagerTV.setTranslationX(mSeekBar.getLeft() + bounds.left);
        if(progress<=0){
            mLeftBtn.setEnabled(false);
        }else{
            mLeftBtn.setEnabled(true);
        }
        if(progress>=mSeekBar.getMax()){
            mRightBtn.setEnabled(false);
        }else{
            mRightBtn.setEnabled(true);
        }
    }

    private void updatePage(boolean increase){
        int currentPage = mSeekBar.getProgress();
        if(increase){
            if(currentPage<mSeekBar.getMax()){
                currentPage++;
            }
        }else{
            if(currentPage>0){
                currentPage--;
            }
        }
        mSeekBar.setProgress(currentPage);
        if(mOnPagerUpdate != null){
            mOnPagerUpdate.onUpdate(mSeekBar.getProgress(), mPageSize);
        }
    }


    public interface OnPagerUpdate{
        void onUpdate(int pageNumber, int pageSize);
    }


}
