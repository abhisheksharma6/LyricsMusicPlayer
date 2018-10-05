package com.vs.lyricsmusicplayer;

/**
 * Created by Android-Dev2 on 9/3/2018.
 */

public interface AsynResult<TData> {

    void success(TData data);

    void failure(String error);

    void passVal(String message);

    void passMediaDuration(double startDurationTime, double finalDurationTime);
}


