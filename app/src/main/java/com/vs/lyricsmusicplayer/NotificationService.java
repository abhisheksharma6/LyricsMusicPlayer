package com.vs.lyricsmusicplayer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by Android-Dev2 on 9/10/2018.
 */

public class NotificationService extends Service implements AudioManager.OnAudioFocusChangeListener {
    Notification status;
    private final String LOG_TAG = "NotificationService";
    RemoteViews views, bigViews;
    PlayActivity playActivity = PlayActivity.instance;
    private AudioManager audioManager;
    private boolean notificationIsShown = false;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeAudioFocus();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)){
            showNotification();
           // Toast.makeText(this, "service started", Toast.LENGTH_SHORT).show();
        } else if(intent.getAction().equals(Constants.ACTION.PREV_ACTION)){
            Toast.makeText(this, "Clicked Previous", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Previous");
        } else if(intent.getAction().equals(Constants.ACTION.PLAY_ACTION)){
           // Toast.makeText(this, "Clicked Play", Toast.LENGTH_SHORT).show();
           // Log.i(LOG_TAG, "Clicked Play");
            if(playActivity != null){
                playActivity.clickedPlayMusicButton();
                showNotification();
            }

        } else if(intent.getAction().equals(Constants.ACTION.NEXT_ACTION)){
            Toast.makeText(this, "Clicked Next", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Next");

        } else if(intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)){
            //Log.i(LOG_TAG, "Received Stop Foreground Intent");
            //Toast.makeText(this, "Service Stoped", Toast.LENGTH_SHORT).show();
            notificationIsShown = false;
            stopForeground(true);
            stopSelf();
        }

        if (requestAudioFocus() == false) {
            //Could not gain focus
            stopSelf();
        }

        return START_STICKY;
    }

    private void showNotification(){
        notificationIsShown = true;
      //Using RemoteViews to bind custom layouts into Notification
         views = new RemoteViews(getPackageName(), R.layout.status_bar);
         bigViews = new RemoteViews(getPackageName(), R.layout.status_bar_expanded);

      //showing default album image
        views.setViewVisibility(R.id.status_bar_icon, View.VISIBLE);
        views.setViewVisibility(R.id.status_bar_album_art, View.GONE);
        bigViews.setImageViewBitmap(R.id.status_bar_album_art,
                       Constants.getDefaultAlbumArt(this));

        Intent notificationIntent = new Intent(this, PlayActivity.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                     | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,  notificationIntent, 0);

        Intent previousIntent = new Intent(this, NotificationService.class);
        previousIntent.setAction(Constants.ACTION.PREV_ACTION);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0);

        Intent playIntent = new Intent(this, NotificationService.class);
        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent nextIntent = new Intent(this, NotificationService.class);
        nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        Intent closeIntent = new Intent(this, NotificationService.class);
        closeIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, 0);


        views.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);

        views.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);

        views.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);

        views.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);

        //This function is called to set the required icons according to the current situation
        changeImageDrawable();
       /*
       views.setImageViewResource(R.id.status_bar_play,
                R.drawable.pause);
        bigViews.setImageViewResource(R.id.status_bar_play,
                R.drawable.pause);
         */

        views.setTextViewText(R.id.status_bar_track_name, "Song Title");
        bigViews.setTextViewText(R.id.status_bar_track_name, "Song Title");

        views.setTextViewText(R.id.status_bar_artist_name, "Artist Name");
        bigViews.setTextViewText(R.id.status_bar_artist_name, "Artist Name");

        bigViews.setTextViewText(R.id.status_bar_album_name, "Album Name");

        status = new Notification.Builder(this).build();
        status.contentView = views;
        status.bigContentView = bigViews;
        status.flags = Notification.FLAG_ONGOING_EVENT;
        status.icon = R.mipmap.ic_launcher;
        status.contentIntent = pendingIntent;
        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);

    }


    private void changeImageDrawable(){
        if(playActivity.musicIsPlaying){
            views.setImageViewResource(R.id.status_bar_play,
                    R.drawable.pause);
            bigViews.setImageViewResource(R.id.status_bar_play,
                    R.drawable.pause);
        } else {
            views.setImageViewResource(R.id.status_bar_play,
                    R.drawable.play);
            bigViews.setImageViewResource(R.id.status_bar_play,
                    R.drawable.play);
        }

    }

    @Override
    public void onAudioFocusChange(int focusState) {
        switch (focusState){
            case AudioManager.AUDIOFOCUS_GAIN:
                if(playActivity != null){
                    playActivity.clickedPlayMusicButton();
                    //you have to call this function only when the notification is already shown to user
                    // else no need to call this function again
                    if(notificationIsShown){
                        showNotification();
                    }

                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                if(playActivity != null){
                    playActivity.clickedPlayMusicButton();
                    //you have to call this function only when the notification is already shown to user
                    // else no need to call this function again
                    if(notificationIsShown){
                        showNotification();
                    }

                }
                break;
        }

    }


    private boolean requestAudioFocus(){
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
         if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
             //Focus gained
             return true;
         }
         //Could not gain focus
        return false;
    }


    private boolean removeAudioFocus(){
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == audioManager.abandonAudioFocus(this);
    }

}
