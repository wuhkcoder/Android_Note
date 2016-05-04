package com.wuhk.note.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wuhk.note.utils.ActivityManager;

/**
 * Activity基类
 * Created by wuhk on 2016/5/4.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }
}
