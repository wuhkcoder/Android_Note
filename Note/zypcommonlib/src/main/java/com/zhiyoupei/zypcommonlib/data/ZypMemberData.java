package com.zhiyoupei.zypcommonlib.data;

import java.io.Serializable;
import java.util.List;

/**
 * 队伍详情数据
 * Created by wuhk on 2016/4/27.
 */
public class ZypMemberData{
    private String chatId;
    private int type;
    private int payStatus;
    private String price;
    private String orderId;
    private List<ZypMember> zypMembers;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public List<ZypMember> getZypMembers() {
        return zypMembers;
    }

    public void setZypMembers(List<ZypMember> zypMembers) {
        this.zypMembers = zypMembers;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public static class ZypMember{
        private String orderMemberId;
        private String phone;
        private String name;
        private boolean master;
        private boolean signed;
        private boolean contractEmpty;
        private List<ZypContract> zypContracts;

        public String getOrderMemberId() {
            return orderMemberId;
        }

        public void setOrderMemberId(String orderMemberId) {
            this.orderMemberId = orderMemberId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isMaster() {
            return master;
        }

        public void setMaster(boolean master) {
            this.master = master;
        }

        public boolean isSigned() {
            return signed;
        }

        public void setSigned(boolean signed) {
            this.signed = signed;
        }

        public boolean isContractEmpty() {
            return contractEmpty;
        }

        public void setContractEmpty(boolean contractEmpty) {
            this.contractEmpty = contractEmpty;
        }

        public List<ZypContract> getZypContracts() {
            return zypContracts;
        }

        public void setZypContracts(List<ZypContract> zypContracts) {
            this.zypContracts = zypContracts;
        }

        public static class ZypContract{
            private String id;
            private String name;
            private boolean signed;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean isSigned() {
                return signed;
            }

            public void setSigned(boolean signed) {
                this.signed = signed;
            }
        }
    }

}
