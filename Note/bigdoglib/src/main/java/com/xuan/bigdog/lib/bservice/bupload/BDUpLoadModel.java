package com.xuan.bigdog.lib.bservice.bupload;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.xuan.bigapple.lib.asynctask.callback.AsyncTaskFailCallback;
import com.xuan.bigapple.lib.asynctask.callback.AsyncTaskSuccessCallback;
import com.xuan.bigapple.lib.asynctask.helper.Result;
import com.xuan.bigdog.lib.bservice.bcommon.BServiceBaseTask;

import java.util.HashMap;

/**
 * 文件上传
 *
 * Created by xuan on 16/2/16.
 */
public class BDUpLoadModel {
    private static BDUpLoadModel instance;

    private BDUpLoadModel() {

    }

    public static BDUpLoadModel getInstance() {
        if (null == instance) {
            instance = new BDUpLoadModel();
        }
        return instance;
    }

    /**
     * 上传代码
     *
     * @param website
     * @param fileName
     * @param l
     */
    public void doUpload(Context context ,String website, String fileName, final BDUploadListener l){
        String url = website + "common/file/upload.htm";

        UploadTask uploadTask = new UploadTask(context , url);
        uploadTask.setAsyncTaskFailCallback(new AsyncTaskFailCallback<BDUploadData>() {
            @Override
            public void failCallback(Result<BDUploadData> result) {
                l.fail(result.getMessage());
            }
        });

        uploadTask.setAsyncTaskSuccessCallback(new AsyncTaskSuccessCallback<BDUploadData>() {
            @Override
            public void successCallback(Result<BDUploadData> result) {
                l.success(result.getValue().getPath());
            }
        });

        uploadTask.execute(fileName);

    }


    /**上传文件Task
     *
     */
    private class UploadTask extends BServiceBaseTask<BDUploadData> {
        private String url;
        public UploadTask(Context context , String url) {
            super(context);
            this.url = url;
        }

        @Override
        protected Result<BDUploadData> onHttpRequest(Object... params) {
            HashMap<String , String> paramMap = new HashMap<String , String>();
            paramMap.put("file" , (String)params[0]);

            Result<BDUploadData> result = postFile(url , paramMap);
            if (result.isSuccess()){
                BDUploadData retData = JSON.parseObject(result.getMessage() , BDUploadData.class);
                result.setMessage(retData.getMessage());
                if (retData.codeOk()){
                    result.setValue(retData.getResult());
                }else{
                    result.setSuccess(false);
                }
            }
            return result;
        }
    }
}
