package com.vs.lyricsmusicplayer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vs.lyricsmusicplayer.Fragments.LyricsFragment;

import java.util.concurrent.TimeUnit;

public class PlayActivity extends AppCompatActivity implements LyricsFragment.OnFragmentInteractionListener, AsynResult {
    TextView currentTime, finishTime;
    //Button play, pause;
    ImageView imageView, plus, minus;
    SeekBar seekBar;
    //private MediaPlayer mediaPlayer;

    private double startTime = 0;
    private double finalTime = 0;
    private int seekBarProgressTime = 0;

    private Handler myHandler = new Handler();
    public static int oneTimeOnly = 0;
    AsynResult asynResult;
    boolean musicIsPlaying = false;
    static PlayActivity instance;
    AudioManager audioManager;

   // LyricView mLyricView;

    public PlayActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new LyricsFragment());
        instance = this;
       // getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
       // asynResult = (AsynResult) getApplication();
        /*if(checkScreenIsOff()) {
            if(musicIsPlaying){
                Log.d("Screen Status:", "Screen Off");
                //showNotification();
                new NotificationPanel(this);
            }

        }
*/
        currentTime = (TextView) findViewById(R.id.currentTime);
        finishTime = (TextView) findViewById(R.id.finishTime);
        //play = (Button) findViewById(R.id.play);
       // pause = (Button) findViewById(R.id.pause);
        imageView = (ImageView) findViewById(R.id.imageView);
        plus = (ImageView) findViewById(R.id.plus);
        minus = (ImageView) findViewById(R.id.minus);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setClickable(false);

        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

          //mediaPlayer = MediaPlayer.create(this, R.raw.patake);
       // mLyricView = (LyricView) findViewById(R.id.lyricView);
        // You can call setLyric anytime to change the lyric to another
       // mLyricView.setLyric(LyricUtils.parseLyric(getResources().openRawResource(R.raw.testfile), "UTF-8"));
     //   mLyricView.setLyricIndex(0);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To increase media player volume
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
            }
        });


        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To decrease media player volume
                audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mediaPlayer.start();
                if(!musicIsPlaying){
                    imageView.setImageResource(R.drawable.pause);
                }else {
                    imageView.setImageResource(R.drawable.play);
                }

                clickedPlayMusicButton();
                //mLyricView.play();
                //finalTime = mediaPlayer.getDuration();
                //startTime = mediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekBar.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }

                finishTime.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime)))
                );

                currentTime.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        startTime)))
                );

                seekBar.setProgress((int)startTime);
                myHandler.postDelayed(UpdateSongTime, 100);
            }
        });


        /*
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mediaPlayer.pause();
                asynResult.passVal("2");

              //  mLyricView.stop();

            }
        });
        */
    }


   /*
   private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                 //Here you have to start Media player when phone call ends
                clickedPlayMusicButton();
            } else if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                 //Here you have to pause Media player when phone call comes
                clickedPlayMusicButton();
            }
        }
    };
    */


    public void clickedPlayMusicButton(){
        asynResult.passVal("1");
    }

    public void passVal(AsynResult asynResult) {
        this.asynResult = asynResult;
    }

    public void stopMediaPlayer(){
       // mediaPlayer.pause();
        asynResult.passVal("2");
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = seekBarProgressTime;
              //  mediaPlayer.seekTo((int) startTime);
               // seekBarProgressTime = 0;

           // startTime = mediaPlayer.getCurrentPosition();
            currentTime.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekBar.setProgress((int)startTime);
            //showNotificationOnPanel();
            startServiceEntryPoint();
            myHandler.postDelayed(this, 100);
        }
    };

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void success(Object o) {
        // Successfully getting the seekBarProgressTime from LyricsFragment
        if(o instanceof Boolean){
            musicIsPlaying = (boolean) o;
        }else{
            seekBarProgressTime = (int) o;
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    seekBar.setProgress(seekBarProgressTime);
                    //mediaPlayer.seekTo(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

    }

    @Override
    public void failure(String error) {

    }

    @Override
    public void passVal(String message) {

    }

    @Override
    public void passMediaDuration(double startDurationTime, double finalDurationTime) {
        startTime = startDurationTime;
        finalTime = finalDurationTime;
    }

   /*
     public void showNotification(){
        new NotificationPanel(this);
        finish();
    }
    */


    public boolean checkScreenIsOff(){
        DisplayManager dm = (DisplayManager) this.getSystemService(Context.DISPLAY_SERVICE);
        for (Display display : dm.getDisplays()) {
            if (display.getState() != Display.STATE_ON) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        /*
        if(checkScreenStatus()) {
            //new NotificationPanel(this);
            *//*if(musicIsPlaying){
                Log.d("Screen Status:", "Screen Off");
                //showNotification();
               // new NotificationPanel(this);
            }*//*

        }else{
           // NotificationPanel nPanel = new NotificationPanel(this);
            //nPanel.notificationCancel();
        }
        */
    }


    @Override
    protected void onPause() {
        super.onPause();
        //showNotificationOnPanel();

       /*
       if(checkScreenStatus()) {
            if(musicIsPlaying){
                Log.d("Screen Status:", "Screen Off");
                //showNotification();
                new NotificationPanel(this);
            }

        }
        */
    }

    @Override
    protected void onStop() {
        super.onStop();
        startServiceWhenAppIsNotVisible();
    }

    /*
    public void showNotificationOnPanel(){
        if(checkScreenStatus()) {
            if(musicIsPlaying){
                Log.d("Screen Status:", "Screen Off");
                //showNotification();
                new NotificationPanel(this);
            }
        }
    }
    */

    public void startServiceEntryPoint() {
        if(checkScreenIsOff()){
            if(musicIsPlaying){
                Intent serviceIntent = new Intent(PlayActivity.this, NotificationService.class);
                serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                startService(serviceIntent);
            }
        }
    }


    public void startServiceWhenAppIsNotVisible() {
        if(!checkScreenIsOff()){
            if(musicIsPlaying){
                Intent serviceIntent = new Intent(PlayActivity.this, NotificationService.class);
                serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                startService(serviceIntent);
            }
        }
    }


}
