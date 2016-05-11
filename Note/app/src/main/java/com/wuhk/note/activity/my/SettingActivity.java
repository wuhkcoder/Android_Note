package com.wuhk.note.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.wuhk.note.R;
import com.wuhk.note.activity.BaseActivity;
import com.wuhk.note.activity.EncryptActivity;
import com.wuhk.note.adapter.DiaryAdapter;
import com.wuhk.note.utils.ToastUtil;
import com.wuhk.note.view.IVButtonBox;
import com.xuan.bigapple.lib.ioc.InjectView;
import com.xuan.bigapple.lib.utils.sharepreference.BPPreferences;
import com.xuan.bigdog.lib.widgets.title.DGTitleLayout;

/**
 * 设置
 * Created by wuhk on 2016/5/9.
 */
public class SettingActivity extends BaseActivity{

    @InjectView(R.id.titleLayout)
    private DGTitleLayout titleLayout;

    @InjectView(R.id.aboutLayout)
    private LinearLayout aboutLayout;
//
//    @InjectView(R.id.buttonIv)
//    private IVButtonBox buttonTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settting);
        initWidgets();
    }

    private void initWidgets(){

        titleLayout.configTitle("设置").configReturn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SettingActivity.this , AboutActivity.class);
                startActivity(intent);
            }
        });

        boolean isPassed = BPPreferences.instance().getBoolean("isPassed" , false);
//        buttonTv.setChecked(isPassed);

//        buttonTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(SettingActivity.this , EncryptActivity.class);
//                intent.putExtra(DiaryAdapter.MODE , "mutli");
//                if (buttonTv.isChecked()){
//                    ToastUtil.toast("开启");
//                    intent.putExtra(DiaryAdapter.TYPE , "setPass");
//                }else{
//                    ToastUtil.toast("关闭");
//                    intent.putExtra(DiaryAdapter.TYPE , "cancelPass");
//                }
//
//                startActivity(intent);
//            }
//        });
    }

}
