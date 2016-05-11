package com.zhiyoupei.zypcommonlib.account.change;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyoupei.zypcommonlib.R;
import com.zhiyoupei.zypcommonlib.account.apply.ZypBaseAccount;

import java.util.List;

/**
 * 更改提现方式适配器
 * Created by wuhk on 2016/5/4.
 */
public class ZypChangeAccountAdapter extends BaseAdapter {
    private Context context;
    private List<ZypBaseAccount>  dataList;
    private ZypAddAccountCallback zypAddAccountCallback;
    private ZypChangeAccountCallback zypChangeAccountCallback;
    private ZypAccountLongClickCallback zypAccountLongClickCallback;


    public ZypChangeAccountAdapter(Context context, List<ZypBaseAccount> dataList , ZypAddAccountCallback zypAddAccountCallback
    ,ZypChangeAccountCallback zypChangeAccountCallback , ZypAccountLongClickCallback zypAccountLongClickCallback) {
        this.context = context;
        this.dataList = dataList;
        this.zypAddAccountCallback = zypAddAccountCallback;
        this.zypChangeAccountCallback = zypChangeAccountCallback;
        this.zypAccountLongClickCallback = zypAccountLongClickCallback;
    }


    @Override
    public int getCount() {
        return dataList.size() + 1;
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
            if (position == dataList.size()){
                view = LayoutInflater.from(context).inflate(R.layout.zyp_listitem_add_account , null);
            }else{
                view = LayoutInflater.from(context).inflate(R.layout.zyp_listitem_change_account , null);
            }
        }

        if(position == dataList.size()){
            RelativeLayout addAccountLayout = (RelativeLayout)view.findViewById(R.id.addAccountLayout);

            addAccountLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    zypAddAccountCallback.goToAdd();
                }
            });
        }else{
            ImageView iconIv = (ImageView)view.findViewById(R.id.iconIv);
            TextView nameTv = (TextView)view.findViewById(R.id.nameTv);
            TextView accountTv = (TextView)view.findViewById(R.id.accountTv);
            ImageView selectIv = (ImageView)view.findViewById(R.id.selectIv);
            TextView bankNameTv = (TextView)view.findViewById(R.id.bankNameTv);

            final ZypBaseAccount zypBaseAccount = dataList.get(position);

            if (zypBaseAccount.getType() == 1){
                iconIv.setImageResource(R.drawable.zyp_alipay);
                bankNameTv.setVisibility(View.GONE);
            }else{
                iconIv.setImageResource(R.drawable.zyp_bank);
                bankNameTv.setVisibility(View.VISIBLE);
                bankNameTv.setText("(" + zypBaseAccount.getBankName() +")");
            }

            nameTv.setText(zypBaseAccount.getName());
            accountTv.setText(zypBaseAccount.getAccount());

            if (zypBaseAccount.isSelected()){
                selectIv.setVisibility(View.VISIBLE);
            }else{
                selectIv.setVisibility(View.GONE);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (zypBaseAccount.isSelected()){
                        zypBaseAccount.setSelected(false);
                    }else{
                        zypBaseAccount.setSelected(true);
                    }
                    zypChangeAccountCallback.changeAccount(zypBaseAccount);
                    notifyDataSetChanged();
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (!zypBaseAccount.isSelected()){
                        zypAccountLongClickCallback.longClick(zypBaseAccount);
                    }
                    return true;
                }
            });
        }


        return view;
    }
}
