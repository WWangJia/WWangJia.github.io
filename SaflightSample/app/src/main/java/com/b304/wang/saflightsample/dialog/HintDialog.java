package com.b304.wang.saflightsample.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.b304.wang.saflightsample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wj on 2019/01/23.
 */

public class HintDialog extends Dialog {
    private Context mContext;
    private String mHintString;
    private String mCancelStr;
    private String mSureStr;

    private DialogOperation mDialogOperation;
    private ViewHolder mViewHolder;

    public HintDialog(Context context, String hintString, String cancelStr, String sureStr, DialogOperation dialogOperation) {
        super(context, R.style.Dialog);
        setCancelable(true);
        mContext = context;
        mHintString = hintString;
        mCancelStr = cancelStr;
        mSureStr = sureStr;
        mDialogOperation = dialogOperation;
        showDialog();
    }


    private void showDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_hint, null);
        mViewHolder = new ViewHolder(view);
        mViewHolder.mHintText.setText(mHintString);
        mViewHolder.mCancel.setText(mCancelStr);
        mViewHolder.mSure.setText(mSureStr);
        mViewHolder.mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogOperation.sureClick();

            }
        });
        mViewHolder.mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogOperation.cancelClick();
            }
        });
        super.setContentView(view);
    }

    public void setCancelText(String text) {
        mViewHolder.mCancel.setText(text);
    }

    public interface DialogOperation {
        void sureClick();

        void cancelClick();
    }

    static class ViewHolder {
        @BindView(R.id.hintText)
        TextView mHintText;
        @BindView(R.id.cancel)
        TextView mCancel;
        @BindView(R.id.sure)
        TextView mSure;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
