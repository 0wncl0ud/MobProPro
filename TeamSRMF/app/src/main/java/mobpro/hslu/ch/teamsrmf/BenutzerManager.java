package mobpro.hslu.ch.teamsrmf;

import android.os.AsyncTask;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

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


    private BenutzerManager(){
        if(mMeineFreunde==null){
            mMeineFreunde=new ArrayList<>();
        }
       // mMeineFreunde = null;     //löschte die Array Liste!
        if(mMeineDaten==null) {
            mMeineDaten = null;
        }
        loader = new DataLoader();
        //TODO wait until finish
    }

    public static BenutzerManager getInstance() {
        if(instance == null) {
            instance = new BenutzerManager();
        }
        return instance;
    }


    public void addUser(Benutzer user){
        loader.execute(user);
    }

    public void editUser(Benutzer user){
        mMeineDaten=user;
        loader.execute(user);
    }

    public ArrayList<Benutzer> loadList(){
        Benutzer dummy = null;
        loader.execute(dummy);
        //TODO make some sleep while loading data
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


    class DataLoader extends AsyncTask<Benutzer, Void, ArrayList<Benutzer>> {
        @Override
        protected ArrayList<Benutzer> doInBackground(Benutzer... params) {
            ArrayList<Benutzer> received = null;
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
            return received;
        }

        @Override
        protected void onPostExecute(ArrayList<Benutzer> user) {
            super.onPostExecute(user);

            mDatenbank = user;
        }
    }
}
