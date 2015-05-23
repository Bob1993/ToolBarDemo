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

public class MainActivity extends AppCompatActivity {//ActionBar已经被弃用，单纯的Activity是没有状态栏的

    private Toolbar toolbar;
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout drawerLayout;
    private LeftMenuFrag leftMenuFrag;

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
}