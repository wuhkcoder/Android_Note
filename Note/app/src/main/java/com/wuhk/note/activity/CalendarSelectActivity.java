package com.wuhk.note.activity;

import android.os.Bundle;
import android.view.View;

import com.wuhk.note.R;
import com.wuhk.note.utils.ToastUtil;
import com.xuan.bigapple.lib.ioc.InjectView;
import com.xuan.bigapple.lib.utils.Validators;
import com.xuan.bigdog.lib.widgets.title.DGTitleLayout;
import com.zhiyoupei.zypcommonlib.calendar.ZypCalendarLayout;
import com.zhiyoupei.zypcommonlib.calendar.ZypCalendarWidget;

import java.util.ArrayList;

/**
 * 时间筛选
 * Created by wuhk on 2016/5/12.
 */
public class CalendarSelectActivity extends BaseActivity {
    @InjectView(R.id.titleLayout)
    private DGTitleLayout titleLayout;

    @InjectView(R.id.calendarLayout)
    private ZypCalendarLayout calendarLayout;

    public static final int resultCode = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calendar);
        initWidgets();
    }

    private void initWidgets(){
        titleLayout.configReturn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).configTitle("时间筛选").configRightText("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = calendarLayout.getMultiSelDateStr();
                if (Validators.isEmpty(time)){
                    ToastUtil.toast("筛选日期不能为空");
                }else{
                    setResult(resultCode, getIntent().putExtra("date", time));
                    finish();
                }
            }
        });

        //可多选
        calendarLayout.init(new ArrayList<String>());
    }


}
