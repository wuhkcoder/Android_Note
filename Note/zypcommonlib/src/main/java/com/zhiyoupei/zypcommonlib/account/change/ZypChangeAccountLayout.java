package com.zhiyoupei.zypcommonlib.account.change;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.zhiyoupei.zypcommonlib.R;
import com.zhiyoupei.zypcommonlib.account.apply.ZypBaseAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * 更改提现方式
 * Created by wuhk on 2016/5/4.
 */
public class ZypChangeAccountLayout extends LinearLayout {
    private ListView listView;

    public ZypChangeAccountLayout(Context context) {
        super(context);
        zypInit();
    }

    public ZypChangeAccountLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        zypInit();
    }

    private void zypInit() {
        inflate(getContext(), R.layout.zyp_layout_change_account, this);
        listView = (ListView)findViewById(R.id.listView);
    }

    public void init(List<ZypBaseAccount> dataList , ZypAddAccountCallback zypAddAccountCallback , ZypChangeAccountCallback zypChangeAccountCallback ,
                     ZypAccountLongClickCallback zypAccountLongClickCallback){

        ZypChangeAccountAdapter zypChangeAccountAdapter = new ZypChangeAccountAdapter(getContext() , dataList , zypAddAccountCallback , zypChangeAccountCallback ,
                zypAccountLongClickCallback);
        listView.setAdapter(zypChangeAccountAdapter);


    }
}
