package com.b304.wang.saflightsample.test.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.entity.DbLight;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * PowerAdapter
 *
 * @author Skx
 * @date 2018/11/27
 */
public class PowerAdapter extends RecyclerView.Adapter<PowerAdapter.ViewHolder> {
    private List<DbLight> mDbLights;
    private Context mContext;

    public PowerAdapter(Context context, List<DbLight> dbLights) {
        mDbLights = dbLights;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_power, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DbLight dbLight = mDbLights.get(i);
        Integer power = Integer.valueOf(dbLight.getPower(), 16);
        if (!dbLight.getName().equals("00")) {
            viewHolder.mName.setText(dbLight.getName());
        } else {
            viewHolder.mName.setText(i + 1 + "");
        }
        switch (power - 49) {
            case -49:
                viewHolder.mPower.setImageResource(R.mipmap.power0);
                break;
            case 0:
                viewHolder.mPower.setImageResource(R.mipmap.power8);
                break;
            case 1:
                viewHolder.mPower.setImageResource(R.mipmap.power15);
                break;
            case 2:
                viewHolder.mPower.setImageResource(R.mipmap.power25);
                break;
            case 3:
                viewHolder.mPower.setImageResource(R.mipmap.power35);
                break;
            case 4:
                viewHolder.mPower.setImageResource(R.mipmap.power45);
                break;
            case 5:
                viewHolder.mPower.setImageResource(R.mipmap.power55);
                break;
            case 6:
                viewHolder.mPower.setImageResource(R.mipmap.power65);
                break;
            case 7:
                viewHolder.mPower.setImageResource(R.mipmap.power75);
                break;
            case 8:
                viewHolder.mPower.setImageResource(R.mipmap.power85);
                break;
            case 9:
                viewHolder.mPower.setImageResource(R.mipmap.power95);
                break;
            case 10:
                viewHolder.mPower.setImageResource(R.mipmap.power100);
                break;


        }
        viewHolder.mAddress.setText(dbLight.getAddress());
    }

    @Override
    public int getItemCount() {
        return mDbLights.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.power)
        ImageView mPower;
        @BindView(R.id.address)
        TextView mAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
