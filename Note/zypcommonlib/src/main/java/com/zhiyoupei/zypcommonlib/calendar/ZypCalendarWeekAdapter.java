package com.zhiyoupei.zypcommonlib.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhiyoupei.zypcommonlib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期控件的星期适配器
 * Created by wuhk on 2015/12/28.
 */
public class ZypCalendarWeekAdapter extends BaseAdapter {
    private Context context;
    private List<String> dataList = new ArrayList<String>();

    public ZypCalendarWeekAdapter(Context context) {
        this.context = context;
        setWeekList();
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
        return dataList.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.zyp_griditem_calendar , null);
        TextView contentTv = (TextView)view.findViewById(R.id.contentTv);
//        initTextView(contentTv , dataList.get(position));
        contentTv.setText(dataList.get(position));
        return view;
    }

    private void setWeekList(){
        dataList.clear();
        dataList.add("日");
        dataList.add("一");
        dataList.add("二");
        dataList.add("三");
        dataList.add("四");
        dataList.add("五");
        dataList.add("六");
    }
}

