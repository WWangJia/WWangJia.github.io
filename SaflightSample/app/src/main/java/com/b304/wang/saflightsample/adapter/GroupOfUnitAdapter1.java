package com.b304.wang.saflightsample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.entity.GroupOfUnit;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupOfUnitAdapter1 extends BaseAdapter {
    private List<GroupOfUnit> mlist;

    public GroupOfUnitAdapter1(List<GroupOfUnit> list) {
        mlist = list;
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
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.item_group_of_unit1, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mGroupName.setText(mlist.get(position).getGroupName());
        viewHolder.mUnitTimes.setText(mlist.get(position).getUnit_num() + "");
        viewHolder.mLightTimes.setText(mlist.get(position).getLight_num() + "");
        viewHolder.mDate.setText(mlist.get(position).getDate());
        viewHolder.mReMark.setText(mlist.get(position).getRemark());
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.group_name)
        TextView mGroupName;
        @BindView(R.id.unit_times)
        TextView mUnitTimes;
        @BindView(R.id.light_times)
        TextView mLightTimes;
        @BindView(R.id.date)
        TextView mDate;
        @BindView(R.id.reMark)
        TextView mReMark;
        @BindView(R.id.bg_color)
        LinearLayout mBgColor;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
