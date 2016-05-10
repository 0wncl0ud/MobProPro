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

    private static final int SERVERPORT = 4711;
    private static final String SERVER_IP = "10.0.2.2";

    private ArrayList<Benutzer> mMeineFreunde, mDatenbank;
    private Benutzer mMeineDaten;
    private Socket socket;
    private DataLoader loader;


    public BenutzerManager(){
        mMeineFreunde = null;
        mMeineDaten = null;
        loader = new DataLoader();
        //TODO wait until finish
    }


    public void addUser(Benutzer user){
        loader.execute(user);
    }

    public void editUser(Benutzer user){
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


    class DataLoader extends AsyncTask<Benutzer, Void, ArrayList<Benutzer>> {
        @Override
        protected ArrayList<Benutzer> doInBackground(Benutzer... params) {
            ArrayList<Benutzer> received = null;
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
                ObjectOutputStream outObj = new ObjectOutputStream(socket.getOutputStream());
                outObj.writeObject(params[0]);
                outObj.flush();
                outObj.close();

                ObjectInputStream inObj = new ObjectInputStream(socket.getInputStream());
                received = (ArrayList<Benutzer>) inObj.readObject();
                inObj.close();
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
