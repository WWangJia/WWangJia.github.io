package com.b304.wang.saflightsample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.b304.wang.saflightsample.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OptionStringAdapter extends BaseAdapter {
    private List<String> mdata;

    public OptionStringAdapter(List<String> data) {
        mdata = data;
    }

    @Override
    public int getCount() {
        return mdata.size();
    }

    @Override
    public Object getItem(int position) {
        return mdata.get(position);
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
            convertView = inflater.inflate(R.layout.item_option_string, null);
            mViewHolder=new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder= (ViewHolder) convertView.getTag();
        }
        mViewHolder.mListItem.setText(mdata.get(position));

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
