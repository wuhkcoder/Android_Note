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
import com.wuhk.note.receiver.RefreshNormalDiaryReceiver;
import com.xuan.bigapple.lib.ioc.InjectView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by wuhk on 2016/5/5.
 */
public class Fragment1 extends BaseFragment{
    @InjectView(R.id.listView)
    private ListView listView;

    private DiaryAdapter diaryAdapter;
    private List<DiaryEntity> dataList = new ArrayList<DiaryEntity>();

    public static boolean reloadEncrypt;
    private RefreshNormalDiaryReceiver refreshNormalDiaryReceiver;//刷新日记
    @Override
    protected int initFragmentView() {
        return R.layout.fragment1;
    }

    @Override
    protected void initFragmentWidgets(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        refreshNormalDiaryReceiver.unRegister();
    }

    private void loadData(boolean isEncrypt){
        reloadEncrypt =false;
        dataList.clear();
        if (isEncrypt){
            dataList = DaoFactory.getDiaryDao().findEncryptDiary();
        }else{
            dataList = DaoFactory.getDiaryDao().findNormalDiary();
        }
        diaryAdapter = new DiaryAdapter(getContext() , dataList);
        listView.setAdapter(diaryAdapter);
    }
}
