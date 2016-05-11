package com.wuhk.note.utils;

import com.xuan.bigapple.lib.utils.Validators;

import java.io.File;

/**
 * 文件工具类
 *
 * Created by xuan on 15/11/6.
 */
public abstract class FileUtil {
    /**
     * 确保文件的父文件夹存在
     *
     * @param filePathName
     */
    public static void checkParentFile(String filePathName){
        if(Validators.isEmpty(filePathName)){
            return;
        }

        File file = new File(filePathName);
        File parentFile = file.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
    }

}
