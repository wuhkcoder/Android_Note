package com.wuhk.note.activity.frame.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wuhk.note.R;
import com.wuhk.note.activity.frame.BaseFragment;
import com.wuhk.note.adapter.TodosAdapter;
import com.wuhk.note.dao.DaoFactory;
import com.wuhk.note.entity.TodoEntity;
import com.wuhk.note.receiver.DelectSelectedReceiver;
import com.xuan.bigapple.lib.ioc.InjectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuhk on 2016/5/5.
 */
public class Fragment2 extends BaseFragment{
    @InjectView(R.id.listView)
    private ListView listView;

    private List<TodoEntity> dataList = new ArrayList<TodoEntity>();
    private TodosAdapter todosAdapter;

    private DelectSelectedReceiver delectSelectedReceiver;//删除选中广播

    public static boolean isReload;
    @Override
    protected int initFragmentView() {
        return R.layout.fragment2;
    }

    @Override
    protected void initFragmentWidgets(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        todosAdapter = new TodosAdapter(getActivity() , dataList);
        listView.setAdapter(todosAdapter);
        loadData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delectSelectedReceiver = new DelectSelectedReceiver() {
            @Override
            public void deleteTodos() {
                for (TodoEntity todoEntity : dataList){
                    if (todoEntity.getSelected() == 2){
                        DaoFactory.getTodoDao().deleteById(todoEntity.getId());
                    }
                }
                loadData();
            }
        };
        delectSelectedReceiver.register();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        delectSelectedReceiver.unRegister();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isReload){
            loadData();
        }
    }

    private void loadData(){
        isReload = false;
        dataList.clear();
        dataList.addAll(DaoFactory.getTodoDao().findALl());
        todosAdapter.notifyDataSetChanged();
    }
}
