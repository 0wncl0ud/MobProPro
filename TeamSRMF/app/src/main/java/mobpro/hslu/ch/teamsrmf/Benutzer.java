package mobpro.hslu.ch.teamsrmf;

import java.util.Date;

/**
 * Created by Manuel on 09.05.2016.
 */
public class Benutzer {
    private String mName, mFarbe, mStudienrichtung;
    private int mXposition, mYposition;
    private Date mTimeStamp;

    public Benutzer(String name, String studienrichtungm, String farbe, int x, int y, Date timeStamp){
        mName = name;
        mFarbe = farbe;
        mStudienrichtung = studienrichtungm;
        mXposition = x;
        mYposition = y;
        mTimeStamp = timeStamp;
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

    public Date getTimeStamp(){
        return mTimeStamp;
    }

}
