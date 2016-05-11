package com.zhiyoupei.zypcommonlib.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.zhiyoupei.zypcommonlib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 日历控件
 * Created by wuhk on 2015/12/28.
 */
public class ZypCalendarWidget extends LinearLayout {
    private GridView weekGridView;
    private GridView dateGridView;
    private ZypCalendarWeekAdapter calendarWeekAdapter;
    private ZypCalendarDateAdapter calendarDateAdapter;
    private List<ZypCalendarDateEntity> dateList = new ArrayList<ZypCalendarDateEntity>();

    public ZypCalendarWidget(Context context) {
        super(context);
        init();
    }

    public ZypCalendarWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.zyp_widget_calendar, this);
        weekGridView = (GridView)findViewById(R.id.weekGridView);
        dateGridView = (GridView)findViewById(R.id.dateGridView);
        calendarWeekAdapter = new ZypCalendarWeekAdapter(getContext());
        weekGridView.setAdapter(calendarWeekAdapter);
    }

    /**设置日历的日期
     *
     * @param tempList
     */
    public void setDateGridView(List<ZypCalendarDateEntity> tempList){
        dateList.clear();
        dateList.addAll(tempList);
        calendarDateAdapter.notifyDataSetChanged();
    }

    /**设置单选点击的回调
     *
     * @param zypDateSelCallback
     */
    public void setZypDateSelCallback(ZypDateSelCallback zypDateSelCallback){

        if (null == zypDateSelCallback){
            calendarDateAdapter = new ZypCalendarDateAdapter(getContext() , dateList);
        }else{
            calendarDateAdapter = new ZypCalendarDateAdapter(getContext() , dateList ,zypDateSelCallback);
        }
        dateGridView.setAdapter(calendarDateAdapter);
    }

    public void setZypAllSelNOtifyCallback(ZypDateAllSelNotifyCallback zypAllSelNOtifyCallback){

        if (null == zypAllSelNOtifyCallback){
            calendarDateAdapter = new ZypCalendarDateAdapter(getContext() , dateList);
        }else{
            calendarDateAdapter = new ZypCalendarDateAdapter(getContext() , dateList ,zypAllSelNOtifyCallback);
        }
        dateGridView.setAdapter(calendarDateAdapter);
    }



    /**获取选中的日期
     *
     * @return
     */
    public List<String> getMultiSelDate(){
        List<String> multiDateList = new ArrayList<String>();
        for (ZypCalendarDateEntity calendarDateEntity : dateList){
            if (calendarDateEntity.isSelected()){
                multiDateList.add(calendarDateEntity.getDate());
            }
        }
        return multiDateList;
    }
}
