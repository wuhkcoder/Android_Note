package com.wuhk.note.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wuhk.note.R;
import com.wuhk.note.activity.edit.EditDiaryActivity;
import com.wuhk.note.activity.edit.EditTodoActivity;
import com.wuhk.note.activity.frame.fragment.Fragment1;
import com.wuhk.note.activity.frame.fragment.Fragment2;
import com.wuhk.note.activity.my.SettingActivity;
import com.wuhk.note.receiver.DelectSelectedReceiver;
import com.wuhk.note.receiver.DiaryLoadByDateReceiver;
import com.wuhk.note.receiver.RefreshNormalDiaryReceiver;
import com.wuhk.note.utils.ToastUtil;
import com.xuan.bigapple.lib.utils.sharepreference.BPPreferences;



public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    public static boolean passSucceed;
    private int lastCheckedId;

    public static final int myRequestCode = 123;

    private Handler handler = new Handler();
    private boolean backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);

        initWidgets();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (passSucceed){
            lastCheckedId = R.id.encryptDiary;
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new Fragment1()).commit();
            toolbar.setTitle("Diary");
            passSucceed = false;
        }
        navigationView.setCheckedItem(lastCheckedId);
    }

    private void initWidgets(){
        //初始化Toolbar
        toolbar.setTitleTextAppearance(MainActivity.this , R.style.ActionBar_TitleText);
        toolbar.setTitle("Diary");
        setSupportActionBar(toolbar);


        //初始化FloatActionBar
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toolbar.getTitle().equals("Diary")){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this , EditDiaryActivity.class);
                    if (lastCheckedId == R.id.diary){
                        intent.putExtra("diaryType" , false);
                    }else if (lastCheckedId == R.id.encryptDiary){
                        intent.putExtra("diaryType" , true);
                    }
                    startActivity(intent);
                }else if (toolbar.getTitle().equals("TO-DOs")){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this , EditTodoActivity.class);
                    startActivity(intent);
                }

            }
        });

        //初始化侧滑
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setCheckedItem(R.id.diary);
        lastCheckedId = R.id.diary;
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content , new Fragment1()).commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.diary) {
                    lastCheckedId = R.id.diary;
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new Fragment1()).commit();
                    toolbar.setTitle("Diary");
                    RefreshNormalDiaryReceiver.notifyReceiver(false);
                }
                if (id == R.id.encryptDiary) {
                    boolean isHavePass = BPPreferences.instance().getBoolean("isHavePass" , false);
                    if (isHavePass){
                        //输入密码
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this , DiaryPassActivity.class);
                        intent.putExtra("operate" , "input");
                        startActivity(intent);
                    }else{
                        //设置密码
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this , DiaryPassActivity.class);
                        intent.putExtra("operate" , "set");
                        startActivity(intent);
                    }

                }
                else if (id == R.id.todo) {
                    lastCheckedId = R.id.todo;
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new Fragment2()).commit();
                    toolbar.setTitle("TO-DOs");
                } else if (id == R.id.setting) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this , SettingActivity.class);
                    startActivity(intent);
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (backPressed){
                finish();
            }else{
                ToastUtil.toast("再按一次退出程序");
                backPressed = true;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backPressed = false;
                    }
                } , 3000L);
            }
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (lastCheckedId == R.id.todo){
            getMenuInflater().inflate(R.menu.main, menu);
        }else if (lastCheckedId == R.id.diary){
            getMenuInflater().inflate(R.menu.diary_menu , menu);
        }else if (lastCheckedId == R.id.encryptDiary){
            getMenuInflater().inflate(R.menu.encrypt_menu , menu);
        }
        return true;
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            DelectSelectedReceiver.notifyReceiver();
        }else if (id == R.id.action_search){
            Intent intent = new Intent();
            intent.setClass(MainActivity.this , SearchActivity.class);
            startActivity(intent);
        }else if (id == R.id.action_filter){
            Intent intent = new Intent();
            intent.setClass(MainActivity.this , CalendarSelectActivity.class);
            startActivityForResult(intent , myRequestCode);
        }else if (id == R.id.action_my){
            Intent intent = new Intent();
            intent.setClass(MainActivity.this , SettingActivity.class);
            startActivity(intent);
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == myRequestCode && resultCode == CalendarSelectActivity.resultCode){
            String dateStr = data.getStringExtra("date");
            DiaryLoadByDateReceiver.notifyReceiver(dateStr);
        }
    }
}
