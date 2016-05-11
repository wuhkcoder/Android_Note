package com.zhiyoupei.zypcommonlib.zypviews;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.zhiyoupei.zypcommonlib.R;

/**
 * Created by wuhk on 2016/4/26.
 */
public class ZypCheckBox extends Button {
    private boolean isChecked;
    private OnClickListener externalOnclickListener;

    public ZypCheckBox(Context context) {
        super(context);
        zypInit();
    }

    public ZypCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        zypInit();
    }

    public ZypCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        zypInit();
    }

    private void zypInit(){
        setGravity(Gravity.CENTER);
        setTextColor(Color.parseColor("#333333"));
        setBackgroundResource(R.drawable.zyp_calendar_item_not_sel);
        setText("全选");
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked){
                    setTextColor(Color.parseColor("#333333"));
                    setBackgroundResource(R.drawable.zyp_calendar_item_not_sel);
                }else{
                    setTextColor(Color.parseColor("#ffffff"));
                    setBackgroundResource(R.drawable.zyp_calendar_item_sel_shape);
                }

                isChecked = !isChecked;
                if (null != externalOnclickListener){
                    externalOnclickListener.onClick(v);
                }

            }
        });
    }

    public boolean isChecked(){
        return isChecked;
    }

    public void setChecked(boolean checked){
        if (checked){
            setTextColor(Color.parseColor("#ffffff"));
            setBackgroundResource(R.drawable.zyp_calendar_item_sel_shape);
        }else{
            setTextColor(Color.parseColor("#333333"));
            setBackgroundResource(R.drawable.zyp_calendar_item_not_sel);
        }

        this.isChecked = checked;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        this.externalOnclickListener = l;
    }

    public void toggle() {
        setChecked(!isChecked);
    }
}
