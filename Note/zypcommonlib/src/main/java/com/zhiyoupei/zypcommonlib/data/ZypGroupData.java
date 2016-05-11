package com.zhiyoupei.zypcommonlib.data;

import java.util.List;

/**
 * 组团详情master界面
 * Created by wuhk on 2016/4/27.
 */
public class ZypGroupData {
    private String pic;
    private int type;
    private String title;
    private String status;
    private String time;
    private List<ZypMaster> list;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<ZypMaster> getList() {
        return list;
    }

    public void setList(List<ZypMaster> list) {
        this.list = list;
    }

    public static class ZypMaster{
        private String masterId;
        private String orderId;
        private String name;
        private int payStatus;
        private String price;
        private boolean signed;

        public String getMasterId() {
            return masterId;
        }

        public void setMasterId(String masterId) {
            this.masterId = masterId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public boolean isSigned() {
            return signed;
        }

        public void setSigned(boolean signed) {
            this.signed = signed;
        }
    }
}
