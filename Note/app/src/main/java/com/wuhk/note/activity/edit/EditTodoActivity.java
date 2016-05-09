package com.wuhk.note.activity.edit;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.wuhk.note.R;
import com.wuhk.note.activity.BaseActivity;
import com.wuhk.note.activity.frame.fragment.Fragment2;
import com.wuhk.note.adapter.TodosAdapter;
import com.wuhk.note.dao.DaoFactory;
import com.wuhk.note.entity.TodoEntity;
import com.wuhk.note.utils.ToastUtil;
import com.xuan.bigapple.lib.ioc.InjectView;
import com.xuan.bigapple.lib.utils.Validators;
import com.xuan.bigapple.lib.utils.uuid.UUIDUtils;
import com.xuan.bigdog.lib.widgets.title.DGTitleLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 编写备忘
 * Created by wuhk on 2016/5/8.
 */
public class EditTodoActivity extends BaseActivity {
    @InjectView(R.id.titleLayout)
    private DGTitleLayout titleLayout;

    @InjectView(R.id.todoEt)
    private EditText todoEt;

    private String jsonStr;
    private TodoEntity todoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_todo);
        initWidgets();
    }

    private void initWidgets(){
        jsonStr = getIntent().getStringExtra(TodosAdapter.TODOS);
        if (!Validators.isEmpty(jsonStr)){
            todoData = JSON.parseObject(jsonStr , TodoEntity.class);
            todoEt.setText(todoData.getContent());
        }


        titleLayout.configTitle("写备忘").configReturn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleLayout.configRightText("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = todoEt.getText().toString();
                if (Validators.isEmpty(content)){
                    ToastUtil.toast("写点什么吧");
                }else{
                    if (null == todoData){
                        TodoEntity todoEntity = new TodoEntity();
                        todoEntity.setId(UUIDUtils.createId());
                        todoEntity.setSelected(1);
                        todoEntity.setContent(content);
                        todoEntity.setCreateTime(new Date().getTime());

                        DaoFactory.getTodoDao().insertOrRerplace(todoEntity);
                    }else{
                        todoData.setContent(content);
                        todoData.setCreateTime(new Date().getTime());

                        DaoFactory.getTodoDao().insertOrRerplace(todoData);
                    }
                    Fragment2.isReload = true;
                    finish();
                }
            }
        });
    }

}
