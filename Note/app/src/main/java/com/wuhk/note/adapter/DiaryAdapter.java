package com.wuhk.note.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wuhk.note.R;
import com.wuhk.note.activity.EncryptActivity;
import com.wuhk.note.activity.edit.EditDiaryActivity;
import com.wuhk.note.dao.DaoFactory;
import com.wuhk.note.entity.DiaryEntity;
import com.wuhk.note.utils.ToastUtil;
import com.xuan.bigappleui.lib.album.BUAlbum;
import com.xuan.bigdog.lib.dialog.DGSingleSelectDialog;

import java.util.List;

/**
 * Created by wuhk on 2016/5/5.
 */
public class DiaryAdapter extends MBaseAdapter {
    private Context context;
    private List<DiaryEntity> dataList;

    public static String DIARY = "diary";
    public static String TYPE = "type";
    public static String MODE = "mode";
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
        final TextView contentTv = (TextView)view.findViewById(R.id.contentTv);
        ImageView encryptIv = (ImageView)view.findViewById(R.id.encryptIv);

        final DiaryEntity data = dataList.get(position);
        initTextView(timeTv , data.getCreateTime());
        initTextView(contentTv , data.getContent());

        if (data.getEncrypt() == 2){
            //未加密
            encryptIv.setVisibility(View.VISIBLE);
        }else{
            encryptIv.setVisibility(View.INVISIBLE);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getEncrypt() == 2){
                    Intent intent = new Intent();
                    intent.setClass(context , EncryptActivity.class);
                    intent.putExtra(TYPE , "inputPass");
                    intent.putExtra(MODE , "single");
                    intent.putExtra(DIARY , JSON.toJSONString(data));
                    context.startActivity(intent);
                }else{
                    //未加密
                    Intent intent = new Intent();
                    intent.setClass(context , EditDiaryActivity.class);
                    intent.putExtra(DIARY , JSON.toJSONString(data));
                    context.startActivity(intent);
                }


            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (data.getEncrypt() == 2){
                    //加密过了，需要解锁
                    DGSingleSelectDialog d = new DGSingleSelectDialog.Builder(context)
                            .setItemTextAndOnClickListener(new String[]{"删除该日记", "解锁"}, new View.OnClickListener[]{new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DaoFactory.getDiaryDao().deleteById(data.getId());
                                    dataList.remove(data);
                                    notifyDataSetChanged();

                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.putExtra(TYPE , "cancelPass");
                                    intent.putExtra(MODE , "single");
                                    intent.putExtra(DIARY , JSON.toJSONString(data));
                                    intent.setClass(context , EncryptActivity.class);
                                    context.startActivity(intent);

                                }
                            }}).create();
                    d.show();
                }else{
                    DGSingleSelectDialog d = new DGSingleSelectDialog.Builder(context)
                            .setItemTextAndOnClickListener(new String[]{"删除该日记", "加密" ,"分享"}, new View.OnClickListener[]{new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DaoFactory.getDiaryDao().deleteById(data.getId());
                                    dataList.remove(data);
                                    notifyDataSetChanged();

                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.putExtra(TYPE , "setPass");
                                    intent.putExtra(MODE , "single");
                                    intent.putExtra(DIARY , JSON.toJSONString(data));
                                    intent.setClass(context , EncryptActivity.class);
                                    context.startActivity(intent);

                                }
                            } , new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtil.toast("分享");
                                }
                            }}).create();
                    d.show();
                }

                return true;
            }
        });
        return view;
    }
}
