package com.zhiyoupei.zypcommonlib.zypviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyoupei.zypcommonlib.R;

import java.util.List;

/**
 * Created by wuhk on 2016/4/12.
 */
public class ZypNoDataView extends RelativeLayout {
    private ImageView imageIv;
    private TextView messageTv;
    public ZypNoDataView(Context context) {
        super(context);
        zypInit();
    }

    public ZypNoDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        zypInit();
    }

    private void zypInit(){
        inflate(getContext() , R.layout.zyp_layout_nodata , this);
        imageIv = (ImageView) findViewById(R.id.zypNoDataIv);
        messageTv = (TextView) findViewById(R.id.zypNodataTv);
    }

    /**
     * 提示图片
     *
     * @param resid
     * @return
     */
    public ZypNoDataView configImage(int resid) {
        imageIv.setImageResource(resid);
        return this;
    }

    /**
     * 提示消息
     *
     * @param message
     * @return
     */
    public ZypNoDataView configMessage(String message) {
        messageTv.setText(message);
        return this;
    }

    /**
     * 显示
     */
    public void show() {
        setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏
     */
    public void hide() {
        setVisibility(View.GONE);
    }

    /**
     * list空就显示，非空就隐藏
     *
     * @param list
     */
    public void showIfEmpty(List<?> list) {
        if (null == list || list.isEmpty()) {
            show();
        } else {
            hide();
        }
    }
}
