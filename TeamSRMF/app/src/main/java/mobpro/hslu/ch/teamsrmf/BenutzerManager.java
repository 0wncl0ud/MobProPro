package mobpro.hslu.ch.teamsrmf;

import android.os.AsyncTask;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Manuel on 10.05.2016.
 */
public class BenutzerManager {

    private static BenutzerManager instance;
    private static final int SERVERPORT = 4711;
    private static final String SERVER_IP = "10.0.2.2";

    private static ArrayList<Benutzer> mMeineFreunde,mViewFreunde, mDatenbank;
    private static Benutzer mMeineDaten;
    private static Socket socket;
    private static boolean mBusy;


    private BenutzerManager(){
        mMeineFreunde=new ArrayList<>();
        mViewFreunde=new ArrayList<>();
        mMeineDaten = null;
        mBusy = false;
        //TODO wait until finish
    }


    public static BenutzerManager getInstance() {
        if (instance == null) {
            instance = new BenutzerManager();
        }
        return instance;
    }

    public boolean getBusy(){
        return mBusy;
    }


    public void addUser(Benutzer user){
        mBusy = true;
        new DataLoader().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user);
   }

    public void editUser(Benutzer user){
        mMeineDaten = user;
        mBusy = true;
    }

    public ArrayList<Benutzer> loadList(){
        mBusy = true;
        Benutzer dummy = null;
        new DataLoader().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, dummy);
        return mDatenbank;
    }

    public ArrayList<Benutzer> getMeineFreunde(){
        return mMeineFreunde;
    }

    public ArrayList<Benutzer> getmDatenbank(){
        return mDatenbank;
    }

    public Benutzer getmMeineDaten(){
        return mMeineDaten;
    }

    public ArrayList<Benutzer> getmViewFreunde(){
        return mViewFreunde;
    }

    public void setmViewFreunde(ArrayList<Benutzer> neueViewList){
        mViewFreunde=neueViewList;
    }

    public void addmMeineFreunde(ArrayList<Benutzer> addList){
        mMeineFreunde=mergeUserList(mMeineFreunde,addList);
    }

    public void löschemMeineFreunde(ArrayList<Benutzer> löschList){
        mMeineFreunde=löscheUserList(mMeineFreunde,löschList);
    }

    public ArrayList<Benutzer> convertStringToBenutzer(ArrayList<String> nameList, ArrayList<Benutzer> benutzerList){
        ArrayList<Benutzer> checkedBenutzerList=new ArrayList<>();
        if (benutzerList != null) {
            for (Benutzer benutzer : benutzerList) {
                if (nameList.contains(benutzer.getName())) {
                    checkedBenutzerList.add(benutzer);
                }
            }
        }
        return checkedBenutzerList;
    }

    public ArrayList<Benutzer> mergeUserList(ArrayList<Benutzer> liste1, ArrayList<Benutzer> liste2){
        if (!liste1.isEmpty()) {
            if(!liste2.isEmpty()) {
                for (Benutzer benutzer2 : liste2) {
                    for (Benutzer benutzer1 : liste1) {
                        if (benutzer1.getName().equals(benutzer2.getName())) {
                            liste1.remove(benutzer1);
                            break;
                        }
                    }
                    liste1.add(benutzer2);
                }
            }
        }
        else{
            liste1=liste2;
        }
        return liste1;
    }

    public ArrayList<Benutzer> löscheUserList(ArrayList<Benutzer> vorgabeListe, ArrayList<Benutzer> löschListe){
        if (!vorgabeListe.isEmpty()) {
            if(!vorgabeListe.isEmpty()) {
                for (Benutzer benutzer2 : löschListe) {
                    for (Benutzer benutzer1 : vorgabeListe) {
                        if (benutzer1.getName().equals(benutzer2.getName())) {
                            vorgabeListe.remove(benutzer1);
                            break;
                        }
                    }
                }
            }
        }
        return vorgabeListe;
    }

    public ArrayList<String> convertBenutzerToString(ArrayList<Benutzer> benutzerList) {
        ArrayList<String> nameList = new ArrayList<String>();
        if (benutzerList != null) {
            for (Benutzer benutzer : benutzerList) {
                nameList.add(benutzer.getName());
            }
        }
        return nameList;
    }


    class DataLoader extends AsyncTask<Benutzer, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Benutzer... params) {
            ArrayList<Benutzer> received = null;
            mBusy = true;
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
                ObjectOutputStream outObj = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inObj = new ObjectInputStream(socket.getInputStream());
                //send Benutzer Object
                outObj.writeObject(params[0]);
                outObj.flush();
                //receive ArrayList of Benutzer
                received = (ArrayList<Benutzer>) inObj.readObject();

                inObj.close();
                outObj.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mDatenbank = received;
            return false;
        }

        @Override
        protected void onPostExecute(Boolean user) {
            mBusy = false;
        }
    }
}
