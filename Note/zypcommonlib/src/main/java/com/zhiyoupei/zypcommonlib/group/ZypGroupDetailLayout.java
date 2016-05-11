package com.zhiyoupei.zypcommonlib.group;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyoupei.zypcommonlib.R;
import com.zhiyoupei.zypcommonlib.data.ZypGroupData;
import com.zhiyoupei.zypcommonlib.zypviews.ZypRoundImageView;

/**
 * 组团详情界面
 * Created by wuhk on 2016/4/27.
 */
public class ZypGroupDetailLayout extends LinearLayout{
    private ZypRoundImageView zypPicIv;
    private TextView zypTitleTv;
    private TextView zypStatusTv;
    private TextView zypTimeTv;
    private ListView zypListView;
    private LinearLayout scanlayout;
    private ZypMasterAdapter zypMasterAdapter;
    private RelativeLayout bottomLayout;
    private OnClickListener onClickListener;//扫描点击
    private ZypMasterItemClickCallback zypMasterItemClickCallback;//master列表点击
    private ZypInitImageCallback zypInitImageCallback;//显示图片

    public ZypGroupDetailLayout(Context context) {
        super(context);
        zypInit();
    }

    public ZypGroupDetailLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        zypInit();
    }

    private void zypInit(){
        inflate(getContext() , R.layout.zyp_layout_group_detail , this);

        zypPicIv = (ZypRoundImageView)findViewById(R.id.zypPicIv);
        zypPicIv.setType(ZypRoundImageView.TYPE_ROUND);
        zypTitleTv = (TextView)findViewById(R.id.zypTitleTv);
        zypStatusTv = (TextView)findViewById(R.id.zypStatusTv);
        zypTimeTv = (TextView)findViewById(R.id.zypTimeTv);
        zypListView = (ListView)findViewById(R.id.zypListView);
        scanlayout = (LinearLayout)findViewById(R.id.scanLayout);
        bottomLayout = (RelativeLayout)findViewById(R.id.bottomLayout);
    }


    /**设置点击事件
     *
     * @param onClickListener
     */
    public void setClick(OnClickListener onClickListener , ZypMasterItemClickCallback zypMasterItemClickCallback , ZypInitImageCallback zypInitImageCallback) {
        this.onClickListener = onClickListener;
        this.zypMasterItemClickCallback = zypMasterItemClickCallback;
        this.zypInitImageCallback = zypInitImageCallback;
    }


    public void setClick(ZypMasterItemClickCallback zypMasterItemClickCallback , ZypInitImageCallback zypInitImageCallback) {
        this.zypMasterItemClickCallback = zypMasterItemClickCallback;
        this.zypInitImageCallback = zypInitImageCallback;
    }

    /**绑定数据
     *
     */
    public void bingData(ZypGroupData zypGroupData){
        if (null == onClickListener){
            bottomLayout.setVisibility(GONE);
        }else{
            bottomLayout.setVisibility(VISIBLE);
        }
        zypInitImageCallback.initImage(zypPicIv);
        zypTitleTv.setText(zypGroupData.getTitle());
        zypStatusTv.setText(zypGroupData.getStatus());
        zypTimeTv.setText(zypGroupData.getTime() + "出发");
        scanlayout.setOnClickListener(onClickListener);

        zypMasterAdapter = new ZypMasterAdapter(getContext(), zypGroupData.getList(),zypMasterItemClickCallback ,zypGroupData.getType());
        zypListView.setAdapter(zypMasterAdapter);
    }

}
