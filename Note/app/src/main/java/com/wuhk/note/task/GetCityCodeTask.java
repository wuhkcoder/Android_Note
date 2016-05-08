package com.wuhk.note.task;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wuhk.note.common.UrlConstants;
import com.wuhk.note.task.data.CityCodeData;
import com.wuhk.note.utils.LogUtil;
import com.xuan.bigapple.lib.asynctask.helper.Result;
import com.xuan.bigapple.lib.utils.StringUtils;
import com.xuan.bigapple.lib.utils.log.LogUtils;
import com.xuan.bigapple.lib.utils.pinyin.PinyinUtil;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by wuhk on 2016/5/8.
 */
public class GetCityCodeTask extends BaseTask<CityCodeData> {
    public GetCityCodeTask(Context context) {
        super(context);
    }

    @Override
    protected Result<CityCodeData> onHttpRequest(Object... params) {
        HashMap<String , String> paramMap = new HashMap<String , String>();
        Result<CityCodeData> result = new Result<CityCodeData>();
        String city = (String)params[0];
        String citypinyin = PinyinUtil.toPinyinLower(city);
        citypinyin = citypinyin.replace(" " , "");
        LogUtils.e(citypinyin);
        paramMap.put("citypinyin" , citypinyin);
        result = get(UrlConstants.GETCITYCODE , paramMap);
        if (result.isSuccess()){
            CityCodeData cityCodeData = JSON.parseObject(result.getMessage() , CityCodeData.class);
            result.setMessage(cityCodeData.getRetMsg());
            if (cityCodeData.getErrNum() == 0){
                result.setValue(cityCodeData);
            }else{
                result.setSuccess(false);
            }
        }
        return result;
    }

    /*
    * 将字符转为Unicode码表示
    */
    public static String string2unicode(String s){
        int in;
        String st = "";
        for(int i=0;i<s.length();i++){
            in = s.codePointAt(i);
            st = st+"\\u"+Integer.toHexString(in).toUpperCase();
        }
        return st;
    }
    /**
     * 把中文转成Unicode码
     * @param str
     * @return
     */
    public String chinaToUnicode(String str){
        String result="";
        for (int i = 0; i < str.length(); i++){
            int chr1 = (char) str.charAt(i);
            if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)
                result+="\\u" + Integer.toHexString(chr1);
            }else{
                result+=str.charAt(i);
            }
        }
        return result;
    }

    /**
     * 判断是否为中文字符
     * @param c
     * @return
     */
    public  boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {

        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }

        return unicode.toString();
    }
}


