package com.zhiyoupei.zypcommonlib.group;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyoupei.zypcommonlib.R;
import com.zhiyoupei.zypcommonlib.data.ZypGroupData;

import java.util.List;

/**
 * 组团详情master列表适配器
 * Created by wuhk on 2016/4/27.
 */
public class ZypMasterAdapter extends BaseAdapter {
    private Context context;
    private List<ZypGroupData.ZypMaster> dataList;
    private ZypMasterItemClickCallback zypMasterItemClickCallback;
    private int type;

    public ZypMasterAdapter(Context context, List<ZypGroupData.ZypMaster> dataList , ZypMasterItemClickCallback zypMasterItemClickCallback , int type) {
        this.context = context;
        this.dataList = dataList;
        this.zypMasterItemClickCallback = zypMasterItemClickCallback;
        this.type = type;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (null == view){
            view = LayoutInflater.from(context).inflate(R.layout.zyp_listitem_master , null);
        }
        TextView masterTv = (TextView)view.findViewById(R.id.zypMasterTv);
        TextView moneyTv = (TextView)view.findViewById(R.id.zypMoneyTv);
        TextView signTv = (TextView)view.findViewById(R.id.zypSignTv);
        ImageView signFlagIv = (ImageView) view.findViewById(R.id.zypSignFlagIv);

        final ZypGroupData.ZypMaster master = dataList.get(position);

        if (master.getName().length() > 8){
            masterTv.setText(master.getName().substring(0 , 8) + "...");
        }else{
            masterTv.setText(master.getName());
        }
        if (master.getPayStatus() == 3){
            moneyTv.setText("已收:" + master.getPrice());
        }else{
            moneyTv.setText("需收:" + master.getPrice());
        }
        if(type == 1){
            //旅行社线路
            signTv.setVisibility(View.VISIBLE);
            if (master.isSigned()){
                //合同已签署
                signTv.setText("合同已签署");
                signTv.setTextColor(Color.parseColor("#0dade9"));
                signFlagIv.setImageResource(R.drawable.zyp_sign_arrow);
            }else{
                //合同未签署
                signTv.setText("合同未完成");
                signTv.setTextColor(Color.parseColor("#eb6100"));
                signFlagIv.setImageResource(R.drawable.zyp_no_sign_arrow);
            }
        }else{
            signTv.setVisibility(View.GONE);
            signFlagIv.setImageResource(R.drawable.zyp_no_contract_arrow);
        }


        //点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zypMasterItemClickCallback.masterCLickWithId(master.getMasterId() , master.getOrderId());
            }
        });

        return view;
    }
}
