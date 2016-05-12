package com.wuhk.note.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wuhk.note.R;
import com.wuhk.note.dao.DaoFactory;
import com.wuhk.note.entity.DiaryEntity;
import com.wuhk.note.utils.ToastUtil;
import com.xuan.bigapple.lib.ioc.InjectView;
import com.xuan.bigapple.lib.utils.Validators;
import com.xuan.bigapple.lib.utils.sharepreference.BPPreferences;
import com.xuan.bigdog.lib.widgets.title.DGTitleLayout;

/**
 * 忘记密码
 * Created by wuhk on 2016/5/12.
 */
public class ForgetPassActivity extends BaseActivity {
    @InjectView(R.id.titleLayout)
    private DGTitleLayout titleLayout;

    @InjectView(R.id.phoneEt)
    private EditText phoneEt;

    @InjectView(R.id.passEt)
    private EditText passEt;

    @InjectView(R.id.sureBtn)
    private Button sureBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_forget_pass);
        initWidgets();
    }

    private void initWidgets(){
        titleLayout.configReturn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).configTitle("忘记密码");

        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneEt.getText().toString();
                String pass = passEt.getText().toString();
                if (!BPPreferences.instance().getBoolean("isHavePass" , false)){
                    ToastUtil.toast("您还没有设置过密码，请先去加密日记栏设置");
                }else if (Validators.isEmpty(phone) || Validators.isEmpty(pass)){
                    ToastUtil.toast("请将信息填写完整");
                }else{
                    if (phone.equals(BPPreferences.instance().getString("phone" , ""))){
                        if (pass.length() != 6){
                            ToastUtil.toast("密码需要6位");
                        }else{
                            BPPreferences.instance().putString("myPassword" , pass);
                            for (DiaryEntity diaryEntity : DaoFactory.getDiaryDao().findEncryptDiary()){
                                diaryEntity.setPassword(pass);
                                DaoFactory.getDiaryDao().insertOrReplace(diaryEntity);
                            }
                            ToastUtil.toast("密码重设成功");
                            finish();
                        }
                    }else{
                        ToastUtil.toast("输入的手机号与设置的手机号不一样");
                    }
                }

            }
        });

    }
}
