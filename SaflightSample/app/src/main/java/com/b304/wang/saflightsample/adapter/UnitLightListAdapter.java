package com.b304.wang.saflightsample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.entity.Light;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;


public class UnitLightListAdapter extends BaseAdapter {
    private List<Light> mLightList;
    private Realm mRealm;
    private int mflag;  //1:单元设置界面   2：组合展示界面
    public UnitLightListAdapter(List<Light> list,int flag) {
        mLightList = list;
        mflag=flag;
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
            if(mflag==1)
                convertView = inflater.inflate(R.layout.item_unit_lightlist, null);
            else
                convertView = inflater.inflate(R.layout.item_unit_lightlist_1, null);
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
        viewHolder.mFeintSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!light.isFeint()) {
                    light.setFeint(true);
                    viewHolder.mFeintSelected.setBackgroundResource(R.mipmap.select);
                }else{
                    light.setFeint(false);
                    viewHolder.mFeintSelected.setBackgroundResource(R.mipmap.unselect);
                }
            }
        });
        return convertView;
    }
    public void clear() {
        mLightList=new ArrayList<>();
        notifyDataSetChanged();
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
