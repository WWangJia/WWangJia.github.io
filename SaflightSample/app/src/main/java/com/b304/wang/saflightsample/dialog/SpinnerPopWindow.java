package com.b304.wang.saflightsample.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.adapter.OptionStringAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wj
 */
public class SpinnerPopWindow {
    private ViewHolder mViewHolder;
    private PopupWindow mPopupWindow;
    public SpinnerPopWindow() {
    }

    public void show(Context context, View clickView,List<String> dataList, final onClickListener mOnClickListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_spinner_pop_window, null);
        mViewHolder=new ViewHolder(view);
        mPopupWindow = new PopupWindow(view, AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setAnimationStyle(R.style.Pop_anim_style_down);
        // 设置触摸PopupWindow外面的区域时该window是否可以消失
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        // 设置PopupWindow的背景图片,否则弹出窗口菜单外部区域点击不消失
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(00000000));
        OptionStringAdapter listAdapter=new OptionStringAdapter(dataList);
        mViewHolder.mListView.setAdapter(listAdapter);
        mViewHolder.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mOnClickListener.onClick(position);
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.showAsDropDown(clickView, 0, 0);
    }

    static class ViewHolder {
        @BindView(R.id.list_view)
        ListView mListView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickListener {
        void onClick(int position);
    }

}
