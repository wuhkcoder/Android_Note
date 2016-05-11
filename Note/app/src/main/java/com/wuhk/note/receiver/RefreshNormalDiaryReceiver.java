package com.wuhk.note.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.wuhk.note.App;

/**
 * Created by wuhk on 2016/5/11.
 */
public abstract class RefreshNormalDiaryReceiver extends BroadcastReceiver{
    public static final String ACTION = RefreshNormalDiaryReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean loadEncrypt = intent.getBooleanExtra("loadType" , false);
        refreshDiary(loadEncrypt);
    }

    /**执行相应操作
     *
     */
    public abstract void refreshDiary(boolean loadEncrypt);
    /**
     * 注册广播
     */
    public void register(){
        App.instance.registerReceiver(this , new IntentFilter(ACTION));
    }

    /**
     * 取消广播
     */
    public void unRegister(){
        App.instance.unregisterReceiver(this);
    }

    /**
     * 发送广播
     */
    public static void notifyReceiver(boolean loadEncrypt){
        Intent intent = new Intent(ACTION);
        intent.putExtra("loadType" , loadEncrypt);
        App.instance.sendBroadcast(intent);
    }
}
