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
public class Benutzer implements java.io.Serializable {
    private String mName, mFarbe, mStudienrichtung, mSemester, mOldName;
    private int mXposition, mYposition;
    private Date mTimeStamp;
    private static final long serialVersionUID = 1L;
    
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

    public void setName(String name){
        mName = name;
    }

    public void setFarbe(String farbe){
        mFarbe = farbe;
    }

    public void setStudienrichtung(String studienrichtung){
        mStudienrichtung = studienrichtung;
    }

    public void setXposition(int x){
        mXposition = x;
    }

    public void setYposition( int y){
        mYposition = y;
    }

    public void setOldname(){
        mOldName = "";
    }

    public void setSemster(String semster){
        mSemester = semster;
    }

    public void setTimeStamp(){
        mTimeStamp = new Date();
    }

    public void setName(String name, BenutzerManager manager){
        mOldName = mName;
        mName = name;
        manager.editUser(this);
    }

    public void setStudienrichtung(String studienrichtung, BenutzerManager manager){
        mOldName = mName;
        mStudienrichtung = studienrichtung;
        manager.editUser(this);
    }

    public void setSemster(String semster, BenutzerManager manager){
        mOldName = mName;
        mSemester = semster;
        manager.editUser(this);
    }

    public void setFarbe(String farbe, BenutzerManager manager){
        mOldName = mName;
        mFarbe = farbe;
        manager.editUser(this);
    }

    public void setX(int x, BenutzerManager manager){
        mOldName = mName;
        mXposition = x;
        manager.editUser(this);
    }

    public void setY(int y, BenutzerManager manager){
        mOldName = mName;
        mYposition = y;
        manager.editUser(this);
    }

    public void setTimeStamp(Date date, BenutzerManager manager){
        mOldName = mName;
        mTimeStamp = date;
        manager.editUser(this);
    }

}