package com.b304.wang.saflightsample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.entity.Light;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnitLightListAdapter2 extends BaseAdapter {
    private List<Light> mLightList;

    public UnitLightListAdapter2(List<Light> list) {
        mLightList = list;
    }

    @Override
    public int getCount() {
        return mLightList.size();
    }

    @Override
    public Object getItem(int position) {
        return mLightList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_unit_lightlist2, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        final Light light=mLightList.get(position);
        viewHolder.mNum.setText(light.getNum()+"");
        viewHolder.mTime.setText(light.getDelayTime()+"ms");
        if(light.isFeint()) {
            viewHolder.mFeintSelected.setBackgroundResource(R.mipmap.select);
        }else{
            viewHolder.mFeintSelected.setBackgroundResource(R.mipmap.unselect);
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.num)
        TextView mNum;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.feint_selected)
        Button mFeintSelected;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
