package com.zhiyoupei.zypcommonlib.group;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyoupei.zypcommonlib.R;
import com.zhiyoupei.zypcommonlib.data.ZypMemberData;

import java.util.List;

/**
 * 团成员列表适配器
 * Created by wuhk on 2016/4/27.
 */
public class ZypMemberAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ZypMemberData.ZypMember> dataList;
    private ZypContractClickCallback zypContractClickCallback;
    private int type;

    public ZypMemberAdapter(Context context, List<ZypMemberData.ZypMember> dataList , ZypContractClickCallback zypContractClickCallback , int type) {
        this.context = context;
        this.dataList = dataList;
        this.zypContractClickCallback = zypContractClickCallback;
        this.type = type;
    }

    //获取父item的个数
    @Override
    public int getGroupCount() {
        return dataList.size();
    }

    //获取父item下子item的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return dataList.get(groupPosition).getZypContracts().size();
    }

    //获取当前父item的数据
    @Override
    public Object getGroup(int groupPosition) {
        return dataList.get(groupPosition);
    }

    //得到子item需要关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataList.get(groupPosition).getZypContracts().get(childPosition);
    }

    //获取指定分组的ID
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //获取指定分组的子视图的ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //获取组视图
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (null == convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.zyp_exp_listitem_group , null);
        }
        ImageView masterIv = (ImageView)convertView.findViewById(R.id.masterIv);
        TextView nameTv  = (TextView)convertView.findViewById(R.id.nameTv);
        TextView statusTv  = (TextView)convertView.findViewById(R.id.statusTv);
        TextView phoneTv  = (TextView)convertView.findViewById(R.id.phoneTv);

        ZypMemberData.ZypMember member = dataList.get(groupPosition);
        if (member.isMaster()){
            masterIv.setVisibility(View.VISIBLE);
        }else{
            masterIv.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(member.getPhone())){
            phoneTv.setVisibility(View.VISIBLE);
            phoneTv.setText("("+ member.getPhone() + ")");
        }else{
            phoneTv.setVisibility(View.GONE);
        }

        if (member.getName().length() > 8){
            nameTv.setText(member.getName().substring(0 , 8) + "...");
        }else{
            nameTv.setText(member.getName());
        }

        if (type == 1){
            statusTv.setVisibility(View.VISIBLE);
            //旅行社线路
            if (member.isContractEmpty()){
                statusTv.setTextColor(Color.parseColor("#eb6100"));
                statusTv.setText("合同未关联");
            }else{
                if (member.isSigned()){
                    statusTv.setTextColor(Color.parseColor("#0dade9"));
                    statusTv.setText("合同已签署");
                }else{
                    statusTv.setTextColor(Color.parseColor("#eb6100"));
                    statusTv.setText("合同未签署");
                }
            }
        }else{
            statusTv.setVisibility(View.GONE);
        }


        return convertView;
    }

    //获取子视图
    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (null == convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.zyp_exp_listiem_child , null);
        }
        TextView nameTv = (TextView)convertView.findViewById(R.id.nameTv);
        TextView statusTv = (TextView)convertView.findViewById(R.id.statusTv);

        final ZypMemberData.ZypMember.ZypContract zypContract = dataList.get(groupPosition).getZypContracts().get(childPosition);
        nameTv.setText(zypContract.getName());
        if (zypContract.isSigned()){
            statusTv.setTextColor(Color.parseColor("#0dade9"));
            statusTv.setText("已签署");
        }else{
            statusTv.setTextColor(Color.parseColor("#eb6100"));
            statusTv.setText("去签署");
        }

        //合同点击
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zypContractClickCallback.clickContract(zypContract , dataList.get(groupPosition).getOrderMemberId());
            }
        });
        return convertView;
    }

    // 是否指定分组视图及其子视图的ID对应的后台数据改变也会保持该ID。
    @Override
    public boolean hasStableIds() {
        return true;
    }

    // 指定位置的子视图是否可选择。
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
