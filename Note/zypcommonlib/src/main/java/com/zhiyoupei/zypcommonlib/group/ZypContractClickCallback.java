package com.zhiyoupei.zypcommonlib.group;

import com.zhiyoupei.zypcommonlib.data.ZypMemberData;

/**
 * Created by wuhk on 2016/4/27.
 */
public interface ZypContractClickCallback {
    void clickContract(ZypMemberData.ZypMember.ZypContract zypContract , String orderMemberId);
}
