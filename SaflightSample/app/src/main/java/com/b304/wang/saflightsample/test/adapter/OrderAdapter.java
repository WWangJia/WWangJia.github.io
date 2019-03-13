package com.b304.wang.saflightsample.test.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.b304.wang.saflightsample.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * OrderAdapter
 *
 * @author Skx
 * @date 2018/11/27
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mStrings;

    public OrderAdapter(Context context, List<String> orderStrList) {
        mContext = context;
        mStrings = orderStrList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String s = mStrings.get((mStrings.size()-1)-i);
        viewHolder.mOrder.setText(s);

    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order)
        TextView mOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
