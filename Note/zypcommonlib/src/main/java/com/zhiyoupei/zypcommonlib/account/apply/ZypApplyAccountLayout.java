package com.zhiyoupei.zypcommonlib.account.apply;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiyoupei.zypcommonlib.R;

/**
 * 申请提现
 * Created by wuhk on 2016/5/4.
 */
public class ZypApplyAccountLayout extends LinearLayout{
    private LinearLayout changeApplyLayout;
    private ImageView iconIv;
    private TextView nameTv;
    private TextView accountTv;
    private EditText applyMoneyEt;
    private Button sureBtn;
    private ZypApplySureCallback zypApplySureCallback;
    private ZypApplyChangeCallback zypApplyChangeCallback;
    private ZypBaseAccount baseAccount;
    private TextView bankNameTv;

    public ZypApplyAccountLayout(Context context) {
        super(context);
        zypInit();
    }

    public ZypApplyAccountLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        zypInit();
    }

    private void zypInit(){
        inflate(getContext() , R.layout.zyp_layout_apply_account , this);
        changeApplyLayout = (LinearLayout)findViewById(R.id.changeApplyLayout);
        iconIv = (ImageView)findViewById(R.id.iconIv);
        nameTv = (TextView)findViewById(R.id.nameTv);
        accountTv = (TextView)findViewById(R.id.accountTv);
        applyMoneyEt = (EditText)findViewById(R.id.applyMoneyEt);
        sureBtn = (Button)findViewById(R.id.sureBtn);
        bankNameTv = (TextView)findViewById(R.id.bankNameTv);
    }

    public void init(final ZypBaseAccount zypBaseAccount , ZypApplyInitCallback applyInitCallback , final ZypApplySureCallback applySureCallback
    ,ZypApplyChangeCallback applyChangeCallback){
        this.baseAccount = zypBaseAccount;
        this.zypApplySureCallback = applySureCallback;
        this.zypApplyChangeCallback = applyChangeCallback;
        if (null == baseAccount){
            applyInitCallback.doWhenWEmpty();
        }else{
            if (baseAccount.getType() == 1){
                //支付宝
                iconIv.setImageResource(R.drawable.zyp_alipay);
                bankNameTv.setVisibility(VISIBLE);
            }else if (baseAccount.getType() == 2){
                //银联
                iconIv.setImageResource(R.drawable.zyp_bank);
                bankNameTv.setVisibility(VISIBLE);
                bankNameTv.setText("(" + baseAccount.getBankName() + ")");
            }
            nameTv.setText(baseAccount.getName());
            accountTv.setText(baseAccount.getAccount());

            changeApplyLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    zypApplyChangeCallback.change();
                }
            });
            //确定
            sureBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String money = applyMoneyEt.getText().toString();
                    zypApplySureCallback.sure(baseAccount , money);
                }
            });
        }
    }

    //更改提现方式
    public void setApplyAccount(ZypBaseAccount zypBaseAccount){
        this.baseAccount = zypBaseAccount;
    }
}
