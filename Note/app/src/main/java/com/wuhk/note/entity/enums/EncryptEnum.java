package com.wuhk.note.entity.enums;

/**
 * Created by wuhk on 2016/5/11.
 */
public enum  EncryptEnum {
    //1、未选中  2、选中 100、未知
    NORMAL(1) , ENCRYPT(2) , UNKNOW(100);

    private int value;

    EncryptEnum(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static EncryptEnum valueOf(int type) {
        switch (type){
            case 1:
                return NORMAL;
            case 2:
                return ENCRYPT;
            default:
                return UNKNOW;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case NORMAL:
                return "加密";
            case ENCRYPT:
                return "正常";
            default:
                return "未知";
        }
    };

    public boolean equals(EncryptEnum encryptEnum) {
        if (null == encryptEnum) {
            return false;
        }

        return value == encryptEnum.value;
    }
}
