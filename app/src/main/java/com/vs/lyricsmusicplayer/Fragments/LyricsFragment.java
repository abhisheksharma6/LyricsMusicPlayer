package com.vs.lyricsmusicplayer.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vs.lyricsmusicplayer.AsynResult;
import com.vs.lyricsmusicplayer.MainActivity;
import com.vs.lyricsmusicplayer.NotificationPanel;
import com.vs.lyricsmusicplayer.PlayActivity;
import com.vs.lyricsmusicplayer.R;
import com.vs.lyricsmusicplayer.view.DefaultLrcBuilder;
import com.vs.lyricsmusicplayer.view.ILrcBuilder;
import com.vs.lyricsmusicplayer.view.ILrcView;
import com.vs.lyricsmusicplayer.view.LrcRow;
import com.vs.lyricsmusicplayer.view.LrcView;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LyricsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LyricsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LyricsFragment extends Fragment {

    public final static String TAG = "MainActivity";
    ILrcView mLrcView;
    private int mPalyTimerDuration = 1000;
    private Timer mTimer;
    private TimerTask mTask;
    private Button play, pause;
    public LrcView lrcView;
    AsynResult asynResultActivity;
    private String activityAssignedValue ="";
    private static final String STRING_VALUE ="stringValue";
    public Context context;
    public double startTime = 0;
    public double finishTime = 0;
    int seekBarChangeTime;
    private Handler myHandler = new Handler();
    MediaPlayer mPlayer;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean musicIsPlaying = false;

    private OnFragmentInteractionListener mListener;

    public LyricsFragment() {
        // Required empty public constructor
      //  this.asynResult = asynResult;
    }




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LyricsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LyricsFragment newInstance(String param1, String param2) {
        LyricsFragment fragment = new LyricsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public String getFromAssets(String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null){
                if(line.trim().equals(""))
                    continue;
                Result += line + "\r\n";
            }
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLrcView = new LrcView(getActivity(), null);

       // View view = inflater.inflate((XmlPullParser) mLrcView, container, false);
        /*if(checkScreenStatus() && MusicPlaying()){
            Log.d("Screen Status:", "Screen Off");
            // showNotification();
            new NotificationPanel(getActivity());
        }*/


        String lrc = getFromAssets("Patake.lrc");
        Log.d(TAG, "lrc:" + lrc);

        ILrcBuilder builder = new DefaultLrcBuilder();
        List<LrcRow> rows = builder.getLrcRows(lrc);

        mLrcView.setLrc(rows);
        //beginLrcPlay();

        mLrcView.setListener(new ILrcView.LrcViewListener() {

            public void onLrcSeeked(int newPosition, LrcRow row) {
                if (mPlayer != null) {
                    Log.d(TAG, "onLrcSeeked:" + row.time);
                    mPlayer.seekTo((int)row.time);
                    seekBarChangeTime = (int) row.time;
                    // Passing seekBarChangeTime to PlayActivity using interface
                    asynResultActivity.success(seekBarChangeTime);
                    Log.d(TAG, "Media Player current Position" + mPlayer.getCurrentPosition());
                }
            }
        });

         mPlayer = MediaPlayer.create(getActivity(), R.raw.patake);

         ((PlayActivity) getActivity()).passVal(new AsynResult() {
            @Override
            public void success(Object o) {

            }

            @Override
            public void failure(String error) {

            }

            @Override
            public void passVal(String name) {
                //Toast.makeText(c, name, Toast.LENGTH_SHORT).show();
                if(name.contains("1")){
                    if(!musicIsPlaying) {
                        musicIsPlaying = true;
                        beginLrcPlay();
                        double startDurationTime = mPlayer.getCurrentPosition();
                        double finalDurationTime = mPlayer.getDuration();
                        asynResultActivity.success(musicIsPlaying);
                        asynResultActivity.passMediaDuration(startDurationTime, finalDurationTime);
                    } else {
                        musicIsPlaying = false;
                        mPlayer.pause();
                        //stopLrcPlay();
                        double startDurationTime = mPlayer.getCurrentPosition();
                        double finalDurationTime = mPlayer.getDuration();
                        asynResultActivity.success(musicIsPlaying);
                        asynResultActivity.passMediaDuration(startDurationTime, finalDurationTime);
                    }
                }
                else if(name.contains("2")){
                    mPlayer.pause();
                    //stopLrcPlay();
                }
            }

            @Override
            public void passMediaDuration(double startDurationTime, double finalDurationTime) {

            }
        });

        return (View) mLrcView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stop();
        }
    }


    private void nextSong(int position){
        mPlayer.reset();
       // mPlayer.setDataSource(songs_url[counter]);
        //mPlayer.prepare();
        mPlayer.start();
    }


    public void beginLrcPlay() {

       // mPlayer = new MediaPlayer();
        try {

           // mPlayer.setDataSource(getFromAssets().openFd("Patake.mp3").getFileDescriptor());
           /* mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "onPrepared");
                    mp.start();
                    if (mTimer == null) {
                        mTimer = new Timer();
                        mTask = new LrcTask();
                        mTimer.scheduleAtFixedRate(mTask, 0, mPalyTimerDuration);
                    }
                }
            });*/

            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                public void onCompletion(MediaPlayer mp) {
                    stopLrcPlay();
                }
            });
           // mPlayer.prepare();
            mPlayer.start();
            if (mTimer == null) {
                mTimer = new Timer();
                mTask = new LrcTask();
                mTimer.scheduleAtFixedRate(mTask, 0, mPalyTimerDuration);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }/* catch (IOException e) {
            e.printStackTrace();
        }*/

        myHandler.postDelayed(UpdateMediaPlayerTime,100);

    }

    private Runnable UpdateMediaPlayerTime = new Runnable() {
        public void run() {

                //  mediaPlayer.seekTo((int) startTime);
                // seekBarProgressTime = 0;

            // startTime = mediaPlayer.getCurrentPosition();

            asynResultActivity.success(mPlayer.getCurrentPosition());
            myHandler.postDelayed(this, 100);
        }
    };

    public void stopLrcPlay(){

        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }


    class LrcTask extends TimerTask{

        long beginTime = -1;

        @Override
        public void run() {
            if(beginTime == -1) {
                beginTime = System.currentTimeMillis();
            }

            final long timePassed = mPlayer.getCurrentPosition();
            if(getActivity() != null){
                getActivity().runOnUiThread(new Runnable() {

                    public void run() {
                        mLrcView.seekLrcToTime(timePassed);
                    }
                });
            }

        }
    };


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = getActivity();
        asynResultActivity = (AsynResult)context;

        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
