package com.wuhk.note.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.wuhk.note.App;

/**
 * Created by wuhk on 2016/5/12.
 */
public abstract class DiaryLoadByDateReceiver extends BroadcastReceiver {
    public static final String ACTION = DiaryLoadByDateReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String time = intent.getStringExtra("date");
        refreByDate(time);
    }

    /**执行相应操作
     *
     */
    public abstract void refreByDate(String time);
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
    public static void notifyReceiver(String time){
        Intent intent = new Intent(ACTION);
        intent.putExtra("date" , time);
        App.instance.sendBroadcast(intent);
    }
}
