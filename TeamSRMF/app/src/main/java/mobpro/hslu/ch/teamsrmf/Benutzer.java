package mobpro.hslu.ch.teamsrmf;

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

    public Date getTimeStamp(){ return mTimeStamp; }

    public String getSemester() { return mSemester; }

    public void setName(String name, BenuterManager manager){
        mOldName = mName;
        mName = name;
        manager.editUser(this);
    }

    public void setStudienrichtung(String studienrichtung, BenuterManager manager){
        mOldName = mName;
        mStudienrichtung = studienrichtung;
        manager.editUser(this);
    }
    public void setSemster(String semster, BenuterManager manager){
        mOldName = mName;
        mSemester = semster;
        manager.editUser(this);
    }
    public void setFarbe(String farbe, BenuterManager manager){
        mOldName = mName;
        mFarbe = farbe;
        manager.editUser(this);
    }
    public void setX(int x, BenuterManager manager){
        mOldName = mName;
        mXposition = x;
        manager.editUser(this);
    }

    public void setY(int y, BenuterManager manager){
        mOldName = mName;
        mYposition = y;
        manager.editUser(this);
    }

    public void setTimeStamp(Date date, BenuterManager manager){
        mOldName = mName;
        mTimeStamp = date;
        manager.editUser(this);
    }

}
