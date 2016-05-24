/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobpro.hslu.ch.teamsrmf;

import java.util.Date;

/**
 * Created by Manuel on 09.05.2016.
 */
public class User implements java.io.Serializable {
    private String mName, mColor, mFieldOfStudy, mTerm, mOldName;
    private int mXposition, mYposition;
    private Date mTimeStamp;
    private static final long serialVersionUID = 1L;

    public User(String name, String fieldOfStudy, String term, String color, int x, int y, Date timeStamp){
        mName = name;
        mColor = color;
        mFieldOfStudy = fieldOfStudy;
        mXposition = x;
        mYposition = y;
        mTimeStamp = timeStamp;
        mTerm = term;
    }

    public String getName(){
        return mName;
    }

    public String getColor(){
        return mColor;
    }

    public String getFieldOfStudy(){
        return mFieldOfStudy;
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

    public String getTerm() { return mTerm; }

    public void setName(String name){
        mOldName = mName;
        mName = name;
    }

    public void setColor(String color){
        mColor = color;
    }

    public void setFieldOfStudy(String fieldOfStudy){
        mFieldOfStudy = fieldOfStudy;
    }

    public void setXposition(int x){
        mXposition = x;
    }

    public void setYposition( int y){
        mYposition = y;
    }

    public void setOldname(String name){
        mOldName = name;
    }

    public void setTerm(String term){
        mTerm = term;
    }

    public void setTimeStamp(){
        mTimeStamp = new Date();
    }

}