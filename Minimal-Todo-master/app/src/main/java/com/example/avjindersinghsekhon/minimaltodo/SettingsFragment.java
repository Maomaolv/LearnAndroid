package com.example.avjindersinghsekhon.minimaltodo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceFragment;

/*
@moss
PreferenceActivity是一个非常有用的基类，
当我们开发Android项目时避免不了选项设置，这些设置习惯用Preference来保存。
Android专门为这种Activity提供了便捷的基类PreferenceActivity。
如果继承自Preference则不需要自己控制Preference的读写，PreferenceActivity会为我们处理一切。
PreferenceActivity与普通的Activity不同，它不是使用界面布局文件，而是使用选项设置的布局文件。
选项设置布局文件以PreferenceScreen作为根元素来表示定义一个参数设置界面布局。
从Android 3.0以后官方不再推荐直接让PreferenceActivity加载选项设置布局文件，而是建议使用PreferenceFragment，二者用法类似。
PreferenceFragment的组件很多，包括CheckBoxPreference, EditTextPreference,
ListPreference, SwitchPreference, SeekBarPreference, VolumePreference等。
这些组建的属性定义如下。
(01) android:key是Preferece的id，它是Preferece的唯一标识。
(02) android:title是Preferece的大标题。
(03) android:summary是Preferece的小标题。
(04) android:dialogTitle是对话框的标题。
(05) android:defaultValue是默认值。
(06) android:entries是列表中各项的说明。
(07) android:entryValues是列表中各项的值。
 */

/*
@moss
OnSharedPreferenceChangeListener是Android中SharedPreference文件发生变化的监听器。通常
 */

/*
@moss
这个类大概是： 当点击setting后，进入setting activity，每选中一下主题，都会进入这个Fragment
而fragment 是有自己的生命周期的，所以有 onCreate 之类的阶段，
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{
//    AnalyticsApplication app;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_layout);
//        app = (AnalyticsApplication) getActivity().getApplication();
   }

    /*
    @moss
    if the theme is change to dark theme,
    set the theme of mainactivity to dark theme
    then save the preference
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PreferenceKeys preferenceKeys = new PreferenceKeys(getResources());
        if(key.equals(preferenceKeys.night_mode_pref_key)){

            SharedPreferences themePreferences = getActivity().getSharedPreferences(MainActivity.THEME_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor themeEditor = themePreferences.edit();
            //We tell our MainLayout to recreate itself because mode has changed
            themeEditor.putBoolean(MainActivity.RECREATE_ACTIVITY, true);

            CheckBoxPreference checkBoxPreference = (CheckBoxPreference)findPreference(preferenceKeys.night_mode_pref_key);
            if(checkBoxPreference.isChecked()){
                //Comment out this line if not using Google Analytics
//                app.send(this, "Settings", "Night Mode used");
                themeEditor.putString(MainActivity.THEME_SAVED, MainActivity.DARKTHEME);
            }
            else{
                themeEditor.putString(MainActivity.THEME_SAVED, MainActivity.LIGHTTHEME);
            }
            themeEditor.apply();

            getActivity().recreate();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
