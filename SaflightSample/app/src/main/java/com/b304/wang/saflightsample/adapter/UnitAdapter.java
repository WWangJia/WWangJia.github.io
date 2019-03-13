package com.b304.wang.saflightsample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.entity.Unit;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnitAdapter extends BaseAdapter {
    private ArrayList<Unit> mlist;

    public UnitAdapter(ArrayList<Unit> list) {
       mlist=list;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.item_unit, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mUnitName.setText("单元"+(position+1));
        viewHolder.mTimes.setText(mlist.get(position).getTimes()+"");
        viewHolder.mDate.setText(mlist.get(position).getDate());
       return view;
    }

    static class ViewHolder {
        @BindView(R.id.unit_name)
        TextView mUnitName;
        @BindView(R.id.times)
        TextView mTimes;
        @BindView(R.id.date)
        TextView mDate;
        @BindView(R.id.bg_color)
        LinearLayout mBgColor;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
