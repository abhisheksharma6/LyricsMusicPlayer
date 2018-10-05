package com.vs.lyricsmusicplayer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Android-Dev2 on 9/4/2018.
 */

public class NotificationReturnSlot extends Activity {
    private NotificationReturnSlot ctx;
    private NotificationPanel notificationPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = (String) getIntent().getExtras().get("DO");
        if (action.equals("relativeLayout")){
            Log.i("NotificationReturnSlot", "Relative Layout");
        }
          else if (action.equals("previousSong")) {
            Log.i("NotificationReturnSlot", "previousSong");
            //Your code
        } else if (action.equals("pause")) {
            //Your code
            Log.i("NotificationReturnSlot", "pause");
            notificationPanel = new NotificationPanel(this);
            notificationPanel.changePauseIconToPlay();
          //  playActivity.clickedPlayMusicButton();

        } else if (action.equals("nextSong")) {
            //Your code
            Log.i("NotificationReturnSlot", "nextSong");
        }
        finish();
    }

}
