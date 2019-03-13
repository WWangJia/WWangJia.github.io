package com.b304.wang.saflightsample.activity;

import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.b304.wang.saflightsample.App.SaflightSampleApplication;
import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.adapter.UnitAdapter2;
import com.b304.wang.saflightsample.adapter.UnitAdapter3;
import com.b304.wang.saflightsample.adapter.UnitLightListAdapter2;
import com.b304.wang.saflightsample.dialog.CustomTimeSetDialog;
import com.b304.wang.saflightsample.dialog.HintDialog;
import com.b304.wang.saflightsample.dialog.SpinnerPopWindow;
import com.b304.wang.saflightsample.entity.GroupOfUnit;
import com.b304.wang.saflightsample.entity.Light;
import com.b304.wang.saflightsample.entity.Unit;
import com.b304.wang.saflightsample.utils.RealmUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;

import static android.widget.Toast.LENGTH_SHORT;

public class GroupSetActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.backName)
    TextView mBackName;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.unit_girdView)
    GridView mUnitGirdView;
    @BindView(R.id.light_list_view)
    ListView mLightListView;
    @BindView(R.id.train_name)
    EditText mTrainName;
    @BindView(R.id.drop_button)
    ImageButton mDropButton;
    @BindView(R.id.group_name)
    EditText mGroupName;
    @BindView(R.id.light_num)
    TextView mLightNum;
    @BindView(R.id.group_num)
    TextView mGroupNum;
    @BindView(R.id.remark)
    EditText mRemark;
    @BindView(R.id.group_list_view)
    ListView mGroupListView;
    @BindView(R.id.clear)
    Button mClear;
    @BindView(R.id.save)
    Button mSave;

    private Realm mRealm;
    //单元列表
    private ArrayList<Unit> mUnitList;
    //单元列表adapter
    private UnitAdapter2 mUnitAdapter2;
    //当前选中的单元
    private Unit unit;
    //具体单元列表亮灯顺序list
    private List<Light> mLightList = new ArrayList<>();
    //训练形式数据
    List<String> dataList= new ArrayList<>();
    //具体单元列表亮灯顺序adapter
    private UnitLightListAdapter2 mUnitLightListAdapter2;
    //mGroupListView列表adapter
    private UnitAdapter3 mUnitAdapter3;
    //下拉列表框
    private SpinnerPopWindow mSpinnerPopWindow;
    //存储组unit
    private ArrayList<Unit> groupOfUnit=new ArrayList<>();
    //自定义时间弹窗
    private CustomTimeSetDialog mCustomTimeSetDialog;
    //亮灯数量，组合次数
    private int lightNum=0,GroupNum=0;
    //弹窗
    private HintDialog mHintDialog;
    //获取文件中的值
    private String value="";
    //获取时间
    private SimpleDateFormat formatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_set);
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
        //读单元数据
        mUnitList = RealmUtils.queryUnit(mRealm);
        //读训练形式数据
        getData();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        //设置标题
        mTitle.setText("组合设置");
        //设置mUnitGirdView列表
        mUnitGirdView.setNumColumns(1);
        mUnitGirdView.setVerticalSpacing(5);
        mUnitAdapter2 = new UnitAdapter2(mUnitList, new UnitAdapter2.Interfacer() {
            @Override
            public void addClick(Unit unit) {
                final Unit munit=new Unit(unit);
                        mCustomTimeSetDialog=new CustomTimeSetDialog(GroupSetActivity.this, munit, new CustomTimeSetDialog.DialogOperation() {
                            @Override
                            public void sureClick() {
                                mCustomTimeSetDialog.dismiss();
                                //亮灯数量，组合次数显示
                                showNum(0,munit);
                                groupOfUnit.add(munit);
                                mUnitAdapter3.notifyDataSetChanged();
                            }

                            @Override
                            public void cancelClick() {
                                mCustomTimeSetDialog.dismiss();
                            }
                        });
                        mCustomTimeSetDialog.show();

            }
        });
        mUnitGirdView.setAdapter(mUnitAdapter2);

        //设置mGroupListView列表
        mUnitAdapter3=new UnitAdapter3(1,groupOfUnit, new UnitAdapter3.Interfacer() {
            @Override
            public void deleteClick(int position) {
                showNum(1,groupOfUnit.get(position));
                groupOfUnit.remove(position);
                mUnitAdapter3.notifyDataSetChanged();
            }
        });
        mGroupListView.setAdapter(mUnitAdapter3);
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
                //右侧listview更新
                mLightList.clear();
                mLightList.addAll(unit.getLightList());
                mUnitLightListAdapter2 = new UnitLightListAdapter2(mLightList);
                mLightListView.setAdapter(mUnitLightListAdapter2);
            }
        });
    }

    /**
     *
     * @param flag  0:增加unit, 1:减少unit
     * @param unit
     */
   public void showNum(int flag,Unit unit){
        if(flag==0){
            lightNum+=unit.getLightList().size();
            GroupNum++;
        }else{
            lightNum-=unit.getLightList().size();
            GroupNum--;
        }
        mGroupNum.setText(GroupNum+"");
        mLightNum.setText(lightNum+"");
   }


    @OnClick({R.id.drop_button,R.id.save,R.id.clear,R.id.back,R.id.backName})
    public void  onViewClick(View view){
        switch (view.getId()){
            case R.id.drop_button:
                if(mSpinnerPopWindow==null)
                    mSpinnerPopWindow=new SpinnerPopWindow();
                mSpinnerPopWindow.show(GroupSetActivity.this, mTrainName, dataList, new SpinnerPopWindow.onClickListener() {
                    @Override
                    public void onClick(int position) {
                        mTrainName.setText(dataList.get(position));
                        //Toast.makeText(GroupSetActivity.this,list.get(position),Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.save:
                saveGroupSet();
                break;
            case R.id.back:
            case R.id.backName:
                SaflightSampleApplication.getInstance().finish();
                break;
            case R.id.clear:
                clear();
                break;
            default:
                break;
        }
   }

    /**
     * 保存
     */
   public void saveGroupSet(){
       if(mTrainName.getText().toString().trim()==null||"".equals(mTrainName.getText().toString().trim())) {
           Toast.makeText(GroupSetActivity.this, "请填写训练形式！", Toast.LENGTH_SHORT).show();
           return;
       }
       if(mGroupName.getText().toString().trim()==null||"".equals(mGroupName.getText().toString().trim())) {
           Toast.makeText(GroupSetActivity.this, "请填写组合名称！", Toast.LENGTH_SHORT).show();
           return;
       }
       if(lightNum==0||GroupNum==0) {
           Toast.makeText(GroupSetActivity.this, "请添加训练单元！", Toast.LENGTH_SHORT).show();
           return;
       }

       final GroupOfUnit mGroupOfUnit=new GroupOfUnit();
       mGroupOfUnit.setTrainStyle(mTrainName.getText().toString().trim());
       mGroupOfUnit.setGroupName(mGroupName.getText().toString().trim());
       mGroupOfUnit.setRemark(mRemark.getText().toString().trim());
       mGroupOfUnit.setLight_num(lightNum);
       mGroupOfUnit.setUnit_num(GroupNum);
       RealmList<Unit> unitRealmList = new RealmList<>();
       for(int j=0;j<groupOfUnit.size();j++) {
           Unit munit=new Unit(groupOfUnit.get(j));
           unitRealmList.add(munit);
       }
       mGroupOfUnit.setUnitsList(unitRealmList);

       //获取当前时间
       formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
       Date curDate = new Date(System.currentTimeMillis());
       String time = formatter.format(curDate);
       mGroupOfUnit.setDate(time);
       
       mHintDialog = new HintDialog(GroupSetActivity.this, "确定要保存训练组合吗？", "否", "是", new HintDialog.DialogOperation() {
           @Override
           public void sureClick() {
               setData();
               RealmUtils.saveGroupOfUnit(mRealm, mGroupOfUnit, new RealmUtils.SetOnSaveListener() {
                   @Override
                   public void onSaveListener(Boolean flag) {
                       if (flag)
                           Toast.makeText(GroupSetActivity.this, "保存成功！", LENGTH_SHORT).show();
                       else
                           Toast.makeText(GroupSetActivity.this, "保存失败！", LENGTH_SHORT).show();
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
   }

    /**
     * 清空
     */
   public void clear(){
       mTrainName.setText("");
       mGroupName.setText("");
       mLightNum.setText("");
       mGroupNum.setText("");
       mRemark.setText("");
       groupOfUnit.clear();
       mUnitAdapter3.notifyDataSetChanged();
   }
   public void getData(){
       //步骤1：创建一个SharedPreferences接口对象
       SharedPreferences read = getSharedPreferences("GroupSet", MODE_PRIVATE);
       //步骤2：获取文件中的值
       value = read.getString("trainStyle", "");
       String[] str=value.split(",");
       for(int i=0;i<str.length;i++){
           dataList.add(str[i]);
       }
   }

   public void setData(){
       String str=mTrainName.getText().toString().trim();
       //检查有没有此训练形式，若没有，则添加
       boolean hasFlag=false;
       for(int i=0;i<dataList.size();i++){
           if(dataList.get(i).equals(str)){
               hasFlag=true;
               break;
           }
       }
       if(hasFlag==false){
           value+=str+",";
       }

       //步骤1：创建一个SharedPreferences.Editor接口对象，lock表示要写入的XML文件名，MODE_WORLD_WRITEABLE写操作
       SharedPreferences.Editor editor = getSharedPreferences("GroupSet", MODE_WORLD_WRITEABLE).edit();
       //步骤2：将获取过来的值放入文件
       editor.putString("trainStyle", value);
       //步骤3：提交
       editor.commit();
   }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
    }

    @Override
    public void onBackPressed() {
        SaflightSampleApplication.getInstance().finish();
    }
}
