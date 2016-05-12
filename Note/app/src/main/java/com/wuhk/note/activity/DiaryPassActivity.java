package com.wuhk.note.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jungly.gridpasswordview.GridPasswordView;
import com.wuhk.note.R;
import com.wuhk.note.activity.BaseActivity;
import com.wuhk.note.activity.edit.EditDiaryActivity;
import com.wuhk.note.activity.frame.fragment.Fragment1;
import com.wuhk.note.adapter.DiaryAdapter;
import com.wuhk.note.dao.DaoFactory;
import com.wuhk.note.entity.DiaryEntity;
import com.wuhk.note.receiver.RefreshNormalDiaryReceiver;
import com.wuhk.note.utils.ToastUtil;
import com.xuan.bigapple.lib.ioc.InjectView;
import com.xuan.bigapple.lib.utils.sharepreference.BPPreferences;
import com.xuan.bigdog.lib.widgets.title.DGTitleLayout;

/**
 * Created by wuhk on 2016/5/11.
 */
public class DiaryPassActivity extends BaseActivity {
    @InjectView(R.id.titleLayout)
    private DGTitleLayout titleLayout;

    @InjectView(R.id.passDesTv)
    private TextView passDesTv;

    @InjectView(R.id.gridPasswordView)
    private GridPasswordView gridPasswordView;

    private String operate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_encrypt);
        initWidgets();
    }

    private void initWidgets(){
        operate = getIntent().getStringExtra("operate");

        titleLayout.configReturn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.passSucceed = false;
                finish();
            }
        }).configTitle("密码锁");

        if (operate.equals("input")){
            passDesTv.setText("输入密码，打开日记本");
        }else if (operate.equals("set")){
            passDesTv.setText("设置加密日记本的密码");
        }
        gridPasswordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                if (operate.equals("input")){
                    String myPass = BPPreferences.instance().getString("myPassword" , "");
                    if (myPass.equals(psw)){
                        MainActivity.passSucceed = true;
                        Fragment1.loadByEncrpt = true;
//                        RefreshNormalDiaryReceiver.notifyReceiver(true);
                        finish();
                    }else{
                        ToastUtil.toast("密码错误，请重新输入");
                        gridPasswordView.clearPassword();
                    }
                }else if (operate.equals("set")){
                    BPPreferences.instance().putBoolean("isHavePass", true);
                    BPPreferences.instance().putString("myPassword", psw);
                    MainActivity.passSucceed = true;
                    Fragment1.loadByEncrpt = true;
//                    RefreshNormalDiaryReceiver.notifyReceiver(true);
                    finish();
                }
            }
        });


    }
}
