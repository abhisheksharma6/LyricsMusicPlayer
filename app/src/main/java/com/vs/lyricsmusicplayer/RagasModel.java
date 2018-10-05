package com.vs.lyricsmusicplayer;

public class RagasModel {
    private String title, subTitle;

    public RagasModel(){

    }

    public RagasModel(String title, String subTitle){
         this.title = title;
         this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

}
