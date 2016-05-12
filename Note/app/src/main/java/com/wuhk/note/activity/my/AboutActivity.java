package com.wuhk.note.activity.my;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wuhk.note.R;
import com.wuhk.note.activity.BaseActivity;
import com.xuan.bigapple.lib.ioc.InjectView;
import com.xuan.bigapple.lib.utils.VersionUtils;
import com.xuan.bigdog.lib.widgets.title.DGTitleLayout;

/**
 * 关于
 * Created by wuhk on 2016/5/9.
 */
public class AboutActivity extends BaseActivity {
    @InjectView(R.id.titleLayout)
    private DGTitleLayout titleLayout;

    @InjectView(R.id.nameTv)
    private TextView nameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);

        titleLayout.configReturn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).configTitle("关于");

        nameTv.setText("Note v" + VersionUtils.getVersionName());
    }
}
