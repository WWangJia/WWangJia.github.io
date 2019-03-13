package com.b304.wang.saflightsample.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.b304.wang.saflightsample.App.SaflightSampleApplication;
import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.adapter.UnitAdapter;
import com.b304.wang.saflightsample.adapter.UnitLightListAdapter;
import com.b304.wang.saflightsample.dialog.FientTimeSetDialog;
import com.b304.wang.saflightsample.dialog.HintDialog;
import com.b304.wang.saflightsample.dialog.lightDialog;
import com.b304.wang.saflightsample.entity.Light;
import com.b304.wang.saflightsample.entity.Unit;
import com.b304.wang.saflightsample.utils.RealmUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;

import static android.widget.Toast.LENGTH_SHORT;

public class UnitSettingActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.backName)
    TextView mBackName;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.add_unit)
    ImageButton mAddUnit;
    @BindView(R.id.unit_girdView)
    GridView mUnitGirdView;
    @BindView(R.id.unit_name)
    TextView mUnitName;
    @BindView(R.id.spinner_trainTimes)
    Spinner mSpinnerTrainTimes;
    @BindView(R.id.random)
    Button mRandom;
    @BindView(R.id.delay_time)
    EditText mDelayTime;
    @BindView(R.id.delay_sure)
    Button mDelaySure;
    @BindView(R.id.remark)
    EditText mRemark;
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
    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.clear)
    Button mClear;
    @BindView(R.id.save)
    Button mSave;
    private Realm mRealm;
    //单元列表
    private ArrayList<Unit> mUnitList;
    //单元列表adapter
    private UnitAdapter mUnitAdapter;
    //具体单元列表亮灯顺序list
    private List<Light> mLightList = new ArrayList<>();
    //具体单元列表亮灯顺序adapter
    private UnitLightListAdapter mUnitLightListAdapter;
    //获取时间
    private SimpleDateFormat formatter;
    //弹窗
    private HintDialog mHintDialog;
    //产生随机数
    private Random mrandom = new Random();
    //当前选中的单元
    private Unit unit;
    //右侧设置延时弹窗
    lightDialog mLightDialog;
    //右侧设置虚晃弹窗
    FientTimeSetDialog mFientTimeSetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_setting);
        ButterKnife.bind(this);
        SaflightSampleApplication.getInstance().addActivityOrder(this);
        mRealm = Realm.getDefaultInstance();
        initData();
        initView();
        setListner();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        //设置标题
        mTitle.setText("单元设置");
        //设置mUnitGirdView列表
        mUnitGirdView.setNumColumns(1);
        mUnitGirdView.setVerticalSpacing(5);
        mUnitAdapter = new UnitAdapter(mUnitList);
        mUnitGirdView.setAdapter(mUnitAdapter);
        //设置mListView列表
        mUnitLightListAdapter = new UnitLightListAdapter(mLightList,1);
        mListView.setAdapter(mUnitLightListAdapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //读单元数据
        mUnitList = RealmUtils.queryUnit(mRealm);
    }

    /**
     * 设置监听
     */
    private void setListner() {
        //设置mUnitGirdView监听
        mUnitGirdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                unit = mUnitList.get(position);
                mUnitName.setText("单元"+(position+1));
                mRemark.setText(unit.getRemark());
                mSpinnerTrainTimes.setSelection(unit.getTimes());
                //右侧listview更新
                mLightList.clear();
                mLightList.addAll(unit.getLightList());
                mUnitLightListAdapter = new UnitLightListAdapter(mLightList,1);
                mListView.setAdapter(mUnitLightListAdapter);
            }
        });

        //设置mUnitGirdView监听,长按删除
        mUnitGirdView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int position1 = position;
                mHintDialog = new HintDialog(UnitSettingActivity.this, "确定要删除此训练单元吗？", "否", "是", new HintDialog.DialogOperation() {
                    @Override
                    public void sureClick() {
                        mHintDialog.dismiss();
                        unit = mUnitList.get(position1);

                        mUnitList.remove(position1);
                        mUnitAdapter.notifyDataSetChanged();
                        //在数据库删除
                        RealmUtils.deleteUnit(mRealm, unit, new RealmUtils.SetOnDeleteListener() {
                            @Override
                            public void onDeleteListener(Boolean flag) {
                                if (flag) {
                                    mLightList.clear();
                                    mUnitLightListAdapter.notifyDataSetChanged();
                                    Toast.makeText(UnitSettingActivity.this, "删除成功！", LENGTH_SHORT).show();
                                    mSpinnerTrainTimes.setSelection(0);
                                    mRemark.setText("");
                                } else
                                    Toast.makeText(UnitSettingActivity.this, "删除完成！", LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void cancelClick() {
                        mHintDialog.dismiss();
                    }
                });
                mHintDialog.show();


                return false;
            }
        });

        //设置右侧listview点击监听
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mLightList.get(position).isFeint()) {  //设置为延时
                    mLightDialog = new lightDialog(UnitSettingActivity.this, mLightList.get(position), new lightDialog.DialogOperation() {
                        @Override
                        public void sureClick() {
                            mLightDialog.dismiss();
                            mUnitLightListAdapter.notifyDataSetChanged();
                        }
                    });
                    mLightDialog.show();
                } else {//设置为虚晃
                    mFientTimeSetDialog = new FientTimeSetDialog(UnitSettingActivity.this, mLightList.get(position), new FientTimeSetDialog.DialogOperation() {
                        @Override
                        public void sureClick() {
                            mFientTimeSetDialog.dismiss();
                            mUnitLightListAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void cancelClick() {
                            mFientTimeSetDialog.dismiss();
                        }
                    });
                    mFientTimeSetDialog.show();
                }
            }
        });

        //设置右侧listview点击监听 长按删除
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final int position1=position;
                mHintDialog = new HintDialog(UnitSettingActivity.this, "确定要删除吗？", "否", "是", new HintDialog.DialogOperation() {
                    @Override
                    public void sureClick() {
                        mLightList.remove(position1);
                        mUnitLightListAdapter.notifyDataSetChanged();
                        mHintDialog.dismiss();
                    }

                    @Override
                    public void cancelClick() {
                        mHintDialog.dismiss();
                    }
                });
                mHintDialog.show();
                return true;
            }
        });
    }


    @OnClick({R.id.add_unit, R.id.random, R.id.save,
            R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6,
            R.id.back,R.id.backName,R.id.delay_sure,R.id.clear})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.add_unit:
                add_unit();
                break;
            case R.id.random:
                random_produce_lightList();
                break;
            case R.id.save:
                saveUnit();
                break;
            case R.id.button1:
                add_light(1);
                break;
            case R.id.button2:
                add_light(2);
                break;
            case R.id.button3:
                add_light(3);
                break;
            case R.id.button4:
                add_light(4);
                break;
            case R.id.button5:
                add_light(5);
                break;
            case R.id.button6:
                add_light(6);
                break;
            case R.id.back:
            case R.id.backName:
                SaflightSampleApplication.getInstance().finish();
                break;
            case R.id.delay_sure:
                String str=mDelayTime.getText().toString().trim();
                if(str!=null&&str.length()!=0){
                    //目前设备灵敏性要求
                    if(Integer.parseInt(str)<5)
                        Toast.makeText(UnitSettingActivity.this,"数值无效",Toast.LENGTH_SHORT).show();
                    for(int i=0;i<mLightList.size();i++){
                        mLightList.get(i).setDelayTime(Integer.parseInt(str));
                    }
                    mUnitLightListAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(UnitSettingActivity.this,"数值无效",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.clear:
                //清楚显示数据
                mUnitName.setText("");
                mSpinnerTrainTimes.setSelection(0);
                mDelayTime.setText("");
                mRemark.setText("");
                mLightList.clear();
                mUnitLightListAdapter.notifyDataSetChanged();
                break;
            default:
        }
    }

    /**
     * 增加light
     */
    private void add_light(int num) {
        Light light = new Light();
        light.setNum(num);
        light.setDelayTime(100);
        mLightList.add(light);
        mUnitLightListAdapter.notifyDataSetChanged();
        mSpinnerTrainTimes.setSelection(mLightList.size());
    }

    /**
     * 保存单元
     */
    private void saveUnit() {
        unit.setAction(1);
        unit.setTimes(mLightList.size());
        unit.setRemark(mRemark.getText().toString().trim());
        RealmList<Light> unitRealmList=new RealmList<>();
        for(int i=0;i<mLightList.size();i++){
            Light light=new Light();
            light.setDelayTime(mLightList.get(i).getDelayTime());
            light.setFeint(mLightList.get(i).isFeint());
            light.setNum(mLightList.get(i).getNum());
            unitRealmList.add(light);
        }
        unit.setLightList(unitRealmList);
        mHintDialog = new HintDialog(UnitSettingActivity.this, "确定要保存训练单元吗？", "否", "是", new HintDialog.DialogOperation() {
            @Override
            public void sureClick() {
                RealmUtils.saveUnit(mRealm, unit, new RealmUtils.SetOnSaveListener() {
                    @Override
                    public void onSaveListener(Boolean flag) {
                        if (flag)
                            Toast.makeText(UnitSettingActivity.this, "保存成功！", LENGTH_SHORT).show();
                        else
                            Toast.makeText(UnitSettingActivity.this, "保存失败！", LENGTH_SHORT).show();
                    }
                });
                mHintDialog.dismiss();
            }

            @Override
            public void cancelClick() {
                mHintDialog.dismiss();
            }
        });
        mHintDialog.show();
        mUnitAdapter.notifyDataSetChanged();
    }

    /**
     * 随机产生制定个数的亮灯顺序
     */
    private void random_produce_lightList() {
        int times = mSpinnerTrainTimes.getSelectedItemPosition();
        if (times == 0) {
            Toast.makeText(this, "请选择训练次数", LENGTH_SHORT).show();
            return;
        }
        mLightList.clear();
        for (int i = 0; i < times; i++) {
            Light light = new Light();
            light.setDelayTime(100);
            light.setNum(1 + mrandom.nextInt(6));
            mLightList.add(light);
        }
        mUnitLightListAdapter = new UnitLightListAdapter(mLightList,1);
        mListView.setAdapter(mUnitLightListAdapter);
        // mUnitLightListAdapter.notifyDataSetChanged();
    }

    /**
     * 添加单元
     */
    private void add_unit() {
        Unit unit = new Unit();
        int temp = mUnitList.size() + 1;
        unit.setName("");

        //获取当前时间
        formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        unit.setDate(time);

        mUnitList.add(unit);
        mUnitAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
        //SaflightSampleApplication.getInstance().finish();
    }

    @Override
    public void onBackPressed() {
       SaflightSampleApplication.getInstance().finish();
    }
}
