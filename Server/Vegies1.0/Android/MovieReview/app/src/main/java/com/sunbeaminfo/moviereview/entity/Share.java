package com.sunbeaminfo.moviereview.entity;

import java.io.Serializable;

public class Share implements Serializable {
    private int reviewid;
    private int userid;

    public Share(int reviewid, int userid) {
        this.reviewid = reviewid;
        this.userid = userid;
    }

    public int getReviewid() {
        return reviewid;
    }

    public void setReviewid(int reviewid) {
        this.reviewid = reviewid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
