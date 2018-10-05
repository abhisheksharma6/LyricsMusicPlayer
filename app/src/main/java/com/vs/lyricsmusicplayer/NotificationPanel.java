package com.vs.lyricsmusicplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;


/**
 * Created by Android-Dev2 on 9/4/2018.
 */

public class NotificationPanel extends Notification {
    private Context parent;
    private NotificationManager nManager;
    private NotificationCompat.Builder nBuilder;
    private RemoteViews remoteView;
    private boolean isPlayed = false;

    public NotificationPanel(){

    }

    public NotificationPanel(Context parent){
        // TODO Auto-generated constructor stub
        this.parent = parent;
        nBuilder = new NotificationCompat.Builder(parent)
                .setContentTitle("Parking Meter")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true);

        remoteView = new RemoteViews(parent.getPackageName(), R.layout.notificationlayout);

        //set the button listeners
        setListeners(remoteView);
        nBuilder.setContent(remoteView);

        nManager = (NotificationManager) parent.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(2, nBuilder.build());
    }

    public void setListeners(RemoteViews view){
        //listener1
        Intent relativeLayout = new Intent(parent, NotificationReturnSlot.class);
        relativeLayout.putExtra("DO", "relativeLayout");
        PendingIntent btn = PendingIntent.getActivity(parent, 0, relativeLayout, 0);
        view.setOnClickPendingIntent(R.id.relativeLayout, btn);

        //listener 2
        Intent previousSong = new Intent(parent,NotificationReturnSlot.class);
        previousSong.putExtra("DO", "previousSong");
        PendingIntent btn1 = PendingIntent.getActivity(parent, 1, previousSong, 0);
        view.setOnClickPendingIntent(R.id.previousSong, btn1);

        //listener 3
        Intent pause = new Intent("com.vs.lyricsmusicplayer.ACTION_PLAY");
        pause.putExtra("DO", "pause");
        PendingIntent btn2 = PendingIntent.getBroadcast(parent, 2, pause, 0);
        view.setOnClickPendingIntent(R.id.pause, btn2);

        //listener 4
        Intent nextSong = new Intent(parent,NotificationReturnSlot.class);
        nextSong.putExtra("DO", "nextSong");
        PendingIntent btn3 = PendingIntent.getActivity(parent, 3, nextSong, 0);
        view.setOnClickPendingIntent(R.id.nextSong, btn3);
    }

    public void notificationCancel() {
        nManager.cancel(2);
    }

    public void changePauseIconToPlay(){
        isPlayed = true;
        remoteView.setImageViewResource(R.id.pause, R.drawable.play);
    }

    public void changePlayIconToPause(){
        isPlayed = false;
        remoteView.setImageViewResource(R.id.pause, R.drawable.pause);
    }
}
