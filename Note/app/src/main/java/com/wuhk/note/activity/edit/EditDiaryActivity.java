package com.wuhk.note.activity.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wuhk.note.R;
import com.wuhk.note.activity.BaseActivity;
import com.xuan.bigapple.lib.ioc.InjectView;
import com.xuan.bigdog.lib.widgets.title.DGTitleLayout;

/**写日记
 * Created by wuhk on 2016/5/5.
 */
public class EditDiaryActivity extends BaseActivity {
    @InjectView(R.id.titleLayout)
    private DGTitleLayout titleLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_diary);

        initWidgets();
    }

    private void initWidgets(){
        titleLayout.configReturn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleLayout.configTitle("写日记");
    }
}
