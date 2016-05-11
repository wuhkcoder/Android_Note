package com.zhiyoupei.zypcommonlib.listener;

import android.widget.RelativeLayout;

/**
 * Created by wuhk on 2016/4/12.
 */
public interface ZypGetCodeNextClickListener {
    void onClick(String phone, String code , String type , RelativeLayout loginLayout , RelativeLayout registerLayout , RelativeLayout setPassLayout);
}
