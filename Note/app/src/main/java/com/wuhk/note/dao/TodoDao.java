package com.wuhk.note.dao;

import android.database.Cursor;
import android.database.SQLException;

import com.wuhk.note.entity.DiaryEntity;
import com.wuhk.note.entity.TodoEntity;
import com.xuan.bigapple.lib.db.BPBaseDao;
import com.xuan.bigapple.lib.db.callback.MultiRowMapper;
import com.xuan.bigapple.lib.db.callback.SingleRowMapper;

import java.util.List;

/**备忘Dao
 * Created by wuhk on 2016/5/8.
 */
public class TodoDao extends BPBaseDao{
    private final String SQL_INSERT =  "INSERT OR　REPLACE INTO todo(id, createTime,title,content)VALUES(?,?,?,?)";
    private final String SQL_FIND_ALL = "SELECT * FROM todo";

    /**查找所有备忘
     *
     * @return
     */
    public List<TodoEntity> findALl(){

        return bpQuery(SQL_FIND_ALL ,
                new String[]{} , new MMultiRowMapper());
    }


    /**插入备忘
     *
     * @param todoEntity
     */
    public void insertOrRerplace(TodoEntity todoEntity){
        if (null == todoEntity){
            return;
        }

        bpUpdate(
                SQL_INSERT,
                new Object[]{todoEntity.getId() , todoEntity.getCreateTime() , todoEntity.getTitle() , todoEntity.getContent()}
        );
    }
    /**
     * 多条结果集
     */
    private class MMultiRowMapper implements MultiRowMapper<TodoEntity> {
        @Override
        public TodoEntity mapRow(Cursor cursor, int n) throws SQLException {
            TodoEntity todoEntity = new TodoEntity();
            todoEntity.setId(cursor.getString(cursor.getColumnIndex("id")));
            todoEntity.setCreateTime(cursor.getString(cursor.getColumnIndex("createTime")));
            todoEntity.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            todoEntity.setContent(cursor.getString(cursor.getColumnIndex("content")));

            return todoEntity;
        }
    }

    /**
     * 单条结果集
     */
    private class MSingleRowMapper implements SingleRowMapper<TodoEntity> {
        @Override
        public TodoEntity mapRow(Cursor cursor) throws SQLException {
            return new MMultiRowMapper().mapRow(cursor, 0);
        }
    }
}
