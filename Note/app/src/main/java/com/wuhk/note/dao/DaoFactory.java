package com.wuhk.note.dao;

import com.xuan.bigapple.lib.db.BPBaseDao;

/**Dao工厂类
 * Created by wuhk on 2016/5/8.
 */
public class DaoFactory extends BPBaseDao {
    private static final DiaryDao diaryDao = new DiaryDao();
    private static final TodoDao todoDao = new TodoDao();

    public static DiaryDao getDiaryDao() {
        return diaryDao;
    }

    public static TodoDao getTodoDao() {
        return todoDao;
    }
}
