package com.wuhk.note.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wuhk.note.R;
import com.wuhk.note.activity.edit.EditTodoActivity;
import com.wuhk.note.dao.DaoFactory;
import com.wuhk.note.entity.TodoEntity;
import com.wuhk.note.view.IVCheckBox;

import java.util.List;

/**
 * 备忘录适配器
 * Created by wuhk on 2016/5/9.
 */
public class TodosAdapter extends MBaseAdapter {
    private Context context;
    private List<TodoEntity> dataList;

    public static String TODOS = "to_dos";

    public TodosAdapter(Context context, List<TodoEntity> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (null == view){
            view = LayoutInflater.from(context).inflate(R.layout.listitem_todo , null);
        }
        final IVCheckBox ivCheckBox = (IVCheckBox)view.findViewById(R.id.ivCheckBox);
        TextView contentTv  = (TextView)view.findViewById(R.id.contentTv);
        RelativeLayout mainLayout = (RelativeLayout)view.findViewById(R.id.mainLayout);

        final TodoEntity todoEntity = dataList.get(position);
        if (todoEntity.getSelected() == 2){
            ivCheckBox.setChecked(true);
            mainLayout.setBackgroundResource(R.color.color_item_click);
        }else{
            ivCheckBox.setChecked(false);
            mainLayout.setBackgroundResource(R.drawable.item_selector);
        }

        ivCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivCheckBox.isChecked()){
                    todoEntity.setSelected(2);
                }else{
                    todoEntity.setSelected(1);
                }
                DaoFactory.getTodoDao().insertOrRerplace(todoEntity);
                notifyDataSetChanged();
            }
        });
        initTextView(contentTv, todoEntity.getContent());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (todoEntity.getSelected() != 2) {
                    Intent intent = new Intent();
                    intent.setClass(context, EditTodoActivity.class);
                    intent.putExtra(TODOS, JSON.toJSONString(todoEntity));
                    context.startActivity(intent);
                }
            }
        });

        return view;
    }
}
