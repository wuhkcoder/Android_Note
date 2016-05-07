package com.wuhk.note;

import android.app.Application;

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

    }

    //设置标题背景色
    private void initTitle() {
        DGTitleLayout.UIConfig dgUiConfig = new DGTitleLayout.UIConfig();
        dgUiConfig.DEFAULT_BG_COLOR = R.color.color_theme;
        DGTitleLayout.setUiConfig(dgUiConfig);
    }

}
