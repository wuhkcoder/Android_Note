package com.wuhk.note.activity;

import android.content.Intent;
import android.os.Bundle;

import com.wuhk.note.adapter.DiaryAdapter;
import com.xuan.bigapple.lib.ioc.app.BPActivity;
import com.xuan.bigapple.lib.utils.ContextUtils;
import com.xuan.bigapple.lib.utils.sharepreference.BPPreferences;
import com.xuan.bigdog.lib.utils.BDActivityManager;

/**
 * 所有Activity的基类
 * Created by wuhk on 2016/5/5.
 */
public class BaseActivity extends BPActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BDActivityManager.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BDActivityManager.removeActivity(this);
    }
}
