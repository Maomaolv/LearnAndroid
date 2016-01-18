package com.example.avjindersinghsekhon.minimaltodo;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.UUID;

/*
@moss
this method is comment out for:
we don't need this service
 */
public class TodoNotificationService extends IntentService {
    public static final String TODOTEXT = "com.avjindersekhon.todonotificationservicetext";
    public static final String TODOUUID = "com.avjindersekhon.todonotificationserviceuuid";
    private String mTodoText;
    private UUID mTodoUUID;
    private Context mContext;

    public TodoNotificationService(){
        super("TodoNotificationService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        mTodoText = intent.getStringExtra(TODOTEXT);
        mTodoUUID = (UUID)intent.getSerializableExtra(TODOUUID);

        Log.d("OskarSchindler", "onHandleIntent called");
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, ReminderActivity.class);
        i.putExtra(TodoNotificationService.TODOUUID, mTodoUUID);
        Intent deleteIntent = new Intent(this, DeleteNotificationService.class);
        deleteIntent.putExtra(TODOUUID, mTodoUUID);
        //@lv setup new notification
        Notification notification = new Notification.Builder(this)
                //@lv set the first line of text in the platform notification template
                .setContentTitle(mTodoText)
                //@lv set the small icon resource
                .setSmallIcon(R.drawable.ic_done_white_24dp)
                //@lv make this notification automatically dismissed when user touch it
                .setAutoCancel(true)
                //@lv set which notification properties will be inherited from system defaults
                .setDefaults(Notification.DEFAULT_SOUND)
                //@lv supply a PendingIntent to send when the notification is cleared explicitly by the user
                .setDeleteIntent(PendingIntent.getService(this, mTodoUUID.hashCode(), deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                //@lv supply a PendingIntent to be sent when the notification is clicked
                .setContentIntent(PendingIntent.getActivity(this, mTodoUUID.hashCode(), i, PendingIntent.FLAG_UPDATE_CURRENT))
                //@lv combine all of the options what have been set and return a new Notification object
                .build();

        manager.notify(100, notification);
//        Uri defaultRingone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        MediaPlayer mp = new MediaPlayer();
//        try{
//            mp.setDataSource(this, defaultRingone);
//            mp.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
//            mp.prepare();
//            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    mp.release();
//                }
//            });
//            mp.start();
//
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }

    }
}
