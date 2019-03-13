package com.b304.wang.saflightsample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.entity.GroupOfUnit;
import com.b304.wang.saflightsample.entity.Unit;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupOfUnitAdapter extends BaseAdapter {
    private List<GroupOfUnit> mlist;
    private DeleteClick mDeleteClick;
    public GroupOfUnitAdapter(List<GroupOfUnit> list,DeleteClick deleteClick) {
        mlist = list;
        mDeleteClick=deleteClick;
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
            view = inflater.inflate(R.layout.item_group_of_unit, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mGroupName.setText(mlist.get(position).getGroupName());
        viewHolder.mUnitTimes.setText(mlist.get(position).getUnit_num()+"");
        viewHolder.mLightTimes.setText(mlist.get(position).getLight_num()+"");
        viewHolder.mDate.setText(mlist.get(position).getDate());
        viewHolder.mReMark.setText(mlist.get(position).getRemark());
        viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteClick.delete(position);
            }
        });
        return view;
    }
    public interface DeleteClick{ void delete(int position);
    }

    static class ViewHolder {
        @BindView(R.id.group_name)
        TextView mGroupName;
        @BindView(R.id.delete)
        ImageButton mDelete;
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
