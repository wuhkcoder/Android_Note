package com.wuhk.note.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.wuhk.note.R;
import com.wuhk.note.activity.my.SettingActivity;
import com.wuhk.note.adapter.DiaryAdapter;
import com.wuhk.note.dao.DaoFactory;
import com.wuhk.note.entity.DiaryEntity;
import com.xuan.bigapple.lib.ioc.InjectView;
import com.xuan.bigdog.lib.widgets.title.DGTitleLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 日记搜索
 * Created by wuhk on 2016/5/12.
 */
public class SearchActivity extends BaseActivity {

    @InjectView(R.id.titleLayout)
    private DGTitleLayout titleLayout;

    @InjectView(R.id.searchEt)
    private EditText searchEt;

    @InjectView(R.id.listView)
    private ListView listView;

    private DiaryAdapter diaryAdapter;
    private List<DiaryEntity> dataList = new ArrayList<DiaryEntity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);
        initWidgets();
    }

    private void initWidgets(){
        titleLayout.configReturn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).configTitle("日记搜索");

        diaryAdapter = new DiaryAdapter(SearchActivity.this , dataList);
        listView.setAdapter(diaryAdapter);

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = "%" + searchEt.getText().toString() + "%";
                dataList.clear();
                dataList.addAll(DaoFactory.getDiaryDao().findByContent(content));
                diaryAdapter.notifyDataSetChanged();

            }
        });
    }
}
