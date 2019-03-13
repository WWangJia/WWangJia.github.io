package com.b304.wang.saflightsample.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.utils.AppConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lenovo
 * @date 2018/11/23
 */

public class AlterSleepDialog extends Dialog {
    private ViewHolder mViewHolder;
    private SetOnDialogListener mOnDialogListener;
    private Context mContext;

    public AlterSleepDialog(Context context, SetOnDialogListener onDialogListener) {
        super(context, R.style.Dialog);
        setCancelable(true);
        mContext = context;
        mOnDialogListener = onDialogListener;
        createDialog();
    }

    /**
     * 创建
     */
    private void createDialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_alter_sleep, null);
        mViewHolder = new ViewHolder(view);
        mViewHolder.mSleep.setText(AppConfig.sSleep + "");
        mViewHolder.mSleep.setSelection(mViewHolder.mSleep.getText().toString().length());
        mViewHolder.mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mViewHolder.mSleep.getText().toString();
                if (!"".equals(s)) {
                    AppConfig.sSleep = Long.valueOf(s);
                    mOnDialogListener.sureClick();
                }
            }
        });
        super.setContentView(view);
    }


    public interface SetOnDialogListener {
        /**
         * 确定
         */
        void sureClick();
    }


    static class ViewHolder {
        @BindView(R.id.sleep)
        EditText mSleep;
        @BindView(R.id.sure)
        Button mSure;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
