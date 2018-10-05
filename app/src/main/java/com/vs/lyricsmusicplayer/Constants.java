package com.vs.lyricsmusicplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Android-Dev2 on 9/10/2018.
 */

public class Constants {

    public interface ACTION {
        public static String MAIN_ACTION = "com.vs.lyricsmusicplayer.action.main";
        public static String INIT_ACTION = "com.vs.lyricsmusicplayer.action.init";
        public static String PREV_ACTION = "com.vs.lyricsmusicplayer.action.prev";
        public static String PLAY_ACTION = "com.vs.lyricsmusicplayer.action.play";
        public static String NEXT_ACTION = "com.vs.lyricsmusicplayer.action.next";
        public static String STARTFOREGROUND_ACTION = "com.vs.lyricsmusicplayer.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.vs.lyricsmusicplayer.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }

    public static Bitmap getDefaultAlbumArt(Context context){
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try{
            bm = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.default_album_art, options);

        } catch (Error ee){

        } catch (Exception e){

        }
        return bm;
    }
}
