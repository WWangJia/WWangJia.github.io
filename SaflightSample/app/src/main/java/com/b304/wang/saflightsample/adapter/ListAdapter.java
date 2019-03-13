package com.b304.wang.saflightsample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.b304.wang.saflightsample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends BaseAdapter {
    private int[] mdata;

    public ListAdapter(int[] data) {
        mdata = data;
    }

    @Override
    public int getCount() {
        return mdata.length;
    }

    @Override
    public Object getItem(int position) {
        return mdata[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_list_view, null);
            mViewHolder=new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder= (ViewHolder) convertView.getTag();
        }
        mViewHolder.mListItem.setText(mdata[position]+"ms");

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.list_item)
        TextView mListItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
