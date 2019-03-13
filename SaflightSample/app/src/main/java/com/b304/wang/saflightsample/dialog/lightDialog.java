package com.b304.wang.saflightsample.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.TextView;

import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.adapter.ListAdapter;
import com.b304.wang.saflightsample.entity.Light;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author  wj
 * @date 20190130
 */
public class lightDialog extends Dialog {

    private Context mContext;
    private Light mLight;
    private ViewHolder mViewHolder;
    private DialogOperation mDialogOperation;
    private int[] data= new int[]{0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};

    public lightDialog(Context context, Light light,DialogOperation dialogOperation) {
        super(context, R.style.Dialog);
        setCancelable(true);
        mContext = context;
        mLight=light;
        mDialogOperation=dialogOperation;
        showDialog();
    }

    public void showDialog() {
       View view = LayoutInflater.from(mContext).inflate(R.layout.item_light_dialog, null);
       mViewHolder=new ViewHolder(view);

       mViewHolder.mNum.setText(mLight.getNum()+"");
       mViewHolder.mDelayTime.setText(mLight.getDelayTime()+"ms");

        final ListAdapter mListAdapter=new ListAdapter(data);
        mViewHolder.mLightListView.setAdapter(mListAdapter);
        mViewHolder.mLightListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewHolder.mDelayTime.setText(data[position]+"ms");
            }
        });
       mViewHolder.mButton1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mViewHolder.mNum.setText(1+"");
           }
       });
        mViewHolder.mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.mNum.setText(2+"");
            }
        });
        mViewHolder.mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.mNum.setText(3+"");
            }
        });
        mViewHolder.mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.mNum.setText(4+"");
            }
        });
        mViewHolder.mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.mNum.setText(5+"");
            }
        });
        mViewHolder.mButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.mNum.setText(6+"");
            }
        });
        mViewHolder.msure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLight.setNum(Integer.parseInt(mViewHolder.mNum.getText().toString()));
                if(mViewHolder.mInputDelayTime.getText().toString().trim()==null||"".equals(mViewHolder.mInputDelayTime.getText().toString().trim())) {
                    String[] str = mViewHolder.mDelayTime.getText().toString().trim().split("ms");
                    mLight.setDelayTime(Integer.parseInt(str[0]));
                }else{
                    mLight.setDelayTime(Integer.parseInt(mViewHolder.mInputDelayTime.getText().toString().trim()));
                }
                mDialogOperation.sureClick();
            }
        });
         super.setContentView(view);
    }

    public interface DialogOperation {
        void sureClick();
    }

    static class ViewHolder {
        @BindView(R.id.button1)
        Button mButton1;
        @BindView(R.id.button2)
        Button mButton2;
        @BindView(R.id.button3)
        Button mButton3;
        @BindView(R.id.button4)
        Button mButton4;
        @BindView(R.id.button5)
        Button mButton5;
        @BindView(R.id.button6)
        Button mButton6;
        @BindView(R.id.num)
        TextView mNum;
        @BindView(R.id.delayTime)
        TextView mDelayTime;
        @BindView(R.id.light_list_view)
        ListView mLightListView;
        @BindView(R.id.input_delay_time)
        EditText mInputDelayTime;
        @BindView(R.id.sure)
        Button msure;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
