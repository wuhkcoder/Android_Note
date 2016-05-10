package com.wuhk.note;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;
import com.wuhk.note.common.Constants;
import com.xuan.bigapple.lib.Bigapple;
import com.xuan.bigapple.lib.bitmap.BPBitmapLoader;
import com.xuan.bigapple.lib.db.DBHelper;
import com.xuan.bigappleui.lib.BigappleUI;
import com.xuan.bigdog.lib.widgets.title.DGTitleLayout;

public class App extends Application {

    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initTitle();
        Bigapple.init(this);
        BigappleUI.init(this);
        DBHelper.init(Constants.DB_VERSION, Constants.DB_NAME, this);
        BPBitmapLoader.init(this);

        // 应用程序入口处调用，避免手机内存过小，杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 如在Application中调用初始化，需要在Mainifest中注册该Applicaiton
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用半角“,”分隔。
        // 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误

        SpeechUtility.createUtility(App.this, "appid=" + Constants.XFAPPID);

        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
        // Setting.setShowLog(false);
        super.onCreate();

    }

    //设置标题背景色
    private void initTitle() {
        DGTitleLayout.UIConfig dgUiConfig = new DGTitleLayout.UIConfig();
        dgUiConfig.DEFAULT_BG_COLOR = R.color.color_theme;
        DGTitleLayout.setUiConfig(dgUiConfig);
    }

}
