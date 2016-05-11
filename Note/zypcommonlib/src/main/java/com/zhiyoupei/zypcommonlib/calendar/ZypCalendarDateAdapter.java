package com.zhiyoupei.zypcommonlib.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhiyoupei.zypcommonlib.R;

import java.util.List;


/**
 * 日历控件日期适配器
 * Created by wuhk on 2015/12/28.
 */
public class ZypCalendarDateAdapter extends BaseAdapter {
    private Context context;
    private List<ZypCalendarDateEntity> dateList;
    private ZypDateSelCallback zypDateSelCallback;
    private ZypDateAllSelNotifyCallback zypDateAllSelNotifyCallback;

    public ZypCalendarDateAdapter(Context context, List<ZypCalendarDateEntity> dateList , ZypDateAllSelNotifyCallback zypDateAllSelNotifyCallback) {
        this.context = context;
        this.dateList = dateList;
        this.zypDateAllSelNotifyCallback = zypDateAllSelNotifyCallback;
    }

    public ZypCalendarDateAdapter(Context context, List<ZypCalendarDateEntity> dateList , ZypDateSelCallback zypDateSelCallback) {
        this.context = context;
        this.dateList = dateList;
        this.zypDateSelCallback = zypDateSelCallback;
    }

    public ZypCalendarDateAdapter(Context context, List<ZypCalendarDateEntity> dateList) {
        this.context = context;
        this.dateList = dateList;
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
    public int getCount() {
        return dateList.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.zyp_griditem_calendar  , null);
        final ZypCalendarDateEntity calendarDateEntity = dateList.get(position);
        final String date = calendarDateEntity.getDate();
        final TextView contentTv = (TextView)view.findViewById(R.id.contentTv);
        final View selectView = (View)view.findViewById(R.id.selectView);

        if (!date.equals(" ")){
//            if (Integer.parseInt(date) < 10){
//                contentTv.setText("0" + date);
//            }else{
//                contentTv.setText(date);
//            }
            contentTv.setText(date);

            if (calendarDateEntity.isCanClick()){
                if(calendarDateEntity.isSelected()){
                    selectView.setVisibility(View.VISIBLE);
                    contentTv.setTextColor(Color.WHITE);
                }else{
                    selectView.setVisibility(View.GONE);
                    contentTv.setTextColor(Color.parseColor("#333333"));
                }
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dateStr = getCompleteDate(calendarDateEntity.getDate());
                        if (selectView.getVisibility() == View.VISIBLE){
                            selectView.setVisibility(View.GONE);
                            contentTv.setTextColor(Color.parseColor("#333333"));
                            calendarDateEntity.setIsSelected(false);
                        }else{
                            selectView.setVisibility(View.VISIBLE);
                            contentTv.setTextColor(Color.WHITE);
                            calendarDateEntity.setIsSelected(true);
                        }
                        if (null != ZypCalendarLayout.selectedList && !ZypCalendarLayout.selectedList.isEmpty()){
                            boolean isAdd = false;
                            for (String str : ZypCalendarLayout.selectedList){
                                if (str.equals(dateStr)){
                                    ZypCalendarLayout.selectedList.remove(str);
                                    isAdd = false;
                                    break;
                                }else{
                                    isAdd = true;
                                }
                            }
                            if (isAdd){
                                ZypCalendarLayout.selectedList.add(dateStr);
                            }
                        }else{
                            ZypCalendarLayout.selectedList.add(dateStr);
                        }


                        if (null != zypDateSelCallback){
                            zypDateSelCallback.doWithSelDate(contentTv.getText().toString());
                        }
                        if (null != zypDateAllSelNotifyCallback){
                            zypDateAllSelNotifyCallback.judge();
                        }
                    }
                });
            }else{
                contentTv.setTextColor(Color.parseColor("#999999"));
            }

        }
        return view;
    }

    //获取完整的日期
    private String getCompleteDate(String date){
        String month="";
        String day="";
        if (ZypCalendarLayout.mMonth < 10){
           month = "0" + ZypCalendarLayout.mMonth;
        }else{
            month = String.valueOf(ZypCalendarLayout.mMonth);
        }
        if (Integer.parseInt(date) < 10){
            day = "0" + date;
        }else{
            day = date;
        }
        String result = ZypCalendarLayout.mYear + "-" + month + "-" + day;
        return result;
    }
}
