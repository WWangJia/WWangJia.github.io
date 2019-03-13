package com.b304.wang.saflightsample.test.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.activity.BaseActivity;
import com.b304.wang.saflightsample.dialog.AlterDeviceDialog;
import com.b304.wang.saflightsample.dialog.AlterOrderNumDialog;
import com.b304.wang.saflightsample.dialog.AlterSleepDialog;
import com.b304.wang.saflightsample.dialog.CusProgressDialog;
import com.b304.wang.saflightsample.entity.DbLight;
import com.b304.wang.saflightsample.entity.ShockInfo;
import com.b304.wang.saflightsample.entity.TimeInfo;
import com.b304.wang.saflightsample.order.CommandNew;
import com.b304.wang.saflightsample.order.CommandRules;
import com.b304.wang.saflightsample.order.Order;
import com.b304.wang.saflightsample.order.OrderUtils;
import com.b304.wang.saflightsample.test.adapter.LightOperateAdapter;
import com.b304.wang.saflightsample.test.adapter.OrderAdapter;
import com.b304.wang.saflightsample.test.adapter.PowerAdapter;
import com.b304.wang.saflightsample.test.adapter.ShockInfoAdapter;
import com.b304.wang.saflightsample.test.adapter.TimeInfoAdapter;
import com.b304.wang.saflightsample.utils.AppConfig;
import com.b304.wang.saflightsample.utils.RealmUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;


/**
 * @author lenovo
 * 测试页面
 */
public class TestActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.sleep)
    Button mSleep;
    @BindView(R.id.send)
    Button mSend;
    @BindView(R.id.turnOn)
    Button mTurnOn;
    @BindView(R.id.turnOff)
    Button mTurnOff;
    @BindView(R.id.lightList)
    RecyclerView mLightList;
    @BindView(R.id.selectAll)
    Button mSelectAll;
    @BindView(R.id.selectNone)
    Button mSelectNone;
    @BindView(R.id.sleepOrder)
    TextView mSleepOrder;
    @BindView(R.id.orderNum)
    TextView mOrderNum;
    @BindView(R.id.beforeOutBlinkNone)
    RadioButton mBeforeOutBlinkNone;
    @BindView(R.id.beforeOutBlinkAlways)
    RadioButton mBeforeOutBlinkAlways;
    @BindView(R.id.beforeOutBlinkSlow)
    RadioButton mBeforeOutBlinkSlow;
    @BindView(R.id.beforeOutBlinkFast)
    RadioButton mBeforeOutBlinkFast;
    @BindView(R.id.beforeOutBlink)
    RadioGroup mBeforeOutBlink;
    @BindView(R.id.beforeOutColorBlue)
    RadioButton mBeforeOutColorBlue;
    @BindView(R.id.beforeOutColorRed)
    RadioButton mBeforeOutColorRed;
    @BindView(R.id.beforeOutColorGreen)
    RadioButton mBeforeOutColorGreen;
    @BindView(R.id.beforeOutColorPurple)
    RadioButton mBeforeOutColorPurple;
    @BindView(R.id.beforeOutColor1)
    RadioGroup mBeforeOutColor1;
    @BindView(R.id.beforeOutColorCyan)
    RadioButton mBeforeOutColorCyan;
    @BindView(R.id.beforeOutColorYellow)
    RadioButton mBeforeOutColorYellow;
    @BindView(R.id.beforeOutColorWhite)
    RadioButton mBeforeOutColorWhite;
    @BindView(R.id.beforeOutColor2)
    RadioGroup mBeforeOutColor2;
    @BindView(R.id.beforeInBlinkNone)
    RadioButton mBeforeInBlinkNone;
    @BindView(R.id.beforeInBlinkAlways)
    RadioButton mBeforeInBlinkAlways;
    @BindView(R.id.beforeInBlinkSlow)
    RadioButton mBeforeInBlinkSlow;
    @BindView(R.id.beforeInBlinkFast)
    RadioButton mBeforeInBlinkFast;
    @BindView(R.id.beforeInBlink)
    RadioGroup mBeforeInBlink;
    @BindView(R.id.beforeInColorBlue)
    RadioButton mBeforeInColorBlue;
    @BindView(R.id.beforeInColorRed)
    RadioButton mBeforeInColorRed;
    @BindView(R.id.beforeInColorPurple)
    RadioButton mBeforeInColorPurple;
    @BindView(R.id.beforeInColor)
    RadioGroup mBeforeInColor;
    @BindView(R.id.infraredEmissionOn)
    RadioButton mInfraredEmissionOn;
    @BindView(R.id.infraredEmissionOff)
    RadioButton mInfraredEmissionOff;
    @BindView(R.id.infraredEmission)
    RadioGroup mInfraredEmission;
    @BindView(R.id.infraredInductionOn)
    RadioButton mInfraredInductionOn;
    @BindView(R.id.infraredInductionOff)
    RadioButton mInfraredInductionOff;
    @BindView(R.id.infraredInduction)
    RadioGroup mInfraredInduction;
    @BindView(R.id.infraredModelNormal)
    RadioButton mInfraredModelNormal;
    @BindView(R.id.infraredModelContend)
    RadioButton mInfraredModelContend;
    @BindView(R.id.infraredModel)
    RadioGroup mInfraredModel;
    @BindView(R.id.infraredHeightLow)
    RadioButton mInfraredHeightLow;
    @BindView(R.id.infraredHeight_5cm)
    RadioButton mInfraredHeight5cm;
    @BindView(R.id.infraredHeight_30cm)
    RadioButton mInfraredHeight30cm;
    @BindView(R.id.infraredHeightHigh)
    RadioButton mInfraredHeightHigh;
    @BindView(R.id.infraredHeight)
    RadioGroup mInfraredHeight;
    @BindView(R.id.vibrationInducedOn)
    RadioButton mVibrationInducedOn;
    @BindView(R.id.vibrationInducedOff)
    RadioButton mVibrationInducedOff;
    @BindView(R.id.vibrationInduced)
    RadioGroup mVibrationInduced;
    @BindView(R.id.vibrationIntensityL)
    RadioButton mVibrationIntensityL;
    @BindView(R.id.vibrationIntensityM)
    RadioButton mVibrationIntensityM;
    @BindView(R.id.vibrationIntensityH)
    RadioButton mVibrationIntensityH;
    @BindView(R.id.vibrationIntensity)
    RadioGroup mVibrationIntensity;
    @BindView(R.id.vibrationDetailsOn)
    RadioButton mVibrationDetailsOn;
    @BindView(R.id.vibrationDetailsOff)
    RadioButton mVibrationDetailsOff;
    @BindView(R.id.vibrationDetails)
    RadioGroup mVibrationDetails;
    @BindView(R.id.buzzerNone)
    RadioButton mBuzzerNone;
    @BindView(R.id.buzzerShort)
    RadioButton mBuzzerShort;
    @BindView(R.id.buzzer_1s)
    RadioButton mBuzzer1s;
    @BindView(R.id.buzzer_2s)
    RadioButton mBuzzer2s;
    @BindView(R.id.buzzer)
    RadioGroup mBuzzer;
    @BindView(R.id.afterOutBlinkNone)
    RadioButton mAfterOutBlinkNone;
    @BindView(R.id.afterOutBlinkAlways)
    RadioButton mAfterOutBlinkAlways;
    @BindView(R.id.afterOutBlinkSlow)
    RadioButton mAfterOutBlinkSlow;
    @BindView(R.id.afterOutBlinkFast)
    RadioButton mAfterOutBlinkFast;
    @BindView(R.id.afterOutBlink)
    RadioGroup mAfterOutBlink;
    @BindView(R.id.afterOutColorBlue)
    RadioButton mAfterOutColorBlue;
    @BindView(R.id.afterOutColorRed)
    RadioButton mAfterOutColorRed;
    @BindView(R.id.afterOutColorGreen)
    RadioButton mAfterOutColorGreen;
    @BindView(R.id.afterOutColorPurple)
    RadioButton mAfterOutColorPurple;
    @BindView(R.id.afterOutColor1)
    RadioGroup mAfterOutColor1;
    @BindView(R.id.afterOutColorCyan)
    RadioButton mAfterOutColorCyan;
    @BindView(R.id.afterOutColorYellow)
    RadioButton mAfterOutColorYellow;
    @BindView(R.id.afterOutColorWhite)
    RadioButton mAfterOutColorWhite;
    @BindView(R.id.afterOutColor2)
    RadioGroup mAfterOutColor2;
    @BindView(R.id.afterInBlinkNone)
    RadioButton mAfterInBlinkNone;
    @BindView(R.id.afterInBlinkAlways)
    RadioButton mAfterInBlinkAlways;
    @BindView(R.id.afterInBlinkSlow)
    RadioButton mAfterInBlinkSlow;
    @BindView(R.id.afterInBlinkFast)
    RadioButton mAfterInBlinkFast;
    @BindView(R.id.afterInBlink)
    RadioGroup mAfterInBlink;
    @BindView(R.id.afterInColorBlue)
    RadioButton mAfterInColorBlue;
    @BindView(R.id.afterInColorRed)
    RadioButton mAfterInColorRed;
    @BindView(R.id.afterInColorPurple)
    RadioButton mAfterInColorPurple;
    @BindView(R.id.afterInColor)
    RadioGroup mAfterInColor;
    @BindView(R.id.afterBuzzerNone)
    RadioButton mAfterBuzzerNone;
    @BindView(R.id.afterBuzzerShort)
    RadioButton mAfterBuzzerShort;
    @BindView(R.id.afterBuzzer_1s)
    RadioButton mAfterBuzzer1s;
    @BindView(R.id.afterBuzzer_2s)
    RadioButton mAfterBuzzer2s;
    @BindView(R.id.afterBuzzer)
    RadioGroup mAfterBuzzer;
    @BindView(R.id.tv_pan_id)
    TextView mTvPanId;
    @BindView(R.id.mac_address)
    TextView mMacAddress;
    @BindView(R.id.alter)
    Button mAlter;
    @BindView(R.id.deviceList)
    RecyclerView mDeviceList;
    @BindView(R.id.orderRecyclerView)
    RecyclerView mOrderRecyclerView;
    @BindView(R.id.resetCoordinator)
    Button mResetCoordinator;
    @BindView(R.id.clearData)
    Button mClearData;
    @BindView(R.id.timeInfoList)
    RecyclerView mTimeInfoList;
    @BindView(R.id.shackView)
    RecyclerView mShackView;
    @BindView(R.id.orderList)
    RecyclerView mOrderList;
    @BindView(R.id.receiveOrderList)
    RecyclerView mReceiveOrderList;
    private Context mContext;
    private PowerAdapter mPowerAdapter;
    private TimeInfoAdapter mTimeInfoAdapter;
    private ShockInfoAdapter mShockInfoAdapter;
    private OrderAdapter mAdapter;
    private AlterSleepDialog mAlterSleepDialog;
    private AlterDeviceDialog mAlterDeviceDialog;
    private AlertDialog mAlertDialog;
    private List<DbLight> mSelectLight = new ArrayList<>();
    private List<TimeInfo> mTimeInfoListData = new ArrayList<>();
    private List<ShockInfo> mShockInfoList = new ArrayList<>();
    private List<String> mOrderStrList = new ArrayList<>();
    private LightOperateAdapter mLightOperateAdapter;
    private ScheduledExecutorService mExecutorService;
    private AlterOrderNumDialog mAlterOrderNumDialog;
    private CommandNew mCommandNew;
    private CusProgressDialog mCusProgressDialog;


    private void showAlterDialog() {
        mAlterDeviceDialog = new AlterDeviceDialog(mContext, mRealm, new AlterDeviceDialog.SetOnDialogListener() {
            @Override
            public void onDataChangeListener() {
                mPowerAdapter.notifyDataSetChanged();
                mLightOperateAdapter.notifyDataSetChanged();
            }
        });
        mAlterDeviceDialog.show();
    }

    private Realm mRealm;
    /**
     * 广播监听
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case AppConfig.ACTION_TIME_INFO:
                    TimeInfo timeInfo = (TimeInfo) intent.getSerializableExtra("timeInfo");
                    mTimeInfoListData.add(timeInfo);
                    mTimeInfoAdapter.notifyDataSetChanged();
                    break;
                case AppConfig.ACTION_SHACK_INFO:
                    ShockInfo shockInfo = (ShockInfo) intent.getSerializableExtra("shackInfo");
                    mShockInfoList.add(shockInfo);
                    mShockInfoAdapter.notifyDataSetChanged();
                    break;
                case AppConfig.ACTION_ANALYSIS_DEVICE:
                    setDevice();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        mContext = this;
        mRealm = Realm.getDefaultInstance();
        mCusProgressDialog = new CusProgressDialog(mContext);
        mCommandNew = new CommandNew();
        mExecutorService = new ScheduledThreadPoolExecutor(2);
        registerReceiver();
        initListener();
        initAdapter();
        setDevice();

    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppConfig.ACTION_TIME_INFO);
        intentFilter.addAction(AppConfig.ACTION_SHACK_INFO);
        intentFilter.addAction(AppConfig.ACTION_ANALYSIS_DEVICE);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private void setDevice() {
        if (AppConfig.sDevice != null) {
            mTvPanId.setText(AppConfig.sDevice.getChannelNumber() + AppConfig.sDevice.getPanId());
            mMacAddress.setText(getMac(AppConfig.sDevice.getMacAddress()));
        }
    }

    private String getMac(String macAddress) {
        String mac = "";
        for (int i = 0; i < macAddress.length(); i++) {
            if (i % 2 == 0) {
                mac += macAddress.charAt(i);
            } else {
                mac += macAddress.charAt(i) + ":";
            }
        }
        mac = mac.substring(0, mac.length() - 1);
        return mac;
    }

    private void initAdapter() {
        for (DbLight dbLight : AppConfig.sDbLights) {
            dbLight.setSelect(false);
        }
        mLightList.setLayoutManager(new StaggeredGridLayoutManager(8, StaggeredGridLayoutManager.VERTICAL));
        mLightOperateAdapter = new LightOperateAdapter(mContext, AppConfig.sDbLights, new LightOperateAdapter.SetOnOperateListener() {
            @Override
            public void onOperateListener(DbLight light) {
                boolean flag = mSelectLight.contains(light);
                if (flag) {
                    mSelectLight.remove(light);
                } else {
                    mSelectLight.add(light);
                }
            }
        });
        mLightList.setAdapter(mLightOperateAdapter);
        mDeviceList.setLayoutManager(new LinearLayoutManager(mContext));
        mPowerAdapter = new PowerAdapter(mContext, AppConfig.sDbLights);
        mDeviceList.setAdapter(mPowerAdapter);
        mExecutorService.scheduleAtFixedRate(new PowerRunnable(), 5, 5, TimeUnit.SECONDS);

        mTimeInfoList.setLayoutManager(new LinearLayoutManager(mContext));
        mTimeInfoAdapter = new TimeInfoAdapter(mContext, mTimeInfoListData);
        mTimeInfoList.setAdapter(mTimeInfoAdapter);

        mShackView.setLayoutManager(new LinearLayoutManager(mContext));
        mShockInfoAdapter = new ShockInfoAdapter(mContext, mShockInfoList);
        mShackView.setAdapter(mShockInfoAdapter);

        mOrderRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new OrderAdapter(mContext, mOrderStrList);
        mOrderRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
        unregisterReceiver(mBroadcastReceiver);
    }

    private void initListener() {
        /*设置外圈灯感应前*/
        setOutBefore();
        /*设置内圈灯感应前*/
        setInBefore();
        /*设置红外*/
        setInfrared();
        /*设置感应*/
        setVibration();
        /*设置外圈灯感应后*/
        setOutAfter();
        /*设置内圈灯感应后*/
        setInAfter();
        /*设置感应前蜂鸣器*/
        setBuzzerBefore();
        /*设置感应后蜂鸣器*/
        setBuzzerAfter();

    }

    private void setBuzzerAfter() {
        mAfterBuzzer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.afterBuzzerNone:
                        mCommandNew.setAfterBuzzerModel(CommandRules.BuzzerModel.NONE);
                        break;
                    case R.id.afterBuzzerShort:
                        mCommandNew.setAfterBuzzerModel(CommandRules.BuzzerModel.SHORT);
                        break;
                    case R.id.afterBuzzer_1s:
                        mCommandNew.setAfterBuzzerModel(CommandRules.BuzzerModel.LONG_1);
                        break;
                    case R.id.afterBuzzer_2s:
                        mCommandNew.setAfterBuzzerModel(CommandRules.BuzzerModel.LONG_2);
                        break;
                    default:
                }
            }
        });
    }

    private void setBuzzerBefore() {
        mBuzzer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.buzzerNone:
                        mCommandNew.setBeforeBuzzerModel(CommandRules.BuzzerModel.NONE);
                        break;
                    case R.id.buzzerShort:
                        mCommandNew.setBeforeBuzzerModel(CommandRules.BuzzerModel.SHORT);
                        break;
                    case R.id.buzzer_1s:
                        mCommandNew.setBeforeBuzzerModel(CommandRules.BuzzerModel.LONG_1);
                        break;
                    case R.id.buzzer_2s:
                        mCommandNew.setBeforeBuzzerModel(CommandRules.BuzzerModel.LONG_2);
                        break;
                    default:
                }
            }
        });
    }

    private void setInAfter() {
        /*内圈灯亮灯模式*/
        mAfterInBlink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.afterInBlinkNone:
                        mCommandNew.setAfterInBlink(CommandRules.OpenModel.NONE);
                        break;
                    case R.id.afterInBlinkAlways:
                        mCommandNew.setAfterInBlink(CommandRules.OpenModel.ALWAYS);
                        break;
                    case R.id.afterInBlinkSlow:
                        mCommandNew.setAfterInBlink(CommandRules.OpenModel.SLOW);
                        break;
                    case R.id.afterInBlinkFast:
                        mCommandNew.setAfterInBlink(CommandRules.OpenModel.FAST);
                        break;
                    default:
                }
            }
        });
        /*内圈灯亮灯颜色*/
        mAfterInColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.afterInColorBlue:
                        mCommandNew.setAfterInColor(CommandRules.InColor.BLUE);
                        break;
                    case R.id.afterInColorRed:
                        mCommandNew.setAfterInColor(CommandRules.InColor.RED);
                        break;
                    case R.id.afterInColorPurple:
                        mCommandNew.setAfterInColor(CommandRules.InColor.PURPLE);
                        break;
                    default:

                }
            }
        });
    }

    private void setOutAfter() {
        /*外圈灯亮灯模式*/
        mAfterOutBlink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.afterOutBlinkNone:
                        mCommandNew.setAfterOutBlink(CommandRules.OpenModel.NONE);
                        break;
                    case R.id.afterOutBlinkAlways:
                        mCommandNew.setAfterOutBlink(CommandRules.OpenModel.ALWAYS);
                        break;
                    case R.id.afterOutBlinkSlow:
                        mCommandNew.setAfterOutBlink(CommandRules.OpenModel.SLOW);
                        break;
                    case R.id.afterOutBlinkFast:
                        mCommandNew.setAfterOutBlink(CommandRules.OpenModel.FAST);
                        break;
                    default:
                }
            }
        });
        /*外圈灯亮灯颜色*/
        mAfterOutColor1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.afterOutColorBlue:
                        if (mAfterOutColorBlue.isChecked()) {
                            mAfterOutColor2.clearCheck();
                            mCommandNew.setAfterOutColor(CommandRules.OutColor.BLUE);
                        }
                        break;
                    case R.id.afterOutColorRed:
                        if (mAfterOutColorRed.isChecked()) {
                            mAfterOutColor2.clearCheck();
                            mCommandNew.setAfterOutColor(CommandRules.OutColor.RED);
                        }
                        break;
                    case R.id.afterOutColorGreen:
                        if (mAfterOutColorGreen.isChecked()) {
                            mAfterOutColor2.clearCheck();
                            mCommandNew.setAfterOutColor(CommandRules.OutColor.GREEN);
                        }
                        break;
                    case R.id.afterOutColorPurple:
                        if (mAfterOutColorPurple.isChecked()) {
                            mAfterOutColor2.clearCheck();
                            mCommandNew.setAfterOutColor(CommandRules.OutColor.PURPLE);
                        }
                        break;
                    default:
                }
            }
        });
        mAfterOutColor2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.afterOutColorCyan:
                        if (mAfterOutColorCyan.isChecked()) {
                            mAfterOutColor1.clearCheck();
                            mCommandNew.setAfterOutColor(CommandRules.OutColor.CYAN);
                        }
                        break;
                    case R.id.afterOutColorYellow:
                        if (mAfterOutColorYellow.isChecked()) {
                            mAfterOutColor1.clearCheck();
                            mCommandNew.setAfterOutColor(CommandRules.OutColor.YELLOW);
                        }
                        break;
                    case R.id.afterOutColorWhite:
                        if (mAfterOutColorWhite.isChecked()) {
                            mAfterOutColor1.clearCheck();
                            mCommandNew.setAfterOutColor(CommandRules.OutColor.WHITE);
                        }
                        break;
                    default:
                }
            }
        });
    }

    private void setVibration() {
        /* 震动感应开关*/
        mVibrationInduced.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.vibrationInducedOn:
                        mCommandNew.setVibrationInduced(CommandRules.VibrationInduced.OPEN);
                        break;
                    case R.id.vibrationInducedOff:
                        mCommandNew.setVibrationInduced(CommandRules.VibrationInduced.CLOSE);
                        break;
                    default:
                }
            }
        });
        /*震动强度*/
        mVibrationIntensity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.vibrationIntensityL:
                        mCommandNew.setVibrationStrength(CommandRules.VibrationStrength.TOUCH_L);
                        break;
                    case R.id.vibrationIntensityM:
                        mCommandNew.setVibrationStrength(CommandRules.VibrationStrength.TOUCH_M);
                        break;
                    case R.id.vibrationIntensityH:
                        mCommandNew.setVibrationStrength(CommandRules.VibrationStrength.TOUCH_H);
                        break;
                    default:
                }
            }
        });
        /*震动详情*/
        mVibrationDetails.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.vibrationDetailsOn:
                        mCommandNew.setVibrationDetails(CommandRules.VibrationDetails.OPEN);
                        break;
                    case R.id.vibrationDetailsOff:
                        mCommandNew.setVibrationDetails(CommandRules.VibrationDetails.CLOSE);
                        break;
                    default:
                }
            }
        });
    }

    private void setInfrared() {
        /*红外发射*/
        mInfraredEmission.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.infraredEmissionOn:
                        mCommandNew.setInfraredEmission(CommandRules.InfraredEmission.OPEN);
                        break;
                    case R.id.infraredEmissionOff:
                        mCommandNew.setInfraredEmission(CommandRules.InfraredEmission.CLOSE);
                        break;
                    default:
                }
            }
        });
        /*红外感应*/
        mInfraredInduction.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.infraredInductionOn:
                        mCommandNew.setInfraredInduction(CommandRules.InfraredInduction.OPEN);
                        break;
                    case R.id.infraredInductionOff:
                        mCommandNew.setInfraredInduction(CommandRules.InfraredInduction.CLOSE);
                        break;
                    default:
                }
            }
        });
        /*红外模式*/
        mInfraredModel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.infraredModelNormal:
                        mCommandNew.setInfraredModel(CommandRules.InfraredModel.NORMAL);
                        break;
                    case R.id.infraredModelContend:
                        mCommandNew.setInfraredModel(CommandRules.InfraredModel.CONTEND);
                        break;
                    default:
                }
            }
        });
        /*红外高度*/
        mInfraredHeight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.infraredHeightLow:
                        mCommandNew.setInfraredHeight(CommandRules.InfraredHeight.MIN_LOW);
                        break;
                    case R.id.infraredHeight_5cm:
                        mCommandNew.setInfraredHeight(CommandRules.InfraredHeight.LOW);
                        break;
                    case R.id.infraredHeight_30cm:
                        mCommandNew.setInfraredHeight(CommandRules.InfraredHeight.HIGH);
                        break;
                    case R.id.infraredHeightHigh:
                        mCommandNew.setInfraredHeight(CommandRules.InfraredHeight.MAX_HIGH);
                        break;
                    default:
                }
            }
        });
    }

    private void setInBefore() {
        /*内圈灯亮灯模式*/
        mBeforeInBlink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.beforeInBlinkNone:
                        mCommandNew.setBeforeInBlink(CommandRules.OpenModel.NONE);
                        break;
                    case R.id.beforeInBlinkAlways:
                        mCommandNew.setBeforeInBlink(CommandRules.OpenModel.ALWAYS);
                        break;
                    case R.id.beforeInBlinkSlow:
                        mCommandNew.setBeforeInBlink(CommandRules.OpenModel.SLOW);
                        break;
                    case R.id.beforeInBlinkFast:
                        mCommandNew.setBeforeInBlink(CommandRules.OpenModel.FAST);
                        break;
                    default:
                }
            }
        });
        /*内圈灯亮灯颜色*/
        mBeforeInColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.beforeInColorBlue:
                        mCommandNew.setBeforeInColor(CommandRules.InColor.BLUE);
                        break;
                    case R.id.beforeInColorRed:
                        mCommandNew.setBeforeInColor(CommandRules.InColor.RED);
                        break;
                    case R.id.beforeInColorPurple:
                        mCommandNew.setBeforeInColor(CommandRules.InColor.PURPLE);
                        break;
                    default:

                }
            }
        });
    }

    private void setOutBefore() {
        /*外圈灯亮灯模式*/
        mBeforeOutBlink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.beforeOutBlinkNone:
                        mCommandNew.setBeforeOutBlink(CommandRules.OpenModel.NONE);
                        break;
                    case R.id.beforeOutBlinkAlways:
                        mCommandNew.setBeforeOutBlink(CommandRules.OpenModel.ALWAYS);
                        break;
                    case R.id.beforeOutBlinkSlow:
                        mCommandNew.setBeforeOutBlink(CommandRules.OpenModel.SLOW);
                        break;
                    case R.id.beforeOutBlinkFast:
                        mCommandNew.setBeforeOutBlink(CommandRules.OpenModel.FAST);
                        break;
                    default:
                }
            }
        });
        /*外圈灯亮灯颜色*/
        mBeforeOutColor1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.beforeOutColorBlue:
                        if (mBeforeOutColorBlue.isChecked()) {
                            mBeforeOutColor2.clearCheck();
                            mCommandNew.setBeforeOutColor(CommandRules.OutColor.BLUE);
                        }
                        break;
                    case R.id.beforeOutColorRed:
                        if (mBeforeOutColorRed.isChecked()) {
                            mBeforeOutColor2.clearCheck();
                            mCommandNew.setBeforeOutColor(CommandRules.OutColor.RED);
                        }
                        break;
                    case R.id.beforeOutColorGreen:
                        if (mBeforeOutColorGreen.isChecked()) {
                            mBeforeOutColor2.clearCheck();
                            mCommandNew.setBeforeOutColor(CommandRules.OutColor.GREEN);
                        }
                        break;
                    case R.id.beforeOutColorPurple:
                        if (mBeforeOutColorPurple.isChecked()) {
                            mBeforeOutColor2.clearCheck();
                            mCommandNew.setBeforeOutColor(CommandRules.OutColor.PURPLE);
                        }
                        break;
                    default:
                }
            }
        });
        mBeforeOutColor2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.beforeOutColorCyan:
                        if (mBeforeOutColorCyan.isChecked()) {
                            mBeforeOutColor1.clearCheck();
                            mCommandNew.setBeforeOutColor(CommandRules.OutColor.CYAN);
                        }
                        break;
                    case R.id.beforeOutColorYellow:
                        if (mBeforeOutColorYellow.isChecked()) {
                            mBeforeOutColor1.clearCheck();
                            mCommandNew.setBeforeOutColor(CommandRules.OutColor.YELLOW);
                        }
                        break;
                    case R.id.beforeOutColorWhite:
                        if (mBeforeOutColorWhite.isChecked()) {
                            mBeforeOutColor1.clearCheck();
                            mCommandNew.setBeforeOutColor(CommandRules.OutColor.WHITE);
                        }
                        break;
                    default:
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        backFinish();
    }

    @OnClick({R.id.back,
            R.id.sleepOrder, R.id.orderNum,
            R.id.sleep, R.id.send, R.id.turnOn, R.id.turnOff,
            R.id.selectAll, R.id.selectNone,
            R.id.alter,
            R.id.resetCoordinator, R.id.clearData})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                backFinish();
                break;
            case R.id.sleepOrder:
                mAlterSleepDialog = new AlterSleepDialog(mContext, new AlterSleepDialog.SetOnDialogListener() {
                    @Override
                    public void sureClick() {
                        mSleepOrder.setText("命令间隔：" + AppConfig.sSleep + " ms");
                        mAlterSleepDialog.dismiss();
                    }
                });
                mAlterSleepDialog.show();
                break;
            case R.id.orderNum:
                mAlterOrderNumDialog = new AlterOrderNumDialog(mContext, new AlterOrderNumDialog.SetOnDialogListener() {
                    @Override
                    public void sureClick() {
                        mOrderNum.setText("命令重发次数：" + AppConfig.sNum);
                        mAlterOrderNumDialog.dismiss();
                    }
                });
                mAlterOrderNumDialog.show();
                break;
            case R.id.sleep:
                for (DbLight dbLight : mSelectLight) {
                    dbLight.setSelect(false);
                }
                mSelectLight.clear();
                mLightOperateAdapter.notifyDataSetChanged();
                OrderUtils.getInstance().sleepAllLight(AppConfig.sDbLights);
                break;
            case R.id.send:
                sendCommand();
                break;
            case R.id.turnOn:
                OrderUtils.getInstance().turnOnAllLight(AppConfig.sDbLights);
                break;
            case R.id.turnOff:
                OrderUtils.getInstance().closeAllLight(AppConfig.sDbLights);
                break;
            case R.id.selectAll:
                selectAll();
                break;
            case R.id.selectNone:
                selectNone();
                break;
            case R.id.alter:
                if (AppConfig.sDevice != null) {
                    showHintDialog("是否对该协调器下的光标进行编辑？");
                }
                break;
            case R.id.resetCoordinator:
                mAlertDialog = new AlertDialog.Builder(mContext)
                        .setTitle("提示：")
                        .setMessage("重置前，请尽可能保证光标在线？")
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            //添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mAlertDialog.dismiss();
                                mCusProgressDialog.showMessage("协调器重置中...");
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        resetCoordinator();
                                    }
                                }).start();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            //添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mAlertDialog.dismiss();
                            }
                        }).create();
                mAlertDialog.show();
                break;
            case R.id.clearData:
                mTimeInfoListData.clear();
                mShockInfoList.clear();
                mOrderStrList.clear();

                mTimeInfoAdapter.notifyDataSetChanged();
                mShockInfoAdapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();

                break;
            default:
        }
    }

    private void backFinish() {
        finish();
    }

    private void resetCoordinator() {
        OrderUtils.getInstance().removeNet(AppConfig.sDbLights);
        SystemClock.sleep(2000);
        OrderUtils.getInstance().resetCoordinator();
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RealmUtils.deleteDevice(mRealm, AppConfig.sDevice, new RealmUtils.SetOnDeleteListener() {
                    @Override
                    public void onDeleteListener(Boolean flag) {
                        if (flag) {
                            /*初始化协调器*/
                            OrderUtils.getInstance().initDevice();
                            int waitNum = 0;
                            while (AppConfig.sDevice == null && waitNum < 3) {
                                SystemClock.sleep(500);
                            }
                            if (waitNum == 3) {
                                mCusProgressDialog.dismiss();
                                Toast.makeText(mContext, "未获取到协调器PanId", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            getSaveLight();
                        } else {
                            mCusProgressDialog.dismiss();
                            Toast.makeText(mContext, "数据清除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    /**
     * 获取保存的光标
     */
    private void getSaveLight() {
        RealmUtils.queryDevice(mRealm, AppConfig.sDevice.getPanId(), new RealmUtils.SetOnIsHaveListener() {
            @Override
            public void onIsHaveListener(Boolean flag) {
                if (!flag) {
                    RealmUtils.saveDevice(mRealm, AppConfig.sDevice, new RealmUtils.SetOnSaveListener() {
                        @Override
                        public void onSaveListener(Boolean flag) {
                            if (!flag) {
                                Toast.makeText(mContext, "协调器保存失败", Toast.LENGTH_SHORT).show();
                            }
                            mCusProgressDialog.dismiss();
                        }
                    });
                }
            }
        });

    }

    private void selectNone() {
        mSelectLight.clear();
        for (DbLight dbLight : AppConfig.sDbLights) {
            dbLight.setSelect(false);
        }
        mLightOperateAdapter.notifyDataSetChanged();
    }

    private void selectAll() {
        mSelectLight.clear();
        for (DbLight dbLight : AppConfig.sDbLights) {
            if (dbLight.getPower() != "0000") {
                dbLight.setSelect(true);
                mSelectLight.add(dbLight);
            }
        }
        mLightOperateAdapter.notifyDataSetChanged();
    }

    private void sendCommand() {
        if (mSelectLight.size() > 0) {
            List<Order> orderList = new ArrayList<>();
            for (DbLight dbLight : mSelectLight) {
                Order order = new Order();
                order.setCommandNew(mCommandNew);
                order.setLight(dbLight);
                orderList.add(order);
            }
            OrderUtils.getInstance().sendCommand(orderList, new OrderUtils.SetOnOrderListener() {
                @Override
                public void onOrderListener(String order) {
                    mOrderStrList.add(order);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mAdapter.notifyDataSetChanged();
//                        }
//                    });
                }
            });
        } else {
            Toast.makeText(mContext, "未选择光标！", Toast.LENGTH_SHORT).show();
        }
    }

    private void showHintDialog(String hint) {
        //添加"Yes"按钮
        //添加取消
        mAlertDialog = new AlertDialog.Builder(this)
                .setTitle("编辑：")
                .setMessage(hint)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    //添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertDialog.dismiss();
                        showAlterDialog();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    //添加取消
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertDialog.dismiss();
                    }
                }).create();
        mAlertDialog.show();
    }

    class PowerRunnable implements Runnable {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPowerAdapter.notifyDataSetChanged();
                    mLightOperateAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
