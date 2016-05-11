package com.zhiyoupei.zypcommonlib.group;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.zhiyoupei.zypcommonlib.R;
import com.zhiyoupei.zypcommonlib.data.ZypMemberData;

/**
 * 成员列表
 * Created by wuhk on 2016/4/27.
 */
public class ZypMemberListLayout extends LinearLayout {
    private ExpandableListView zypListView;
    private Button zypChatBtn;
    private Button zypConfirmBtn;
    private ZypMemberAdapter zypMemberAdapter;
    private ZypContractClickCallback zypContractClickCallback;//合同点击
    private ZypChatCallback zypChatCallback;//聊天点击
    private ZypConfirmCallback zypConfirmCallback;//确认收款点击


    public ZypMemberListLayout(Context context) {
        super(context);
        zypInit();
    }

    public ZypMemberListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        zypInit();
    }

    private void zypInit(){
        inflate(getContext() , R.layout.zyp_layout_member , this);
        zypListView = (ExpandableListView)findViewById(R.id.zypListView);
        zypChatBtn = (Button)findViewById(R.id.zypChatBtn);
        zypConfirmBtn = (Button)findViewById(R.id.zypConfirmBtn);
    }

    /**合同点击
     *
     * @param zypContractClickCallback
     * @param zypChatCallback
     * @param zypConfirmCallback
     */
    public void setClick(ZypContractClickCallback zypContractClickCallback , ZypChatCallback zypChatCallback , ZypConfirmCallback zypConfirmCallback){
        this.zypContractClickCallback = zypContractClickCallback;
        this.zypChatCallback = zypChatCallback;
        this.zypConfirmCallback = zypConfirmCallback;
    }

    /**绑定数据
     *
     * @param zypMemberData
     */
    public void bindData(final ZypMemberData zypMemberData){
        zypMemberAdapter = new ZypMemberAdapter(getContext() , zypMemberData.getZypMembers() , zypContractClickCallback , zypMemberData.getType());
        zypListView.setGroupIndicator(null);
        zypListView.setAdapter(zypMemberAdapter);

        //聊天点击
        zypChatBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                zypChatCallback.chatWithId(zypMemberData.getChatId());
            }
        });

        if(zypMemberData.getPayStatus() == 3){
            //已付款,显示已收
            zypConfirmBtn.setText("已收:" + zypMemberData.getPrice());
        }else if (zypMemberData.getPayStatus() == 1){
            //未付款，需收
            zypConfirmBtn.setText("需收:" + zypMemberData.getPrice());
        }else if (zypMemberData.getPayStatus() == 2){
            //线下付款
            zypConfirmBtn.setText("确认收款:" + zypMemberData.getPrice());
        }

        //确认收款点击
        zypConfirmBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                zypConfirmCallback.confirm(zypMemberData);
            }
        });


    }
}
