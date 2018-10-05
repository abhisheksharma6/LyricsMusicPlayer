package com.vs.lyricsmusicplayer.view;

import java.util.List;

/**
 * Created by Android-Dev2 on 8/30/2018.
 */

public interface ILrcView {

    void setLrc(List<LrcRow> lrcRows);

    /**
     * seek lyric row to special time
     * @time time to be seek
     *
     */
    void seekLrcToTime(long time);

    void setListener(LrcViewListener l);

    public static interface LrcViewListener {

        /**
         * when lyric line was seeked by user
         */
        void onLrcSeeked(int newPosition, LrcRow row);
    }
}
