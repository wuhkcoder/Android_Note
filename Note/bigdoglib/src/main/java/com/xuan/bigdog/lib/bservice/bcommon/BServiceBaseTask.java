package com.xuan.bigdog.lib.bservice.bcommon;

import android.app.ProgressDialog;
import android.content.Context;

import com.squareup.okhttp.Response;
import com.xuan.bigapple.lib.asynctask.AbstractTask;
import com.xuan.bigapple.lib.asynctask.callback.AsyncTaskResultNullCallback;
import com.xuan.bigapple.lib.asynctask.helper.Result;
import com.xuan.bigapple.lib.utils.ContextUtils;
import com.xuan.bigapple.lib.utils.ToastUtils;
import com.xuan.bigdog.lib.dialog.DGProgressDialog;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by wuhk on 2016/2/23.
 */
public abstract class BServiceBaseTask<T> extends AbstractTask<T> {
    public BServiceBaseTask(Context context) {
        super(context);
        initTask();
    }

    // 初始化一些自定义的异步加载属性
    private void initTask() {

        setProgressDialog(new DGProgressDialog(context, getProgressTip()));

        setAsyncTaskResultNullCallback(new AsyncTaskResultNullCallback() {
            @Override
            public void resultNullCallback() {
                if (!ContextUtils.hasNetwork()) {
                    ToastUtils.displayTextShort("亲~ 没有网络连接");
                }
            }
        });
    }


    /**
     * okHttpPost提交(无通用参数)
     *
     * @param url
     * @param postParam
     * @return
     */
    protected Result<T> okHttpPost(String url, Map<String, String> postParam) {
        Result<T> result = new Result<T>();
        BOkHttpClientManager.Param [] params = new BOkHttpClientManager.Param[postParam.size()];
        int i = 0 ;
        for (Map.Entry<String , String> entry : postParam.entrySet()){
            params[i] = new BOkHttpClientManager.Param(entry.getKey() , entry.getValue());
            i++;
        }
        Response response = null;
        try {
            response = BOkHttpClientManager.getInstance().getPostDelegate().post(url,params);
            if (response.isSuccessful()){
                result.setSuccess(true);
                result.setMessage(response.body().string());
            }else{
                result.setSuccess(false);
                result.setMessage(response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * okHttpPost提交文件(无通用参数)
     *
     * @param url
     * @param postParam
     * @return
     */
    protected Result<T> postFile(String url, Map<String, String> postParam) {
        Result<T> result = new Result<T>();
        File files = new File(postParam.get("file"));
        String fileKeys = "file";
        try {
            Response response = BOkHttpClientManager.getInstance().getUploadDelegate().post(url, fileKeys, files);
            if (response.isSuccessful()){
                result.setSuccess(true);
                try {
                    result.setMessage(response.body().string());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }else {
                result.setSuccess(false);
                result.setMessage(response.message());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected Result<T> doHttpRequest(Object... objects) {
        // 访问前先判断是否有网络
        if (!ContextUtils.hasNetwork()) {
            return null;
        }
        // 真正的网络操作
        return onHttpRequest(objects);
    }

    protected abstract Result<T> onHttpRequest(Object... params);

    /**
     * 加载中的提示语
     *
     * @return
     */
    protected String getProgressTip() {
        return "请稍后...";
    }

}
