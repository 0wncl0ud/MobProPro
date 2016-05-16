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

    private static ArrayList<Benutzer> mMeineFreunde, mDatenbank;
    private static Benutzer mMeineDaten;
    private static Socket socket;
    private static DataLoader loader;
    private static boolean mBusy;


    private BenutzerManager(){
        mMeineFreunde=new ArrayList<>();
        mMeineDaten = null;
        mBusy = false;
        loader = new DataLoader();
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
        loader.execute(user);
   }

    public void editUser(Benutzer user){
        mMeineDaten=user;
        mBusy = true;
        loader.execute(user);
    }

    public ArrayList<Benutzer> loadList(){
        mBusy = true;
        Benutzer dummy = null;
        loader.execute(dummy);
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

    public void addmMeineFreunde(ArrayList<Benutzer> addList){
        mMeineFreunde.addAll(addList);
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
            mBusy = user;
            //TODO methode wird nicht aufgerufen!!!
        }
    }
}
