package com.wuhk.note.task;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wuhk.note.common.UrlConstants;
import com.wuhk.note.task.data.WeatherData;
import com.xuan.bigapple.lib.asynctask.helper.Result;

import java.util.HashMap;

/**
 * 获取天气Task
 * Created by wuhk on 2016/5/8.
 */
public class GetWeatherTask extends BaseTask<WeatherData> {

    public GetWeatherTask(Context context) {
        super(context);
    }

    @Override
    protected Result<WeatherData> onHttpRequest(Object... params) {
        HashMap<String ,String> paramMap = new HashMap<>();
        paramMap.put("citykey" , (String)params[0]);

        Result<WeatherData> result = get(UrlConstants.WEATHERURL, paramMap);

        if (result.isSuccess()){
            WeatherData retData = JSON.parseObject(result.getMessage(), WeatherData.class);
            if (!retData.getDesc().equals("OK") || retData.getStatus()!= 1000){
                result.setSuccess(false);
            }else{
                result.setValue(retData);
            }
        }
        return result;
    }
}
