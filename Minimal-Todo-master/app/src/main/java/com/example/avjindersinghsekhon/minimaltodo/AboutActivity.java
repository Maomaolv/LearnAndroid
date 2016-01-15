package com.example.avjindersinghsekhon.minimaltodo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    private TextView mVersionTextView;
    private String appVersion = "0.1";
    private Toolbar toolbar;
    private TextView contactMe;
    String theme;

    //test with git
//    private UUID mId;
    private AnalyticsApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        app = (AnalyticsApplication)getApplication();
        app.send(this);
        //change app theme
        //getSharedPreferences(name, mode)
        theme = getSharedPreferences(MainActivity.THEME_PREFERENCES, MODE_PRIVATE).getString(MainActivity.THEME_SAVED, MainActivity.LIGHTTHEME);
        if(theme.equals(MainActivity.DARKTHEME)){
            //Log.d(TAG,Message) :Debug
            Log.d("OskarSchindler", "One");
            setTheme(R.style.CustomStyle_DarkTheme);
        }
        else{
            Log.d("OskarSchindler", "One");
            setTheme(R.style.CustomStyle_LightTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);

        Intent i = getIntent();
//        mId = (UUID)i.getSerializableExtra(TodoNotificationService.TODOUUID);

        //setup back Arrow
        final Drawable backArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        if(backArrow!=null){
            //设置颜色过滤，这个方法需要我们传入一个ColorFilter参数同样也会返回一个ColorFilter实例
            //PorterDuff.Mode.SRC_ATOP,取下层非交集部分与上层交集部分
            //构造ComposeShader需要 PorterDuffXfermode或者PorterDuff.Mode作为参数
            //设置返回按钮外观
            backArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        }
        try{
            //Return PackageManager instance to find global package information.
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            appVersion = info.versionName;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //mVersionTextView is a TextView,we can find it in R file
        mVersionTextView = (TextView)findViewById(R.id.aboutVersionTextView);
        mVersionTextView.setText(String.format(getResources().getString(R.string.app_version), appVersion));
        //toolbar is a Toolbar,we can find it in R file
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        //contactMe is a TextView,we can find it in R file
        contactMe = (TextView)findViewById(R.id.aboutContactMe);

        //set OnClickListener for TextView contactMe
        contactMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.send(this, "Action", "Feedback");
            }
        });


        //setup SupportActionBar=toolbar
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            //点击返回=true,返回该app的逻辑层级的上一层
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //点击backArrow返回
            getSupportActionBar().setHomeAsUpIndicator(backArrow);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //NavUtils provides helper functionality for applications implementing recommended Android UI navigation patterns.
                if(NavUtils.getParentActivityName(this)!=null){
                    //点击按钮后,回到home页面
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
