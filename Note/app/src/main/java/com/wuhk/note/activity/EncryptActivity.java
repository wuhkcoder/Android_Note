package com.wuhk.note.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jungly.gridpasswordview.GridPasswordView;
import com.jungly.gridpasswordview.PasswordType;
import com.wuhk.note.R;
import com.wuhk.note.activity.edit.EditDiaryActivity;
import com.wuhk.note.activity.frame.fragment.Fragment1;
import com.wuhk.note.adapter.DiaryAdapter;
import com.wuhk.note.dao.DaoFactory;
import com.wuhk.note.entity.DiaryEntity;
import com.wuhk.note.utils.ToastUtil;
import com.xuan.bigapple.lib.ioc.InjectView;
import com.xuan.bigapple.lib.utils.ContextUtils;
import com.xuan.bigapple.lib.utils.sharepreference.BPPreferences;
import com.xuan.bigdog.lib.widgets.title.DGTitleLayout;

/**
 * 加密Activity
 * Created by wuhk on 2016/5/8.
 */
public class EncryptActivity extends BaseActivity {

    @InjectView(R.id.titleLayout)
    private DGTitleLayout titleLayout;

    @InjectView(R.id.passDesTv)
    private TextView passDesTv;

    @InjectView(R.id.gridPasswordView)
    private GridPasswordView gridPasswordView;

    private String type;
    private DiaryEntity diaryEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_encrypt);
        initWidgets();
    }

    private void initWidgets(){
        type = getIntent().getStringExtra(DiaryAdapter.TYPE);

        titleLayout.configReturn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).configTitle("密码锁");

        if (type.equals("setPass")){
            passDesTv.setText("设置密码");
        }else if (type.equals("inputPass")){
            passDesTv.setText("输入密码");
        }else if (type.equals("cancelPass")){
            passDesTv.setText("输入密码");
        }

        gridPasswordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                //单篇设置密码
                if (type.equals("setPass") ){
                    diaryEntity = JSON.parseObject(getIntent().getStringExtra(DiaryAdapter.DIARY) , DiaryEntity.class);
                    diaryEntity.setEncrypt(2);
                    diaryEntity.setPassword(psw);
                    DaoFactory.getDiaryDao().insertOrReplace(diaryEntity);
                    ToastUtil.toast("密码设置成功");

                    finish();
                }
                //输入密码解锁单篇
                if (type.equals("inputPass")){
                    diaryEntity = JSON.parseObject(getIntent().getStringExtra(DiaryAdapter.DIARY) , DiaryEntity.class);
                    if (diaryEntity.getPassword().equals(psw)){
                        //密码正确，进入日记
                        Intent intent = new Intent();
                        intent.setClass(EncryptActivity.this , EditDiaryActivity.class);
                        intent.putExtra(DiaryAdapter.DIARY , JSON.toJSONString(diaryEntity));
                        startActivity(intent);
                        finish();
                    }else{
                        ToastUtil.toast("密码错误，请重新输入");
                        gridPasswordView.clearPassword();
                    }
                }
                //取消密码设置
                if (type.equals("cancelPass")){
                    diaryEntity = JSON.parseObject(getIntent().getStringExtra(DiaryAdapter.DIARY) , DiaryEntity.class);
                    if (diaryEntity.getPassword().equals(psw)){
                        //密码正确，解除密码锁
                        diaryEntity.setEncrypt(1);
                        diaryEntity.setPassword("");
                        DaoFactory.getDiaryDao().insertOrReplace(diaryEntity);
                        ToastUtil.toast("解锁成功");
                        finish();
                    }else{
                        ToastUtil.toast("密码错误，请重新输入");
                        gridPasswordView.clearPassword();
                    }
                }
//                //全局密码设置
//                if (type.equals("setPass") && mode.equals("mutli")){
//                    BPPreferences.instance().putBoolean("isPassed" , true);
//                    BPPreferences.instance().putString("globalPass" , psw);
//                    finish();
//                }
//                //全局取消密码设置
//                if (type.equals("cancelPass") && mode.equals("mutli")){
//                    BPPreferences.instance().putBoolean("isPassed" , false);
//                    finish();
//                }
//                //全局加密解锁
//                if (type.equals("inputPass") && mode.equals("mutli")){
//                    if (BPPreferences.instance().getString("globalPass" , "").equals(psw)){
//                        Intent intent = new Intent();
//                        intent.setClass(EncryptActivity.this , MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
            }
        });


    }
}
