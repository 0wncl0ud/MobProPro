/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.mobile.android.server.socket;

import java.util.Date;

/**
 * Created by Manuel on 09.05.2016.
 */
public class Benutzer implements java.io.Serializable {
    private String mName, mFarbe, mStudienrichtung, mSemester, mOldName;
    private int mXposition, mYposition;
    private Date mTimeStamp;

    public Benutzer(String name, String studienrichtungm, String semester,String farbe, int x, int y, Date timeStamp){
        mName = name;
        mFarbe = farbe;
        mStudienrichtung = studienrichtungm;
        mXposition = x;
        mYposition = y;
        mTimeStamp = timeStamp;
        mSemester = semester;
    }

    public String getName(){
        return mName;
    }

    public String getFarbe(){
        return mFarbe;
    }

    public String getStudienrichtung(){
        return mStudienrichtung;
    }

    public int getXposition(){
        return mXposition;
    }

    public int getYposition(){
        return mYposition;
    }
    
    public String getOldName(){
        return mOldName;
    }

    public Date getTimeStamp(){ return mTimeStamp; }

    public String getSemester() { return mSemester; }

}

