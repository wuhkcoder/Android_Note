package com.wuhk.note.activity.edit;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.wuhk.note.R;
import com.wuhk.note.activity.BaseActivity;
import com.xuan.bigapple.lib.ioc.InjectView;
import com.xuan.bigdog.lib.widgets.title.DGTitleLayout;

/**
 * 编写备忘
 * Created by wuhk on 2016/5/8.
 */
public class EditTodoActivity extends BaseActivity {
    @InjectView(R.id.titleLayout)
    private DGTitleLayout titleLayout;

    @InjectView(R.id.todoEt)
    private EditText todoEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_todo);
        initWidgets();
    }

    private void initWidgets(){
        titleLayout.configTitle("写备忘").configReturn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
