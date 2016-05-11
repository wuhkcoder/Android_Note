package com.zhiyoupei.zypcommonlib.calendar;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhiyoupei.zypcommonlib.R;
import com.zhiyoupei.zypcommonlib.wheelview.OnWheelScrollListener;
import com.zhiyoupei.zypcommonlib.wheelview.WheelView;
import com.zhiyoupei.zypcommonlib.wheelview.adapter.NumericWheelAdapter;
import com.zhiyoupei.zypcommonlib.zypviews.ZypCheckBox;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wuhk on 2016/4/14.
 */
public class ZypCalendarLayout extends RelativeLayout {
    private TextView timeDesTv;
    private TextView timeSelTv;
    private RelativeLayout desLayout;
    private ZypCalendarWidget calendarWidget;
    private RelativeLayout timePickerLayout;
    private WheelView yearWheel;
    private WheelView monthWheel;
    private TextView sureTv;
    private View remainLayout;
    private ZypCheckBox allSelBtn;
    private ZypDateSelCallback zypDateSelCallback;

    public static int mYear;//显示的年
    public static int mMonth;//显示的月

    private int curYear;//当前年
    private int curMonth;//当前月
    private int curDay;//当前日

    private List<ZypCalendarDateEntity> dataList = new ArrayList<ZypCalendarDateEntity>();//天数
    private List<ZypCalendarDateEntity> tempList = new ArrayList<ZypCalendarDateEntity>();//选中的天数
    public static List<String> selectedList  = new ArrayList<String>();//已选中的日期
    private List<String> removeList = new ArrayList<String>();

    public ZypCalendarLayout(Context context) {
        super(context);
        zypInit();
    }

    public ZypCalendarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        zypInit();
    }

    private void zypInit(){
        inflate(getContext() , R.layout.zyp_layout_calendar , this);
        timeDesTv = (TextView)findViewById(R.id.timeDesTv);
        timeSelTv = (TextView)findViewById(R.id.timeSelTv);
        desLayout = (RelativeLayout)findViewById(R.id.desLayout);
        calendarWidget = (ZypCalendarWidget)findViewById(R.id.calendarWidget);
        timePickerLayout = (RelativeLayout)findViewById(R.id.timePickerLayout);
        yearWheel = (WheelView)findViewById(R.id.yearWheel);
        monthWheel =  (WheelView)findViewById(R.id.monthWheel);
        sureTv = (TextView)findViewById(R.id.sureTv);
        allSelBtn = (ZypCheckBox)findViewById(R.id.allSelBtn);
        remainLayout = (View)findViewById(R.id.remainLayout);


        desLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerLayout.setVisibility(VISIBLE);
            }
        });

        remainLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerLayout.setVisibility(GONE);
            }
        });
    }


    /**设置单选点击事件的回调
     *
     * @param zypDateSelCallback
     */
    public void init(ZypDateSelCallback zypDateSelCallback){
        allSelBtn.setVisibility(GONE);
        selectedList.clear();
        this.zypDateSelCallback = zypDateSelCallback;
        if (null != zypDateSelCallback){
            calendarWidget.setZypDateSelCallback(zypDateSelCallback);
        }else{
            calendarWidget.setZypDateSelCallback(null);
        }
        initDate();
        initWheelSelect();
    }

    public void init(List<String> strList){
        allSelBtn.setVisibility(VISIBLE);
        selectedList.clear();
        selectedList = strList;
        calendarWidget.setZypDateSelCallback(null);
        calendarWidget.setZypAllSelNOtifyCallback(new ZypDateAllSelNotifyCallback() {
            @Override
            public void judge() {
                judgeAllSel();
            }
        });
        initDate();
        initWheelSelect();
    }

    private void initDate(){
//        selectedList.clear();
        //初始化年月
        Calendar curCalendar = Calendar.getInstance();
        curCalendar.setTime(new Date());
        curYear = curCalendar.get(Calendar.YEAR);
        curMonth  = curCalendar.get(Calendar.MONTH) + 1;
        curDay = curCalendar.get(Calendar.DAY_OF_MONTH);
        mYear = curYear;
        mMonth  = curMonth;
        timeSelTv.setText(getCurTimeDes());
        setData(selectedList);

        allSelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(mYear, mMonth-1, 1);
                int num = dayAmountOfMonth(calendar);
                removeList.clear();
                for (int i = 0 ; i < selectedList.size() ; i++){
                    String time = selectedList.get(i);
                    if (time.indexOf(getSelTime()) != -1){
                        removeList.add(selectedList.get(i));
                    }
                }
                selectedList.removeAll(removeList);


                if (allSelBtn.isChecked()){
                    for (int  i = 1 ; i < num+1 ; i++){
                        if (i < 10){
                            selectedList.add(getSelTime()+"0"+i);
                        }else{
                            selectedList.add(getSelTime()+i);
                        }
                    }
                }else{
                    removeList.clear();
                    for (int i = 0 ; i < selectedList.size() ; i++){
                        String time = selectedList.get(i);
                        if (time.indexOf(getSelTime()) != -1){
                            removeList.add(selectedList.get(i));
                        }
                    }
                    selectedList.removeAll(removeList);
                }
                    setData(selectedList);
                }
        });

    }

    private void setData(List<String> selectedList){

        if (mYear < curYear){
            allSelBtn.setEnabled(false);
        }else if(mYear == curYear && mMonth < curMonth){
            allSelBtn.setEnabled(false);
        }else{
            allSelBtn.setEnabled(true);
        }

        tempList.clear();
        if (null != selectedList && !selectedList.isEmpty()){
            for (String time : selectedList){
                String [] splitTime = time.split("-");
                if (splitTime[0].equals(String.valueOf(mYear)) && splitTime[1].equals(getCompareMonth(mMonth))){
                    ZypCalendarDateEntity dateEntity = new ZypCalendarDateEntity();
                    dateEntity.setCanClick(true);
                    dateEntity.setDate(splitTime[2]);
                    dateEntity.setIsSelected(true);
                    tempList.add(dateEntity);
                }
            }
        }


        //首次显示当前月
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(mYear, mMonth-1, 1);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1 ;
        int num = dayAmountOfMonth(calendar);
        dataList.clear();
        for (int i = 0; i < dayOfWeek ; i++){
            ZypCalendarDateEntity calendarDateEntity = new ZypCalendarDateEntity();
            calendarDateEntity.setIsSelected(false);
            calendarDateEntity.setDate(" ");
            dataList.add(calendarDateEntity);
        }
        for(int i = 1; i < num+1 ; i ++){
            ZypCalendarDateEntity calendarDateEntity = new ZypCalendarDateEntity();
            if (null != tempList && !tempList.isEmpty()){
                for (ZypCalendarDateEntity dateEntity : tempList){
                    String compareDay = "";
                    if (i < 10){
                        compareDay = "0"+ i;
                    }else{
                        compareDay = String.valueOf(i);
                    }
                    if (String.valueOf(compareDay).equals(dateEntity.getDate())){
                        calendarDateEntity.setIsSelected(true);
                        break;
                    }else{
                        calendarDateEntity.setIsSelected(false);
                    }
                }
            }else{
                calendarDateEntity.setIsSelected(false);
            }
            calendarDateEntity.setDate(String.valueOf(i));
            if (mYear < curYear){
                calendarDateEntity.setCanClick(false);
            }else if (mYear == curYear && mMonth < curMonth){
                calendarDateEntity.setCanClick(false);
            }else if (mYear == curYear && mMonth == curMonth && i < curDay){
                calendarDateEntity.setCanClick(false);
            }else{
                calendarDateEntity.setCanClick(true);
            }
            dataList.add(calendarDateEntity);
        }
        for (int i = 0 ; i < (42 - (num) -dayOfWeek) ; i++){
            ZypCalendarDateEntity calendarDateEntity = new ZypCalendarDateEntity();
            calendarDateEntity.setIsSelected(false);
            calendarDateEntity.setDate(" ");
            dataList.add(calendarDateEntity);
        }
        calendarWidget.setDateGridView(dataList);
        //每次换月之后判断全选
        judgeAllSel();

    }
    //获取这个月的总天数
    private int dayAmountOfMonth(Calendar calendar){
        Calendar a = calendar;
        a.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        a.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    //初始化时间滚轮选择
    private void initWheelSelect(){
        //年份
        NumericWheelAdapter yearAdapter = new NumericWheelAdapter(getContext() , curYear , 3000 , "%02d");
        yearAdapter.setLabel(" ");
        yearAdapter.setTextSize(18);
        yearWheel.setViewAdapter(yearAdapter);
        yearWheel.setCyclic(false);
        //设置监听
        yearWheel.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                mYear = yearWheel.getCurrentItem() + curYear;//年
                mMonth = monthWheel.getCurrentItem() + 1;//月
            }
        });

        //月份
        NumericWheelAdapter monthAdapter = new NumericWheelAdapter(getContext() , 1 , 12 , "%02d");
        monthAdapter.setLabel(" ");
        monthAdapter.setTextSize(18);
        monthWheel.setViewAdapter(monthAdapter);
        monthWheel.setCyclic(false);
        //设置监听
        monthWheel.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                mYear = yearWheel.getCurrentItem() + curYear;//年
                mMonth = monthWheel.getCurrentItem() + 1;//月
            }
        });

        //设置显示行数
        yearWheel.setVisibleItems(6);
        monthWheel.setVisibleItems(6);

        //设置选中显示的数据
        yearWheel.setCurrentItem(mYear - curYear);
        monthWheel.setCurrentItem(mMonth-1);

        //确定点击事件
        sureTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(selectedList);
                timeSelTv.setText(getCurTimeDes());
                timePickerLayout.setVisibility(View.GONE);

            }
        });
    }

    //获取当前年月描述
    private String getCurTimeDes(){
        if (mMonth<10){
            return mYear + "年" + "0" + mMonth + "月";
        }else{
            return mYear + "年" + mMonth + "月";
        }
    }

    /**设置时间描述
     *
     * @param des
     */
    public void setTimeDes(String des){
        timeDesTv.setText(des);
    }

    /**获取选中的年月
     *
     * @return
     */
    public String getSelTime(){
        if (mMonth<10){
            return mYear + "-" + "0"+ mMonth + "-";
        }else{
            return mYear+"-"+mMonth + "-";
        }
    }

    /**多选获取日期
     *
     * @return
     */
    public String getMultiSelDateStr(){
        StringBuilder dateStr = new StringBuilder();
        String compareStr = getCurCompare();
        for (String string : selectedList){
            //在当前年月
            if (string.indexOf(compareStr) != -1){
                //小于当日
                if (Integer.parseInt(string.split("-")[2]) >= curDay){
                    dateStr.append(string);
                    dateStr.append(",");
                }
            }else{
                dateStr.append(string);
                dateStr.append(",");
            }
        }
        if (!TextUtils.isEmpty(dateStr.toString())){
            dateStr.deleteCharAt(dateStr.length() -1);
        }
        return dateStr.toString();
    }


    //获取去比较的月份
    private String getCompareMonth(int month){
        if (month < 10){
            return  "0" + month;
        }else{
            return String.valueOf(month);
        }
    }

    //判断全选
    private void judgeAllSel(){

        boolean isAllSel = true;
        for (ZypCalendarDateEntity dateEntity : dataList){
            if (!dateEntity.getDate().equals(" ")){
                if(mYear == curYear && mMonth == curMonth){
                    if (Integer.parseInt(dateEntity.getDate()) >= curDay){
                        if (!dateEntity.isSelected()){
                            isAllSel = false;
                        }
                    }
                }else if(!dateEntity.isSelected()){
                    isAllSel = false;
                }
            }
        }
       allSelBtn.setChecked(isAllSel);
    }

    /**当前年月
     *
     * @return
     */
    private String getCurCompare(){
        if (curMonth < 10){
            return curYear + "-" + "0"+ curMonth + "-";
        }else{
            return curYear + "-" + curMonth + "-";
        }
    }
}
