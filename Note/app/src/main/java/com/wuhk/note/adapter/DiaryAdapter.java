package com.wuhk.note.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wuhk.note.R;
import com.wuhk.note.entity.DiaryEntity;

import java.util.List;

/**
 * Created by wuhk on 2016/5/5.
 */
public class DiaryAdapter extends MBaseAdapter {
    private Context context;
    private List<DiaryEntity> dataList;

    public DiaryAdapter(Context context, List<DiaryEntity> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (null == view){
            view = LayoutInflater.from(context).inflate(R.layout.listitem_diary , null);
        }

        TextView timeTv = (TextView)view.findViewById(R.id.timeTv);
        TextView contentTv = (TextView)view.findViewById(R.id.contentTv);

        DiaryEntity data = dataList.get(position);
        initTextView(timeTv , data.getCreateTime());
        initTextView(contentTv , data.getContent());
//        initTextView(titleTv , data.getTitle());
//        initTextView(contentTv , data.getContent());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context , "点击了" + position , Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
