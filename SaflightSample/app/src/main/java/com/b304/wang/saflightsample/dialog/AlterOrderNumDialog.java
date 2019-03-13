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

public class AlterOrderNumDialog extends Dialog {
    private ViewHolder mViewHolder;
    private SetOnDialogListener mOnDialogListener;
    private Context mContext;

    public AlterOrderNumDialog(Context context, SetOnDialogListener onDialogListener) {
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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_alter_order_num, null);
        mViewHolder = new ViewHolder(view);
        mViewHolder.mNum.setText(AppConfig.sNum + "");
        mViewHolder.mNum.setSelection(mViewHolder.mNum.getText().toString().length());
        mViewHolder.mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mViewHolder.mNum.getText().toString();
                if (!"".equals(s)) {
                    AppConfig.sNum = Long.valueOf(s);
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
        @BindView(R.id.num)
        EditText mNum;
        @BindView(R.id.sure)
        Button mSure;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
