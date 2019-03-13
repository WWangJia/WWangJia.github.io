package com.b304.wang.saflightsample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.entity.Unit;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

public class UnitAdapter3 extends BaseAdapter {
    private List<Unit> mlist;
    public Interfacer mInterfacer;
    private int mflag; //1:组合设置界面  2：组合展示界面
    public UnitAdapter3(int flag,List<Unit> list,Interfacer Interfacer) {
        mlist = list;
        mflag=flag;
        mInterfacer=Interfacer;
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
            view = inflater.inflate(R.layout.item_unit3, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if(mflag==1)
          viewHolder.mUnitName.setText(mlist.get(position).getName());
        else
            viewHolder.mUnitName.setText("单元"+(position+1));
        viewHolder.mTimes.setText(mlist.get(position).getTimes() + "");
        viewHolder.mDelayTime.setText(mlist.get(position).getDelayTime()+"s");
        viewHolder.mReMark.setText(mlist.get(position).getRemark());
        viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterfacer.deleteClick(position);
            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.unit_name)
        TextView mUnitName;
        @BindView(R.id.delete)
        ImageButton mDelete;
        @BindView(R.id.times)
        TextView mTimes;
        @BindView(R.id.delay_time)
        TextView mDelayTime;
        @BindView(R.id.reMark)
        TextView mReMark;
        @BindView(R.id.bg_color)
        LinearLayout mBgColor;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface Interfacer{
        void deleteClick(int position);
    }
}
