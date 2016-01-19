package com.example.avjindersinghsekhon.minimaltodo;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;

/*

IntentService is a base class for Services that handle asynchronous requests (expressed as Intents) on demand.
Clients send requests through startService(Intent) calls;
the service is started as needed, handles each Intent in turn using a worker thread,
and stops itself when it runs out of work.

This "work queue processor" pattern is commonly used to offload tasks from an application's main thread.
The IntentService class exists to simplify this pattern and take care of the mechanics.
To use it, extend IntentService and implement onHandleIntent(Intent).
IntentService will receive the Intents, launch a worker thread, and stop the service as appropriate.

All requests are handled on a single worker thread --
they may take as long as necessary (and will not block the application's main loop),
 but only one request will be processed at a time.
 */
public class DeleteNotificationService extends IntentService {

    private StoreRetrieveData storeRetrieveData;
    private ArrayList<ToDoItem> mToDoItems;
    private ToDoItem mItem;

    /*
    constructor
     */
    public DeleteNotificationService(){
        super("DeleteNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        storeRetrieveData = new StoreRetrieveData(this, MainActivity.FILENAME);
//
//        /*
//        @moss
//        UUID是Universally Unique Identifier的缩写，它是在一定的范围内（从特定的名字空间到全球）唯一的机器生成的标识符。
//        UUID具有以下涵义：经由一定的算法机器生成,非人工指定，非人工识别,在特定的范围内重复的可能性极小
//        UUID是16字节128位长的数字，通常以36字节的字符串表示
//         */
//        UUID todoID = (UUID)intent.getSerializableExtra(TodoNotificationService.TODOUUID);
//
//        /*@moss
//        loadData() will return storeRetrieveData.loadFromFile();
//         */
//        mToDoItems = loadData();
//        if(mToDoItems!=null){
//            for(ToDoItem item : mToDoItems){
//                if(item.getIdentifier().equals(todoID)){
//                    mItem = item;
//                    break;
//                }
//            }
//
//            if(mItem!=null){
//                mToDoItems.remove(mItem);
//                dataChanged();
//                saveData();
//            }
//
//        }

    }

    private void dataChanged(){
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MainActivity.CHANGE_OCCURED, true);
        editor.apply();
    }

    private void saveData(){
        try{
            storeRetrieveData.saveToFile(mToDoItems);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    @moss
    save the data when onDestroy()
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        saveData();
    }

    private ArrayList<ToDoItem> loadData(){
        try{
            return storeRetrieveData.loadFromFile();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }
}
