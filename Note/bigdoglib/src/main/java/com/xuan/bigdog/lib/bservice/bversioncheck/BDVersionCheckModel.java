package com.xuan.bigdog.lib.bservice.bversioncheck;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.xuan.bigapple.lib.utils.log.LogUtils;
import com.xuan.bigdog.lib.update.UpdateHelper;
import com.xuan.bigdog.lib.update.core.UpdateConfig;

/**
 * 版本监测
 *
 * Created by xuan on 16/2/16.
 */
public class BDVersionCheckModel {
    private static BDVersionCheckModel instance;

    private BDVersionCheckModel() {

    }

    public static BDVersionCheckModel getInstance() {
        if (null == instance) {
            instance = new BDVersionCheckModel();
        }
        return instance;
    }

    /**
     * 检查版本更新
     *
     * @param context
     * @param checkUrl
     * @param apkSaveFilePath
     */
    public void doCheck(Context context, String checkUrl, String apkSaveFilePath){
        UpdateConfig config = new UpdateConfig();
        config.setCheckUrl(checkUrl);
        config.setSaveFileName(apkSaveFilePath);
        config.setPasreUpdateInfoHandler(new BDVersionCheckPasreHandler());

        UpdateHelper updateHelper = new UpdateHelper(context, config);
        updateHelper.check();
    }

    /**
     * 检查版本更新,自动设置参数
     *
     * @param context
     * @param checkUrl
     * @param apkSaveFilePath
     */
    public void doCheckDefault(Context context, String checkUrl, String apkSaveFilePath){
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String completeUrl = checkUrl + "?version="+packageInfo.versionCode+"&os=ANDROID&appId="+packageInfo.packageName;
            UpdateConfig config = new UpdateConfig();
            config.setCheckUrl(completeUrl);
            config.setSaveFileName(apkSaveFilePath);
            config.setPasreUpdateInfoHandler(new BDVersionCheckPasreHandler());

            UpdateHelper updateHelper = new UpdateHelper(context, config);
            updateHelper.check();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
