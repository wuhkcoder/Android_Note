package com.wuhk.note.activity.frame.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wuhk.note.R;
import com.wuhk.note.activity.frame.BaseFragment;
import com.wuhk.note.adapter.DiaryAdapter;
import com.wuhk.note.dao.DaoFactory;
import com.wuhk.note.entity.DiaryEntity;
import com.wuhk.note.receiver.DiaryLoadByDateReceiver;
import com.wuhk.note.receiver.RefreshNormalDiaryReceiver;
import com.xuan.bigapple.lib.ioc.InjectView;
import com.zhiyoupei.zypcommonlib.zypviews.ZypNoDataView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by wuhk on 2016/5/5.
 */
public class Fragment1 extends BaseFragment{
    @InjectView(R.id.listView)
    private ListView listView;

    @InjectView(R.id.noDataView)
    private ZypNoDataView noDataView;

    private DiaryAdapter diaryAdapter;
    private List<DiaryEntity> dataList = new ArrayList<DiaryEntity>();

    public static boolean reloadEncrypt;
    private RefreshNormalDiaryReceiver refreshNormalDiaryReceiver;//刷新日记
    private DiaryLoadByDateReceiver diaryLoadByDateReceiver;//时间筛选刷新

    @Override
    protected int initFragmentView() {
        return R.layout.fragment1;
    }

    @Override
    protected void initFragmentWidgets(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        noDataView.configMessage("暂无日记");
        diaryAdapter = new DiaryAdapter(getActivity() , dataList);
        listView.setAdapter(diaryAdapter);
        loadData(reloadEncrypt);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshNormalDiaryReceiver = new RefreshNormalDiaryReceiver() {
            @Override
            public void refreshDiary(boolean loadEncrypt) {
                loadData(loadEncrypt);
            }
        };
        refreshNormalDiaryReceiver.register();

        diaryLoadByDateReceiver = new DiaryLoadByDateReceiver() {
            @Override
            public void refreByDate(String time) {
                dataList.clear();
                String[] times = time.split(",");
//                for(int i = 0; i < times.length ; i++){
//                    dataList.addAll(DaoFactory.getDiaryDao().findByTime(times[i]));
//                }
                dataList.addAll(DaoFactory.getDiaryDao().findByTime(time , time.length()));
                diaryAdapter.notifyDataSetChanged();
                noDataView.showIfEmpty(dataList);
            }
        };

        diaryLoadByDateReceiver.register();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        refreshNormalDiaryReceiver.unRegister();
        diaryLoadByDateReceiver.unRegister();
    }

    private void loadData(boolean isEncrypt){
        reloadEncrypt =false;
        dataList.clear();
        if (isEncrypt){
            dataList.addAll(DaoFactory.getDiaryDao().findEncryptDiary());
        }else{
            dataList.addAll(DaoFactory.getDiaryDao().findNormalDiary());
        }
        diaryAdapter.notifyDataSetChanged();
        noDataView.showIfEmpty(dataList);
    }
}
