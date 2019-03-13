package com.b304.wang.saflightsample.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.b304.wang.saflightsample.R;


/**
 * Created by zq on 16/4/12.
 */
public class CusProgressDialog extends Dialog {
    private ImageView mLoadingImg;

    private TextView mMessageTV;

    public CusProgressDialog(Context context) {
        super(context, R.style.transparent_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process_dialog);
        mLoadingImg = findViewById(R.id.loadingimg);
        mMessageTV = findViewById(R.id.messagetv);
        setCancelable(false);
    }

    /**
     * 当没有消息时只展示菊花
     *
     * @param message
     */
    public void showMessage(String message) {
        super.show();
        try {

            if (!TextUtils.isEmpty(message)) {
                mMessageTV.setText(message);
                mMessageTV.setVisibility(View.VISIBLE);
            } else {
                mMessageTV.setText("");
                mMessageTV.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        mLoadingImg.startAnimation(animation);
    }
}
