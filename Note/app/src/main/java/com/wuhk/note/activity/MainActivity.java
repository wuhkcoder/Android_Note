package com.wuhk.note.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.xuan.bigdog.lib.widgets.title.DGTitleLayout;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private NavigationView navigationView;
    private DrawerLayout drawer;

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
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content , new Fragment1()).commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.diary) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new Fragment1()).commit();
                    toolbar.setTitle("Diary");

                } else if (id == R.id.todo) {
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            if (toolbar.getTitle().equals("TO_DOs")){
                DelectSelectedReceiver.notifyReceiver();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
