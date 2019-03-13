package com.b304.wang.saflightsample.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.entity.Light;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wj
 * @date 20190203
 */
public class FientTimeSetDialog extends Dialog {
    private Context mContext;
    private Light mLight;
    private ViewHolder mViewHolder;
    private DialogOperation mDialogOperation;

    public FientTimeSetDialog(Context context, Light light, DialogOperation dialogOperation) {
        super(context, R.style.Dialog);
        mContext = context;
        mLight = light;
        mDialogOperation = dialogOperation;
        showDialog();
    }

    public void showDialog()
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fient_times_set_dialog, null);
        mViewHolder=new ViewHolder(view);
        mViewHolder.mQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogOperation.cancelClick();
            }
        });
        mViewHolder.mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=mViewHolder.mInputFientTime.getText().toString().trim();
                if(str!=null&&str.length()!=0){
                    mLight.setDelayTime(Integer.parseInt(str));
                    mDialogOperation.sureClick();
                }else{
                    Toast.makeText(mContext,"未作修改",Toast.LENGTH_SHORT).show();
                }
            }
        });
        super.setContentView(view);
    }

    public interface DialogOperation {
        void sureClick();

        void cancelClick();
    }

    static class ViewHolder {
        @BindView(R.id.input_fient_time)
        EditText mInputFientTime;
        @BindView(R.id.quit)
        Button mQuit;
        @BindView(R.id.save)
        Button mSave;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
