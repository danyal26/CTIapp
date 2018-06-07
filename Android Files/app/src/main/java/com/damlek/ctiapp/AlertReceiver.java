package com.damlek.ctiapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;

/**
 * Created by DAMLEK GROUP.
 */
public class AlertReceiver extends BroadcastReceiver {

    ArrayList<String> detailsList;

    String title, alert, messageContent;

    //called when a broadcast is made targeting this class
    @Override
    public void onReceive(Context context, Intent intent){

        title = intent.getExtras().getString("title");
        alert = intent.getExtras().getString("alert");
        messageContent = intent.getExtras().getString("message_content");
        createNotification(context, title, alert, messageContent);
    }
    public void createNotification(Context context, String title, String alert, String messageContent){

        //build a notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
               .setSmallIcon(R.drawable.icon)
                .setContentTitle(title)
                .setTicker(alert)
                .setContentText(messageContent);

        mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());
    }
}
