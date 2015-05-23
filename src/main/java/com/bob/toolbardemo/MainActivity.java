package com.bob.toolbardemo;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {//ActionBar已经被弃用，单纯的Activity是没有状态栏的

    private Toolbar toolbar;
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout drawerLayout;
    private LeftMenuFrag leftMenuFrag;
    Timer tExit;
    private boolean isExit= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        /*
        有疑问，为什么静态设置不生效呢？
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setSubtitle("firstPage");*/


        leftMenuFrag = new LeftMenuFrag();
        getSupportFragmentManager().beginTransaction().add(R.id.left_menu, leftMenuFrag).commit();

    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_toc_white_24dp);
        toolbar.setTitle("ToolBar");
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);

        mToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.hello_world, R.string.action_settings);

        mToggle.syncState();
        drawerLayout.setDrawerListener(mToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);//在菜单中找到对应空间的item
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        Log.d("Tag", "menu create");
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(MainActivity.this, "onExpand", Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(MainActivity.this, "Collapse", Toast.LENGTH_LONG).show();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        exitBy2Click();
    }


    private void exitBy2Click() {
        tExit = null;//释放计时器堆内存
        if (!isExit) {//为假，初始化计时器开始计时，为真表示延时任务未被执行，而进入了else
            isExit = true;//开始计时的标识
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();

            tExit.schedule(new TimerTask() {//计划一个延时任务
                @Override
                public void run() {//该任务被执行，则表示两秒之内没有再次触发onBackPressed方法，isExit标记被重置为假。本次为时2s的倒计时结束
                    isExit = false;
                }
            }, 2000);//两秒之后执行run里的代码块，结束本次对第二次连击的监听（异步执行计时任务，也可以理解为对子线程的一个sleep）
        } else {
            finish();
            System.exit(0);//鉴于活动栈里没有活动了，可暂时不使用这段强制退出代码，不过还是建议使用
        }
    }
}