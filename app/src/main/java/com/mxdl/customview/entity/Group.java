package com.mxdl.customview.entity;

/**
 * Description: <Group><br>
 * Author:      mxdl<br>
 * Date:        2019/10/7<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class Group {
    public Group(String title) {
        this.title = title;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
