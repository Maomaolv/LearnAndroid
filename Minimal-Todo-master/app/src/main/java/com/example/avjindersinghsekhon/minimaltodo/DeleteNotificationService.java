package com.example.avjindersinghsekhon.minimaltodo;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.UUID;

/*
@moss

在Android开发中，我们或许会碰到这么一种业务需求，
一项任务分成几个子任务，子任务按顺序先后执行，子任务全部执行完后，这项任务才算成功。
那么，利用几个子线程顺序执行是可以达到这个目的的，但是每个线程必须去手动控制，
而且得在一个子线程执行完后，再开启另一个子线程。
或者，全部放到一个线程中让其顺序执行。这样都可以做到，但是，
如果这是一个后台任务，就得放到Service里面，由于Service和Activity是同级的，
所以，要执行耗时任务，就得在Service里面开子线程来执行。
那么，有没有一种简单的方法来处理这个过程呢，答案就是IntentService。

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

意思是说:
IntentService是一个通过Context.startService(Intent)启动可以处理异步请求的Service,
使用时你只需要继承IntentService和重写其中的onHandleIntent(Intent)方法接收一个Intent对象,
在适当的时候会停止自己(一般在工作完成的时候). 所有的请求的处理都在一个工作线程中完成,
它们会交替执行(但不会阻塞主线程的执行),一次只能执行一个请求.
IntentService 实际上是Looper,Handler,Service 的集合体,他不仅有服务的功能,还有处理和循环消息的功能.

这是一个基于消息的服务,每次启动该服务并不是马上处理你的工作,
而是首先会创建对应的Looper,Handler并且在MessageQueue中添加的附带客户Intent的Message对象,
当Looper发现有Message的时候接着得到Intent对象通过在onHandleIntent((Intent)msg.obj)中调用你的处理程序.
处理完后即会停止自己的服务.意思是Intent的生命周期跟你的处理的任务是一致的.
所以这个类用下载任务中非常好,下载任务结束后服务自身就会结束退出.
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

    /*
    @moss
    重写其onHandleIntent(Intent)方法接收一个Intent对象
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        storeRetrieveData = new StoreRetrieveData(this, MainActivity.FILENAME);

        /*
        @moss
        UUID是Universally Unique Identifier的缩写，它是在一定的范围内（从特定的名字空间到全球）唯一的机器生成的标识符。
        UUID具有以下涵义：经由一定的算法机器生成,非人工指定，非人工识别,在特定的范围内重复的可能性极小
        UUID是16字节128位长的数字，通常以36字节的字符串表示
         */
        UUID todoID = (UUID)intent.getSerializableExtra(TodoNotificationService.TODOUUID);

        /*@moss
        loadData() will return storeRetrieveData.loadFromFile();
         */
        mToDoItems = loadData();
        if(mToDoItems!=null){
            for(ToDoItem item : mToDoItems){
                if(item.getIdentifier().equals(todoID)){
                    mItem = item;
                    break;
                }
            }

            if(mItem!=null){
                mToDoItems.remove(mItem);
                dataChanged();
                saveData();
            }

        }

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
