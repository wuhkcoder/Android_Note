package com.wuhk.note.dao;

import android.database.Cursor;
import android.database.SQLException;

import com.wuhk.note.entity.DiaryEntity;
import com.wuhk.note.entity.enums.EncryptEnum;
import com.wuhk.note.utils.LogUtil;
import com.xuan.bigapple.lib.db.BPBaseDao;
import com.xuan.bigapple.lib.db.callback.MultiRowMapper;
import com.xuan.bigapple.lib.db.callback.SingleRowMapper;
import com.xuan.bigapple.lib.db.helper.SqlUtils;
import com.xuan.bigapple.lib.utils.Validators;

import java.util.List;

/**日记dao
 * Created by wuhk on 2016/5/8.
 */
public class DiaryDao extends BPBaseDao{
    private final String SQL_INSERT = "INSERT OR REPLACE INTO diary(id,createTime, weekDay,weather,content,pic,encrypt,password , modifyTime) VALUES (?,?,?,?,?,?,?,?,?)";
    private final String SQL_FIND_ALL = "SELECT * FROM diary ORDER BY modifyTime DESC";
    private final String SQL_DELETE_BY_ID = "DELETE FROM diary WHERE id = ?";
    private final String SQL_SELECT_BY_ENCRYPT = "SELECT * FROM diary WHERE encrypt = ? ORDER BY modifyTime DESC";
    private final String SQL_SELECT_BY_CONTENT = "SELECT * FROM diary WHERE encrypt = 1 AND content LIKE ?";
    private final String SQL_SELECT_BY_CREATETIME = "SELECT * FROM diary WHERE encrypt = 1 AND createTime IN (";

    /**查找所有日记
     *
     * @return
     */
    public List<DiaryEntity> findAllDiary(){

        List<DiaryEntity> ret = bpQuery(SQL_FIND_ALL ,
                new String[]{} , new MMultiRowMapper());
        return ret;
    }

    /**查找所有日记
     *
     * @return
     */
    public List<DiaryEntity> findByTime(String time , int length){
        if (length < 1){
            return  null;
        }else{
            StringBuilder sb = new StringBuilder();
            sb.append("?");
            for (int i = 1; i < length ; i++){
                sb.append(",?");
            }
            List<DiaryEntity> ret = bpQuery(SQL_SELECT_BY_CREATETIME+sb.toString() + ");" ,
                    new String[]{time} , new MMultiRowMapper());
            LogUtil.e(SqlUtils.getSQL(SQL_SELECT_BY_CREATETIME, new String[]{time} ));
            return ret;
        }

    }

    /**根据内容模糊查询
     *
     * @param content
     * @return
     */
    public List<DiaryEntity> findByContent(String content){
        List<DiaryEntity> ret = bpQuery(SQL_SELECT_BY_CONTENT , new String[]{content} , new MMultiRowMapper());
        return ret;
    }

    /**根据日记id删除日记
     *
     * @param id
     */
    public void deleteById(String id) {
        if (Validators.isEmpty(id)) {
            return;
        }

        bpUpdate(SQL_DELETE_BY_ID, new String[]{id});
    }

    /**查找所有加密日记
     *
     * @return
     */
    public List<DiaryEntity> findEncryptDiary(){
        List<DiaryEntity> ret = bpQuery(SQL_SELECT_BY_ENCRYPT , new String[]{String.valueOf(EncryptEnum.ENCRYPT.getValue())} , new MMultiRowMapper());
        return ret;
    }

    /**查找所有普通日记
     *
     * @return
     */
    public List<DiaryEntity> findNormalDiary(){
        List<DiaryEntity> ret = bpQuery(SQL_SELECT_BY_ENCRYPT , new String[]{String.valueOf(EncryptEnum.NORMAL.getValue())} , new MMultiRowMapper());
        return ret;
    }

    /**插入或者更新日记
     *
     * @param diary
     */
    public void insertOrReplace(DiaryEntity diary){
        if (null == diary){
            return;
        }

        bpUpdate(
                SQL_INSERT,
                new Object[]{diary.getId() , diary.getCreateTime() , diary.getWeekDay(),
                diary.getWeather() , diary.getContent() , diary.getPic() , diary.getEncrypt() , diary.getPassword() , diary.getModifyTime()}
        );
    }

    /**
     * 多条结果集
     */
    private class MMultiRowMapper implements MultiRowMapper<DiaryEntity> {
        @Override
        public DiaryEntity mapRow(Cursor cursor, int n) throws SQLException {
            DiaryEntity diary = new DiaryEntity();
            diary.setId(cursor.getString(cursor.getColumnIndex("id")));
            diary.setCreateTime(cursor.getString(cursor.getColumnIndex("createTime")));
            diary.setWeekDay(cursor.getString(cursor.getColumnIndex("weekDay")));
            diary.setWeather(cursor.getString(cursor.getColumnIndex("weather")));
            diary.setContent(cursor.getString(cursor.getColumnIndex("content")));
            diary.setPic(cursor.getString(cursor.getColumnIndex("pic")));
            diary.setEncrypt(cursor.getInt(cursor.getColumnIndex("encrypt")));
            diary.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            diary.setModifyTime(cursor.getLong(cursor.getColumnIndex("modifyTime")));

            return diary;
        }
    }

    /**
     * 单条结果集
     */
    private class MSingleRowMapper implements SingleRowMapper<DiaryEntity> {
        @Override
        public DiaryEntity mapRow(Cursor cursor) throws SQLException {
            return new MMultiRowMapper().mapRow(cursor, 0);
        }
    }
}
