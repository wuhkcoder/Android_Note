package com.zhiyoupei.zypcommonlib.calendar;

/**
 * 日历的时间
 * Created by wuhk on 2015/12/29.
 */
public class ZypCalendarDateEntity {
    private String date;
    private boolean isSelected;
    private boolean canClick;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isCanClick() {
        return canClick;
    }

    public void setCanClick(boolean canClick) {
        this.canClick = canClick;
    }
}
