package com.b304.wang.saflightsample.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.b304.wang.saflightsample.App.SaflightSampleApplication;
import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.dialog.CusProgressDialog;
import com.b304.wang.saflightsample.test.activity.TestActivity;
import com.b304.wang.saflightsample.utils.SafLightReceiver;
import com.b304.wang.saflightsample.utils.ToastUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends BaseActivity {

    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.img1)
    ImageView mImg1;
    @BindView(R.id.FunGames)
    LinearLayout mFunGames;
    @BindView(R.id.img2)
    ImageView mImg2;
    @BindView(R.id.PEquality)
    LinearLayout mPEquality;
    @BindView(R.id.img3)
    ImageView mImg3;
    @BindView(R.id.motorSkill)
    LinearLayout mMotorSkill;
    @BindView(R.id.img4)
    ImageView mImg4;
    @BindView(R.id.sportsmanship)
    LinearLayout mSportsmanship;
    @BindView(R.id.img5)
    ImageView mImg5;
    @BindView(R.id.GenSportsCourses)
    LinearLayout mGenSportsCourses;
    @BindView(R.id.img7)
    ImageView mImg7;
    @BindView(R.id.MidtermExamPE)
    LinearLayout mMidtermExamPE;
    @BindView(R.id.img6)
    ImageView mImg6;
    @BindView(R.id.StudyEvaluation)
    LinearLayout mStudyEvaluation;
    @BindView(R.id.viewPage)
    FrameLayout mViewPage;
    @BindView(R.id.light_num)
    TextView mLightNum;


    private ExecutorService mExecutorService;
    private Context mContext;
    private Realm mRealm;
    private  CusProgressDialog mCusProgressDialog;
    private SafLightReceiver mSafLightReceiver;
    private Intent mintent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SaflightSampleApplication.getInstance().addActivityOrder(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        mExecutorService = new ScheduledThreadPoolExecutor(2);
        mRealm = Realm.getDefaultInstance();
        mCusProgressDialog = new CusProgressDialog(mContext);
        //注册广播
        mSafLightReceiver = new SafLightReceiver(mRealm);
        mSafLightReceiver.usbRegisterReceiver(mContext);
    }

    @OnClick({R.id.FunGames,R.id.PEquality,R.id.motorSkill,R.id.sportsmanship})
    public void onViewClick(View view){
        switch(view.getId()){
            case R.id.FunGames:
                ToastUtils.showToast(this,"FunGames点击事件",Toast.LENGTH_SHORT);
                mintent=new Intent(this,UnitSettingActivity.class);
                startActivity(mintent);
                break;
            case R.id.PEquality:
                ToastUtils.showToast(this,"PEquality点击事件",Toast.LENGTH_SHORT);
                mintent=new Intent(this,GroupSetActivity.class);
                startActivity(mintent);
                break;
            case R.id.motorSkill:
                ToastUtils.showToast(this,"motorSkill点击事件",Toast.LENGTH_SHORT);
                mintent=new Intent(this,GroupShowSetActivity.class);
                startActivity(mintent);
                break;
            case R.id.sportsmanship:
                ToastUtils.showToast(this,"sportsmanship点击事件",Toast.LENGTH_SHORT);
                mintent=new Intent(this,SelectTrainActivity.class);
                startActivity(mintent);
                break;
            default:
        }
    }

    /**
     * 测试页面触发方法
     *
     * @param view
     */
    public void test(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mRealm != null) {
            mRealm.close();
        }
        mSafLightReceiver.usbUnregisterReceiver(mContext);
        SaflightSampleApplication.getInstance().finish();
    }
}
