package com.b304.wang.saflightsample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.entity.Unit;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

public class UnitAdapter2 extends BaseAdapter {
    private ArrayList<Unit> mlist;
    private Interfacer mInterfacer;

    public UnitAdapter2(ArrayList<Unit> list,Interfacer interfacer) {
        mlist = list;
        mInterfacer=interfacer;
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
            view = inflater.inflate(R.layout.item_unit2, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        mlist.get(position).setName("单元" + (position + 1));
        viewHolder.mUnitName.setText( mlist.get(position).getName());
        viewHolder.mTimes.setText(mlist.get(position).getTimes() + "");
        viewHolder.mDate.setText(mlist.get(position).getDate());
        viewHolder.mRemark.setText(mlist.get(position).getRemark());
        final int position1=position;
        viewHolder.mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterfacer.addClick(mlist.get(position1));
            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.unit_name)
        TextView mUnitName;
        @BindView(R.id.add)
        Button mAdd;
        @BindView(R.id.times)
        TextView mTimes;
        @BindView(R.id.date)
        TextView mDate;
        @BindView(R.id.remark)
        TextView mRemark;
        @BindView(R.id.bg_color)
        LinearLayout mBgColor;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface Interfacer{
        void addClick(Unit unit);
    }
}
