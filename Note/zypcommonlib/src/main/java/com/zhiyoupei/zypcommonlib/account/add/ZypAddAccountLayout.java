package com.zhiyoupei.zypcommonlib.account.add;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyoupei.zypcommonlib.R;

/**
 * 增加账号
 * Created by wuhk on 2016/5/9.
 */
public class ZypAddAccountLayout extends LinearLayout {
    private RelativeLayout selectLayout;//选择
    private LinearLayout aliLayout;
    private LinearLayout bankLayout;

    private TextView accountTv;
    private EditText aliAccountEt;
    private EditText aliNameEt;
    private EditText bankCardEt;
    private EditText bankNameEt;
    private EditText bankPeopleEt;

    public TextView getAccountTv() {
        return accountTv;
    }

    public void setAccountTv(TextView accountTv) {
        this.accountTv = accountTv;
    }

    public LinearLayout getAliLayout() {
        return aliLayout;
    }

    public LinearLayout getBankLayout() {
        return bankLayout;
    }

    public EditText getAliAccountEt() {
        return aliAccountEt;
    }

    public EditText getAliNameEt() {
        return aliNameEt;
    }

    public EditText getBankCardEt() {
        return bankCardEt;
    }

    public EditText getBankNameEt() {
        return bankNameEt;
    }

    public EditText getBankPeopleEt() {
        return bankPeopleEt;
    }

    private ZypSelectAccountCallback zypSelectAccountCallback;

    public ZypAddAccountLayout(Context context) {
        super(context);
        zypInit();
    }

    public ZypAddAccountLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        zypInit();
    }

    private void zypInit(){
        inflate(getContext() , R.layout.zyp_layout_add_account , this);
        selectLayout = (RelativeLayout)findViewById(R.id.selectLayout);
        aliLayout = (LinearLayout)findViewById(R.id.aliLayout);
        bankLayout = (LinearLayout)findViewById(R.id.bankLayout);

        accountTv = (TextView)findViewById(R.id.accountTv);
        aliAccountEt = (EditText)findViewById(R.id.aliAccountEt);
        aliNameEt = (EditText)findViewById(R.id.aliNameEt);
        bankCardEt = (EditText)findViewById(R.id.bankCardEt);
        bankNameEt = (EditText)findViewById(R.id.bankNameEt);
        bankPeopleEt = (EditText)findViewById(R.id.bankPeopleEt);
    }

    public void init(final ZypSelectAccountCallback zypSelectAccountCallback){
        this.zypSelectAccountCallback = zypSelectAccountCallback;

        selectLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                zypSelectAccountCallback.select();
            }
        });
    }




}
