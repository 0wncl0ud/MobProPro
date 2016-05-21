package mobpro.hslu.ch.teamsrmf;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
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

    private static ArrayList<Benutzer> mMeineFreunde, mViewFreunde, mDatenbank;
    private static Benutzer mMeineDaten;
    private static Socket socket;
    private static boolean mBusy;

    private String mBenutzerFileName;
    private String mFreundeFileName;
    private static Context context;


    private BenutzerManager() {
        mMeineFreunde = new ArrayList<>();
        mViewFreunde = new ArrayList<>();
        mMeineDaten = null;
        mBusy = false;
        mBenutzerFileName = "Benutzer.txt";
        mFreundeFileName = "Freunde.txt";
        ArrayList<Benutzer> tempList = parseToList(readFromFile(context.getFilesDir().getPath(), mBenutzerFileName));
        if (!tempList.isEmpty()) {
            mMeineDaten = tempList.get(0);
            mMeineDaten.setOldname(null);
        }
        tempList = parseToList(readFromFile(context.getFilesDir().getPath(), mFreundeFileName));
        if (!tempList.isEmpty()) {
            mMeineFreunde.addAll(tempList);
        }
        loadList();
        //TODO wait until finish
    }


    public static BenutzerManager getInstance(Context pContext) {
        if (instance == null) {
            context = pContext;
            instance = new BenutzerManager();
        }
        return instance;
    }

    public boolean getBusy() {
        return mBusy;
    }


    public void addUser(Benutzer user) {
        mBusy = true;
        new DataLoader().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user);
    }

    public void editUser(Benutzer user) {
        mMeineDaten = user;
        mBusy = true;
        new DataLoader().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user);
        ArrayList<Benutzer> temp = new ArrayList<Benutzer>();
        temp.add(mMeineDaten);
        saveToFile(temp, context.getFilesDir().getPath(), mBenutzerFileName);
    }

    public void loadList(){
        mBusy = true;
        Benutzer dummy = null;
        new DataLoader().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, dummy);
    }

    public ArrayList<Benutzer> getMeineFreunde() {
        return mMeineFreunde;
    }

    public ArrayList<Benutzer> getmDatenbank() {
        return mDatenbank;
    }

    public Benutzer getmMeineDaten() {
        return mMeineDaten;
    }

    public ArrayList<Benutzer> getmViewFreunde() {
        return mViewFreunde;
    }

    public void setmViewFreunde(ArrayList<Benutzer> neueViewList) {
        mViewFreunde = neueViewList;
    }

    public void addmMeineFreunde(ArrayList<Benutzer> addList) {
        mMeineFreunde = mergeUserList(mMeineFreunde, addList);
        saveToFile(mMeineFreunde, context.getFilesDir().getPath(), mFreundeFileName);
    }

    public void löschemMeineFreunde(ArrayList<Benutzer> löschList) {
        mMeineFreunde = löscheUserList(mMeineFreunde, löschList);
        saveToFile(mMeineFreunde, context.getFilesDir().getPath(), mFreundeFileName);
    }

    public ArrayList<Benutzer> convertStringToBenutzer(ArrayList<String> nameList, ArrayList<Benutzer> benutzerList) {
        ArrayList<Benutzer> checkedBenutzerList = new ArrayList<>();
        if (benutzerList != null) {
            for (Benutzer benutzer : benutzerList) {
                if (nameList.contains(benutzer.getName())) {
                    checkedBenutzerList.add(benutzer);
                }
            }
        }
        return checkedBenutzerList;
    }

    public ArrayList<Benutzer> mergeUserList(ArrayList<Benutzer> liste1, ArrayList<Benutzer> liste2) {
        if (!liste1.isEmpty()) {
            if (!liste2.isEmpty()) {
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
        } else {
            liste1 = liste2;
        }
        return liste1;
    }

    public ArrayList<Benutzer> syncList(ArrayList<Benutzer> alleList, ArrayList<Benutzer> vorgabeList) {
        ArrayList<Benutzer> result=new ArrayList<>();
        if (!alleList.isEmpty()) {
            if (!vorgabeList.isEmpty()) {
                for (Benutzer benutzer2 : alleList) {
                    for (Benutzer benutzer1 : vorgabeList) {
                        if (benutzer1.getName().equals(benutzer2.getName())) {
                            result.add(benutzer2);
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    public ArrayList<Benutzer> löscheUserList(ArrayList<Benutzer> vorgabeListe, ArrayList<Benutzer> löschListe) {
        if (!vorgabeListe.isEmpty()) {
            if (!löschListe.isEmpty()) {
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

    public ArrayList<Benutzer> filterUserListName(String name, ArrayList<Benutzer> liste) {
        ArrayList<Benutzer> resultat=new ArrayList<>();
        if(liste!=null) {
            if (!liste.isEmpty()) {
                for (Benutzer benutzer : liste) {
                    if (benutzer.getName().equals(name)) {
                        resultat.add(benutzer);
                        break;
                    }
                }
            }
        }
        return resultat;
    }

        public ArrayList<String> convertBenutzerToString (ArrayList < Benutzer > benutzerList) {
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

    public void saveToFile(ArrayList<Benutzer> list, String path, String fileName) {
        StringBuilder data = new StringBuilder();
        data.append("{\n\t\"Benutzer\": [{\n");
        for (int i = 0; i < list.size(); i++) {
            data.append("\t\t\"Name\": \"" + list.get(i).getName() + "\",\n");
            data.append("\t\t\"Studiengang\": \"" + list.get(i).getStudienrichtung() + "\",\n");
            data.append("\t\t\"Farbe\": \"" + list.get(i).getFarbe() + "\",\n");
            data.append("\t\t\"Semester\": \"" + list.get(i).getSemester() + "\",\n");
            data.append("\t\t\"TimeStamp\": \"" + list.get(i).getTimeStamp() + "\",\n");
            data.append("\t\t\"Position\": [\n");
            data.append("\t\t\t\"" + list.get(i).getXposition() + "\",\n");
            data.append("\t\t\t\"" + list.get(i).getYposition() + "\"\n");
            data.append("\t\t]\n");
            if (list.size() > (i + 1))
                data.append("\t}, {\n");
        }
        data.append("\t}]\n");
        data.append("}");
        File fileBenutzer = new File(path, fileName);
        try {
            Writer writer = new BufferedWriter(new FileWriter(fileBenutzer));
            writer.write(data.toString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
    }


    public JSONObject readFromFile(String path, String fileName) {
        JSONObject jsonObject = new JSONObject();
        try {
            File fileBenutzer = new File(path, fileName);
            BufferedReader reader = new BufferedReader(new FileReader(fileBenutzer));
            String line = null;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            jsonObject = new JSONObject(stringBuilder.toString());

            //Debug print out json from file
            //System.out.print("[DEBUG]Read from File" + jsonObject + "\n");
            //jsonObject.writeJSONString(out);
        } catch (IOException | JSONException ex) {
            System.out.print(ex.getMessage());
        }
        return jsonObject;
    }

    private ArrayList<Benutzer> parseToList(JSONObject obj) {
        ArrayList<Benutzer> benutzerListe = new ArrayList<>();
        try {
            JSONArray benutzer = obj.getJSONArray("Benutzer");
            for (int i = 0; i < benutzer.length(); i++) {
                //String date = benutzer.getJSONObject(i).getString("TimeStamp");
                //DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss");
                //Date result =  df.parse(date);
                //TODO parse date correct!
                Date result = new Date();
                benutzerListe.add(new Benutzer(benutzer.getJSONObject(i).getString("Name"),
                        benutzer.getJSONObject(i).getString("Studiengang"),
                        benutzer.getJSONObject(i).getString("Semester"),
                        "rot",
                        Integer.parseInt(benutzer.getJSONObject(i).getJSONArray("Position").getString(0)),
                        Integer.parseInt(benutzer.getJSONObject(i).getJSONArray("Position").getString(1)),
                        result));
            }
        } catch (JSONException ex) {
            System.out.print(ex.getMessage());
        }
        return benutzerListe;
    }
}
