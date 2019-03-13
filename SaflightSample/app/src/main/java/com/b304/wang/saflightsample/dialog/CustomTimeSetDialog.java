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
import com.b304.wang.saflightsample.entity.Unit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wj
 * @date 20190203
 */
public class CustomTimeSetDialog extends Dialog {
    private Context mContext;
    private Unit mUnit;
    private ViewHolder mViewHolder;
    private DialogOperation mDialogOperation;

    public CustomTimeSetDialog(Context context, Unit unit, DialogOperation dialogOperation) {
        super(context, R.style.Dialog);
        mContext = context;
        mUnit = unit;
        mDialogOperation = dialogOperation;
        showDialog();
    }

    public void showDialog()
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_custom_times_set_dialog, null);
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
                    mUnit.setDelayTime(Integer.parseInt(str));
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
