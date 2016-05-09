package com.wuhk.note.activity;

import android.content.Intent;
import android.os.Bundle;

import com.wuhk.note.adapter.DiaryAdapter;
import com.xuan.bigapple.lib.utils.sharepreference.BPPreferences;

/**
 * 检查是否加密
 * Created by wuhk on 2016/5/9.
 */
public class CheckPassActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isPassed = BPPreferences.instance().getBoolean("isPassed" , false);
        if (isPassed){
            Intent intent = new Intent();
            intent.setClass(CheckPassActivity.this , EncryptActivity.class);
            intent.putExtra(DiaryAdapter.TYPE , "inputPass");
            intent.putExtra(DiaryAdapter.MODE , "mutli");
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent();
            intent.setClass(CheckPassActivity.this , MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
