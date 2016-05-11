package com.wuhk.note.common;

import android.os.Environment;

import com.xuan.bigapple.lib.utils.ContextUtils;

/**
 * 常量
 * Created by wuhk on 2016/5/5.
 */
public class Constants {
    static {
        if (ContextUtils.hasSdCard()) {
            SDCARD = Environment.getExternalStorageDirectory().getPath();
        } else {
            SDCARD = ContextUtils.getFileDirPath();
        }
    }

    /**
     * 本地数据库版本
     */
    public static final int DB_VERSION = 1;

    /**
     * 本地数据库名称
     */
    public static String DB_NAME = "note_db";

    /**
     * 文件存储目录
     */
    public static String SDCARD;

    /**讯飞AppId
     *
     */
    public static String XFAPPID = "572cd1f8";


    public static String SDCARD_NOTE = SDCARD + "/note/";

    public static final String SDCARD_NOTE_DIARY_TEMP_CAMREA = SDCARD_NOTE + "diary/temp/camrea.jpg";

    public static final String SDCARD_NOTE_DIARY_EDIT = SDCARD_NOTE + "diary/edit/";

    /**
     * 图片限制宽高
     */
    public static final int IMAGE_LIMIT_SIZE = 500;


}
