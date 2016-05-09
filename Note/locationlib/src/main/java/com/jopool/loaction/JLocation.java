package com.jopool.loaction;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 百度定位
 * Created by wuhk on 2016/3/8.
 */
public class JLocation {
    private static LocationClient locationClient;


    /**初始化locationClient
     *
     * @param application
     * @param l
     */
    public static void init (Context application , BDLocationListener l){
        if (null != locationClient){
            locationClient.stop();
            locationClient = null;
        }

        locationClient = new LocationClient(application);
        locationClient.registerLocationListener(l);
    }

    /**配置定位SDK参数
     *
     */
    private static void configLocOption(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 高精度
        option.setCoorType("bd09ll");// gcj02：国测局加密坐标值，bd09ll:百度加密坐标值，bd09：百度加密墨卡托坐标
        option.setIsNeedAddress(true);// 是否需要反地理编码
        locationClient.setLocOption(option);
    }

    /**
     * 开始定位
     */
    public static void start() {
        checkNull();
        configLocOption();
        locationClient.start();
    }

    /**
     * 结束定位
     */
    public static void stop() {
        if (null != locationClient) {
            locationClient.stop();
            locationClient = null;
        }
    }


    // 检查空
    private static void checkNull() {
        if (null == locationClient) {
            throw new NullPointerException("Call BGLocationUtils.init fisrt.");
        }
    }



}
