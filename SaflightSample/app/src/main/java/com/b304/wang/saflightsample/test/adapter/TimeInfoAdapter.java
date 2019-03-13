package com.b304.wang.saflightsample.test.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.entity.TimeInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * TimeInfoAdapter
 *
 * @author
 * @date 2018/11/27
 */
public class TimeInfoAdapter extends RecyclerView.Adapter<TimeInfoAdapter.ViewHolder> {
    private Context mContext;
    private List<TimeInfo> mTimeInfoList;

    public TimeInfoAdapter(Context context, List<TimeInfo> timeInfoList) {
        mContext = context;
        mTimeInfoList = timeInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_time_info, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TimeInfo timeInfo = mTimeInfoList.get((mTimeInfoList.size() - 1) - i);
        viewHolder.mNum.setText(mTimeInfoList.size() - i + "");
        viewHolder.mTime.setText(timeInfo.getTime() + "ms");
        if ("00".equals(timeInfo.getName())) {
            viewHolder.mName.setText(timeInfo.getAddress());
        } else {
            viewHolder.mName.setText(timeInfo.getName());
        }

        viewHolder.mModel.setText(timeInfo.getReactionModel());
    }

    @Override
    public int getItemCount() {
        return mTimeInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.num)
        TextView mNum;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.model)
        TextView mModel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
