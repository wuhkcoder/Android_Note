package com.wuhk.note.activity;

import android.os.Bundle;
import android.view.View;

import com.jungly.gridpasswordview.GridPasswordView;
import com.jungly.gridpasswordview.PasswordType;
import com.wuhk.note.R;
import com.wuhk.note.utils.ToastUtil;
import com.xuan.bigapple.lib.ioc.InjectView;
import com.xuan.bigdog.lib.widgets.title.DGTitleLayout;

/**
 * 加密Activity
 * Created by wuhk on 2016/5/8.
 */
public class EncryptActivity extends BaseActivity {

    @InjectView(R.id.titleLayout)
    private DGTitleLayout titleLayout;

    @InjectView(R.id.gridPasswordView)
    private GridPasswordView gridPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_encrypt);
        initWidgets();
    }

    private void initWidgets(){
        titleLayout.configReturn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).configTitle("密码锁");

        gridPasswordView.setPasswordType(PasswordType.NUMBER);
        gridPasswordView.setPasswordVisibility(true);
        gridPasswordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                //完成输入
                ToastUtil.toast("输入的密码是：" + psw);
            }
        });

    }
}
