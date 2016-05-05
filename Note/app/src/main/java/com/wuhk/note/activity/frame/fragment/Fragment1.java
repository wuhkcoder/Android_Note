package com.wuhk.note.activity.frame.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wuhk.note.R;
import com.wuhk.note.activity.frame.BaseFragment;
import com.wuhk.note.adapter.DiaryAdapter;
import com.wuhk.note.data.DiaryData;
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
    private List<DiaryData> dataList = new ArrayList<DiaryData>();

    @Override
    protected int initFragmentView() {
        return R.layout.fragment1;
    }

    @Override
    protected void initFragmentWidgets(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        diaryAdapter = new DiaryAdapter(getContext() , dataList);
        listView.setAdapter(diaryAdapter);
        loadData();
    }

    private void loadData(){
        DiaryData data1 = new DiaryData();
        data1.setTitle("发动机号广东客服了解韩国好看");
        data1.setContent("是打开附件是打开链接来规范考试");
        dataList.add(data1);

        DiaryData data2 = new DiaryData();
        data2.setTitle("教科书的股份好看");
        data2.setContent("速度快结婚方会计师的恢复快速打开感觉不合适的空间");
        dataList.add(data2);

        DiaryData data3 = new DiaryData();
        data3.setTitle("的深刻理解付款了");
        data3.setContent("的接口是否该考试的机会");
        dataList.add(data3);

        DiaryData data4 = new DiaryData();
        data4.setTitle("的思考两个分开了");
        data4.setContent("看来是对方会感觉了");
        dataList.add(data4);

        diaryAdapter.notifyDataSetChanged();
    }
}
