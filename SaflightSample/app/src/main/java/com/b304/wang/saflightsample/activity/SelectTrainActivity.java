package com.b304.wang.saflightsample.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.b304.wang.saflightsample.App.SaflightSampleApplication;
import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.adapter.GroupOfUnitAdapter;
import com.b304.wang.saflightsample.adapter.GroupOfUnitAdapter1;
import com.b304.wang.saflightsample.dialog.SpinnerPopWindow;
import com.b304.wang.saflightsample.entity.GroupOfUnit;
import com.b304.wang.saflightsample.utils.RealmUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static android.widget.Toast.LENGTH_SHORT;

public class SelectTrainActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.backName)
    TextView mBackName;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.train_name)
    TextView mTrainName;
    @BindView(R.id.drop_button)
    ImageButton mDropButton;
    @BindView(R.id.group_of_unit_list_view)
    ListView mGroupOfUnitListView;
    @BindView(R.id.group_list_view)
    ListView mGroupListView;

    private Realm mRealm;
    //训练形式数据
    List<String> dataList = new ArrayList<>();
    private GroupOfUnitAdapter1 mGroupOfUnitAdapter1;
    //下拉列表框
    private SpinnerPopWindow mSpinnerPopWindow;
    //单元列表
    private List<GroupOfUnit> mGroupOfUnits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_train);
        ButterKnife.bind(this);
        SaflightSampleApplication.getInstance().addActivityOrder(this);
        mRealm = Realm.getDefaultInstance();
        initData();
        initView();
        setListner();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        getData();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        //设置标题
        mTitle.setText("选择训练");
    }

    /**
     * 设置监听
     */
    private void setListner() {

    }

    @OnClick({R.id.drop_button,R.id.train_name})
    public void OnViewClick(View view){
        switch (view.getId()){
            case R.id.drop_button:
            case R.id.train_name:
                if (mSpinnerPopWindow == null)
                    mSpinnerPopWindow = new SpinnerPopWindow();
                mSpinnerPopWindow.show(SelectTrainActivity.this, mTrainName, dataList, new SpinnerPopWindow.onClickListener() {
                    @Override
                    public void onClick(int position) {
                        mTrainName.setText(dataList.get(position));
                        //Toast.makeText(GroupSetActivity.this,list.get(position),Toast.LENGTH_SHORT).show();
                        mGroupOfUnits = RealmUtils.queryGroupOfUnit(mRealm, dataList.get(position));

                        mGroupOfUnitAdapter1 = new GroupOfUnitAdapter1(mGroupOfUnits);
                        mGroupOfUnitListView.setAdapter(mGroupOfUnitAdapter1);
                    }
                });
                break;
        }
    }

    public void getData() {
        //步骤1：创建一个SharedPreferences接口对象
        SharedPreferences read = getSharedPreferences("GroupSet", MODE_PRIVATE);
        //步骤2：获取文件中的值
        String value = read.getString("trainStyle", "");
        String[] str = value.split(",");
        for (int i = 0; i < str.length; i++) {
            dataList.add(str[i]);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SaflightSampleApplication.getInstance().finish();
    }

}
