package com.zhiyoupei.zypcommonlib.zypviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyoupei.zypcommonlib.R;

import java.util.List;

/**
 * Created by wuhk on 2016/4/12.
 */
public class ZypAuditingView extends RelativeLayout {
    private ImageView imageIv;
    private TextView messageTv;
    private TextView midTipTv;
    private Button addBtn;

    public ZypAuditingView(Context context) {
        super(context);
        zypInit();
    }

    public ZypAuditingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        zypInit();
    }

    private void zypInit(){
        inflate(getContext() , R.layout.zyp_layout_auditing , this);
        imageIv = (ImageView) findViewById(R.id.zypAuditingIv);
        messageTv = (TextView) findViewById(R.id.zypMessageTv);
        midTipTv = (TextView)findViewById(R.id.midTipTv);
        addBtn = (Button)findViewById(R.id.zypAddBtn);
    }

    /**
     * 提示图片
     *
     * @param resid
     * @return
     */
    public ZypAuditingView configImage(int resid) {
        imageIv.setImageResource(resid);
        return this;
    }

    /**
     * 提示消息
     *
     * @param message
     * @return
     */
    public ZypAuditingView configMessage(String message) {
        messageTv.setText(message);
        return this;
    }

    /**按钮文字
     *
     * @param msg
     */
    public ZypAuditingView configBtnText(String msg){
        addBtn.setText(msg);
        return this;
    }

    /**设置中间提示语
     *
     * @param message
     * @return
     */
    public ZypAuditingView configMidTipText(String message){
        midTipTv.setText(message);
        return this;
    }

    /**配置去完善点击事件
     *
     * @param listener
     * @return
     */
    public ZypAuditingView configAddClick(final OnClickListener listener){
        addBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
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
}
