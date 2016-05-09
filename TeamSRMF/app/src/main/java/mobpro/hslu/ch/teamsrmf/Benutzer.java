package mobpro.hslu.ch.teamsrmf;

/**
 * Created by Manuel on 09.05.2016.
 */
public class Benutzer {
    private String mName, mFarbe, mStudienrichtung;
    private int mXposition, mYposition;

    public Benutzer(String name, String studienrichtungm, String farbe, int x, int y){
        mName = name;
        mFarbe = farbe;
        mStudienrichtung = studienrichtungm;
        mXposition = x;
        mYposition = y;
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

}
