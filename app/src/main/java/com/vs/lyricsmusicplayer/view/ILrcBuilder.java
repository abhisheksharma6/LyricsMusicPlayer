package com.vs.lyricsmusicplayer.view;

import java.util.List;

/**
 * Created by Android-Dev2 on 8/30/2018.
 */

public interface ILrcBuilder {

    List<LrcRow> getLrcRows(String rawLrc);
}
