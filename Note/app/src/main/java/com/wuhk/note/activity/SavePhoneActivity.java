package com.wuhk.note.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.wuhk.note.R;
import com.wuhk.note.activity.BaseActivity;
import com.wuhk.note.utils.ToastUtil;
import com.xuan.bigapple.lib.ioc.InjectView;
import com.xuan.bigapple.lib.utils.Validators;
import com.xuan.bigapple.lib.utils.sharepreference.BPPreferences;
import com.xuan.bigdog.lib.widgets.title.DGTitleLayout;

/**
 * 保存手机号
 * Created by wuhk on 2016/5/12.
 */
public class SavePhoneActivity extends BaseActivity {
    @InjectView(R.id.titleLayout)
    private DGTitleLayout titleLayout;

    @InjectView(R.id.phoneEt)
    private EditText phoneEt;

    @InjectView(R.id.sureBtn)
    private Button sureBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_save_phone);
        initWidgets();
    }

    private void initWidgets(){
        if (BPPreferences.instance().getBoolean("isFirst" , true)){
            titleLayout.configTitle("设置手机号");
            sureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = phoneEt.getText().toString();
                    if (Validators.isEmpty(phone)){
                        ToastUtil.toast("手机号不能为空");
                    }else{
                        BPPreferences.instance().putBoolean("isFirst"  , false);
                        BPPreferences.instance().putString("phone" , phone);
                        Intent intent = new Intent();
                        intent.setClass(SavePhoneActivity.this , MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }else{
            Intent intent = new Intent();
            intent.setClass(SavePhoneActivity.this , MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
