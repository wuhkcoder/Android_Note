package com.wuhk.note.activity.frame.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wuhk.note.R;
import com.wuhk.note.activity.frame.BaseFragment;
import com.wuhk.note.adapter.DiaryAdapter;
import com.wuhk.note.dao.DaoFactory;
import com.wuhk.note.entity.DiaryEntity;
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

    public static boolean isReload = false;

    @Override
    protected int initFragmentView() {
        return R.layout.fragment1;
    }

    @Override
    protected void initFragmentWidgets(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isReload){
            loadData();
        }
    }

    private void loadData(){
        dataList = DaoFactory.getDiaryDao().findAllDiary();
        diaryAdapter = new DiaryAdapter(getContext() , dataList);
        listView.setAdapter(diaryAdapter);
        isReload = false;
    }
}
