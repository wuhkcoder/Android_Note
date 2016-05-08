package com.wuhk.note.task;

import android.content.Context;

import com.wuhk.note.common.UrlConstants;
import com.wuhk.note.utils.HttpUtil;
import com.wuhk.note.utils.ToastUtil;
import com.xuan.bigapple.lib.asynctask.AbstractTask;
import com.xuan.bigapple.lib.asynctask.callback.AsyncTaskResultNullCallback;
import com.xuan.bigapple.lib.asynctask.helper.Result;
import com.xuan.bigapple.lib.http.BPResponse;
import com.xuan.bigapple.lib.utils.ContextUtils;
import com.xuan.bigapple.lib.utils.ToastUtils;
import com.xuan.bigdog.lib.dialog.DGProgressDialog;

import java.util.Map;

/**Task基类
 * Created by wuhk on 2016/5/8.
 */
public abstract class BaseTask<T> extends AbstractTask<T> {
    public BaseTask(Context context) {
        super(context);
        initTask();
    }

    // 初始化一些自定义的异步加载属性
    private void initTask() {
        setProgressDialog(new DGProgressDialog(context, getProgressTip()));
        // 提示进度条
        setAsyncTaskResultNullCallback(new AsyncTaskResultNullCallback() {
            @Override
            public void resultNullCallback() {
                if (!ContextUtils.hasNetwork()) {
                    ToastUtils.displayTextShort("亲~ 没有网络连接");
                }
            }
        });
    }

    @Override
    protected Result<T> doHttpRequest(Object... params) {
        // 访问前先判断是否有网络
        if (!ContextUtils.hasNetwork()) {
            return null;
        }

        // 真正的网络操作
        return onHttpRequest(params);

    }

    /**
     * post提交(无通用参数)
     *
     * @param url
     * @param postParam
     * @return
     */
    protected Result<T> post(String url, Map<String, String> postParam) {
        BPResponse response = HttpUtil.post(context, url, postParam);
        Result<T> result = new Result<T>();
        if (-1 == response.getStatusCode()) {
            result.setSuccess(false);
            result.setMessage(response.getReasonPhrase());
        } else if (200 == response.getStatusCode()) {
            // 正常200
            result.setSuccess(true);
            result.setMessage(response.getResultStr());
        } else {
            // 非200的状态码
            result.setSuccess(false);
            result.setMessage("返回状态码错误" + response.toString());
        }
        return result;
    }


    /**
     * post提交(无通用参数)
     *
     * @param url
     * @param postParam
     * @return
     */
    protected Result<T> get(String url, Map<String, String> postParam) {
        BPResponse response = HttpUtil.get(context, url, postParam);
        Result<T> result = new Result<T>();
        if (-1 == response.getStatusCode()) {
            result.setSuccess(false);
            result.setMessage(response.getReasonPhrase());
        } else if (200 == response.getStatusCode()) {
            // 正常200
            result.setSuccess(true);
            result.setMessage(response.getResultStr());
        } else {
            // 非200的状态码
            result.setSuccess(false);
            result.setMessage("返回状态码错误" + response.toString());
        }
        return result;
    }

    /**
     * 加载中的提示语
     *
     * @return
     */
    protected String getProgressTip() {
        return "请稍后...";
    }

    protected abstract Result<T> onHttpRequest(Object... params);
}
