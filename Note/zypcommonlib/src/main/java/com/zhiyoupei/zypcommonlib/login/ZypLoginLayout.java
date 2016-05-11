package com.zhiyoupei.zypcommonlib.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhiyoupei.zypcommonlib.R;
import com.zhiyoupei.zypcommonlib.listener.ZypGetCodeClickListener;
import com.zhiyoupei.zypcommonlib.listener.ZypGetCodeNextClickListener;
import com.zhiyoupei.zypcommonlib.listener.ZypLoginClickListener;
import com.zhiyoupei.zypcommonlib.listener.ZypSetPassClickListener;

/**
 * Created by wuhk on 2016/4/12.
 */
public class ZypLoginLayout extends LinearLayout {
    //登录用控件
    private RelativeLayout loginLayout;
    private EditText phoneEt;
    private EditText passEt;
    private Button sureBtn;
    private TextView forgetTv;
    private RelativeLayout registerLayout;
    //设置密码用控件
    private RelativeLayout setPassLayout;
    private EditText setPassEt;
    private EditText rePassEt;
    private Button savePassBtn;
    private RelativeLayout backLoginLayout;
    //注册用控件
    private RelativeLayout zypRelayout;
    private EditText phone2Et;
    private EditText identifyEt;
    private Button getCodeBtn;
    private Button nextBtn;
    private RelativeLayout reBackLoginLayout;


    private String type = "";
    private String tempPhone= "";


    public ZypLoginLayout(Context context) {
        super(context);
        init();
    }

    public ZypLoginLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init(){
        inflate(getContext() , R.layout.zyp_layout_login , this);

        //登录布局
        loginLayout = (RelativeLayout)findViewById(R.id.loginLayout);
        phoneEt = (EditText)findViewById(R.id.phoneEt);
        passEt = (EditText)findViewById(R.id.passEt);
        sureBtn = (Button)findViewById(R.id.sureBtn);
        forgetTv = (TextView)findViewById(R.id.forgetTv);
        registerLayout = (RelativeLayout)findViewById(R.id.registerLayout);

        //设置密码布局
        setPassLayout  = (RelativeLayout)findViewById(R.id.setPassLayout);
        setPassEt = (EditText)findViewById(R.id.setPassEt);
        rePassEt = (EditText)findViewById(R.id.rePassEt);
        savePassBtn = (Button)findViewById(R.id.savePassBtn);
        backLoginLayout = (RelativeLayout)findViewById(R.id.backLoginLayout);

        //注册布局
        zypRelayout = (RelativeLayout)findViewById(R.id.zypReLayout);
        phone2Et = (EditText)findViewById(R.id.phone2Et);
        identifyEt = (EditText)findViewById(R.id.identifyEt);
        getCodeBtn = (Button)findViewById(R.id.getCodeBtn);
        nextBtn = (Button)findViewById(R.id.nextBtn);
        reBackLoginLayout  = (RelativeLayout)findViewById(R.id.reBackLoginLayout);
        //忘记密码改变布局
        forgetTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "forget";
                showSelLayout(zypRelayout);
//                zypRelayout.setVisibility(VISIBLE);
//                loginLayout.setVisibility(GONE);
//                setPassLayout.setVisibility(GONE);
            }
        });

        //底部改变布局的点击事件
        registerLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "register";
                showSelLayout(zypRelayout);
                zypRelayout.setVisibility(VISIBLE);
                loginLayout.setVisibility(GONE);
                setPassLayout.setVisibility(GONE);
            }
        });

        backLoginLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelLayout(loginLayout);
//                zypRelayout.setVisibility(GONE);
//                loginLayout.setVisibility(VISIBLE);
//                setPassLayout.setVisibility(GONE);
            }
        });

        reBackLoginLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelLayout(loginLayout);
//                zypRelayout.setVisibility(GONE);
//                loginLayout.setVisibility(VISIBLE);
//                setPassLayout.setVisibility(GONE);
            }
        });

    }

    /**配置登录按钮点击事件
     *
     * @param listener
     * @return
     */
    public ZypLoginLayout configLoginClick(final ZypLoginClickListener listener){
        //登录确定按钮点击事件
        sureBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneEt.getText().toString();
                tempPhone = phone;
                String pass = passEt.getText().toString();
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(getContext() , "请填写手机号" , Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(getContext() , "请填写密码" , Toast.LENGTH_SHORT).show();
                }else{
                    listener.onClick(phone , pass);
                }
            }
        });
        return this;
    }

    /**配置设置密码点击事件
     *
     * @param listener
     * @return
     */
    public ZypLoginLayout configSetPassClick(final ZypSetPassClickListener listener){
        savePassBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = setPassEt.getText().toString();
                String rePass = rePassEt.getText().toString();
                if (TextUtils.isEmpty(pass)){
                    Toast.makeText(getContext() , "请输入原密码" , Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(rePass)){
                    Toast.makeText(getContext() , "请输入新密码" , Toast.LENGTH_SHORT).show();
                }else if (!pass.equals(rePass)){
                    Toast.makeText(getContext() , "两次输入密码不一致" , Toast.LENGTH_SHORT).show();
                }else {
                    listener.onClick(tempPhone , rePass , type);
                }
            }
        });

        return this;
    }

    /**获取验证码
     *
     * @param listener
     * @return
     */
    public ZypLoginLayout configGetCodeClick(final ZypGetCodeClickListener listener){
        getCodeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phone2Et.getText().toString();
                tempPhone = phone;
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(getContext() , "请填写手机号" , Toast.LENGTH_SHORT).show();
                }else{
                    listener.onClick(phone , type , getCodeBtn);
                }
            }
        });
        return this;
    }

    /**获取验证码之后下一步点击事件
     *
     * @param listener
     * @return
     */
    public ZypLoginLayout configGetCodeNextClick(final ZypGetCodeNextClickListener listener){
        nextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phone2Et.getText().toString();
                tempPhone = phone;
                String code = identifyEt.getText().toString();
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(getContext() , "请填写手机号" , Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(code)){
                    Toast.makeText(getContext() , "请填写验证码" , Toast.LENGTH_SHORT).show();
                }else{
                    listener.onClick(phone , code , type , loginLayout , zypRelayout , setPassLayout);
                }
            }
        });
        return this;
    }

    /**设置默认显示上一次登录过的用户名
     *
     * @param phone
     */
    public void setLoginedPhoneNum(String phone){
        phoneEt.setText(phone);
    }


    //显示选择界面，并清空
    private void showSelLayout(RelativeLayout layout){
        if(layout.equals(loginLayout)){
            zypRelayout.setVisibility(GONE);
            loginLayout.setVisibility(VISIBLE);
            setPassLayout.setVisibility(GONE);
        }else if (layout.equals(zypRelayout)){
            zypRelayout.setVisibility(VISIBLE);
            loginLayout.setVisibility(GONE);
            setPassLayout.setVisibility(GONE);
        }else if(layout.equals(setPassLayout)){
            zypRelayout.setVisibility(GONE);
            loginLayout.setVisibility(GONE);
            setPassLayout.setVisibility(VISIBLE);
        }
        phoneEt.setText("");
        passEt.setText("");
        setPassEt.setText("");
        rePassEt.setText("");
        phone2Et.setText("");
        identifyEt.setText("");
    }
}
