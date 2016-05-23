package mobpro.hslu.ch.teamsrmf;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Manuel on 10.05.2016.
 */
public class UserManager {

    private static UserManager instance;
    private static final int SERVERPORT = 4711;
    private static final String SERVER_IP = "10.0.2.2";

    private static ArrayList<User> mMeineFreunde, mViewFreunde, mDatenbank;
    private static User mMeineDaten;
    private static Socket socket;
    private static boolean mBusy;

    private String mBenutzerFileName;
    private String mFreundeFileName;
    private static Context context;


    private UserManager() {
        mMeineFreunde = new ArrayList<>();
        mViewFreunde = new ArrayList<>();
        mMeineDaten = null;
        mBusy = false;
        mBenutzerFileName = "User.txt";
        mFreundeFileName = "Freunde.txt";
        ArrayList<User> tempList = parseToList(readFromFile(context.getFilesDir().getPath(), mBenutzerFileName));
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


    public static UserManager getInstance(Context pContext) {
        if (instance == null) {
            context = pContext;
            instance = new UserManager();
        }
        return instance;
    }

    public boolean getBusy() {
        return mBusy;
    }


    public void addUser(User user) {
        mBusy = true;
        new DataLoader().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user);
    }

    public void editUser(User user) {
        mMeineDaten = user;
        mBusy = true;
        new DataLoader().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user);
        ArrayList<User> temp = new ArrayList<User>();
        temp.add(mMeineDaten);
        saveToFile(temp, context.getFilesDir().getPath(), mBenutzerFileName);
    }

    public void loadList(){
        mBusy = true;
        User dummy = null;
        new DataLoader().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, dummy);
    }

    public ArrayList<User> getMeineFreunde() {
        return mMeineFreunde;
    }

    public ArrayList<User> getmDatenbank() {
        return mDatenbank;
    }

    public User getmMeineDaten() {
        return mMeineDaten;
    }

    public ArrayList<User> getmViewFreunde() {
        return mViewFreunde;
    }

    public void setmViewFreunde(ArrayList<User> neueViewList) {
        mViewFreunde = neueViewList;
    }

    public void addmMeineFreunde(ArrayList<User> addList) {
        mMeineFreunde = mergeUserList(mMeineFreunde, addList);
        saveToFile(mMeineFreunde, context.getFilesDir().getPath(), mFreundeFileName);
    }

    public void löschemMeineFreunde(ArrayList<User> löschList) {
        mMeineFreunde = löscheUserList(mMeineFreunde, löschList);
        saveToFile(mMeineFreunde, context.getFilesDir().getPath(), mFreundeFileName);
    }

    public ArrayList<User> convertStringToBenutzer(ArrayList<String> nameList, ArrayList<User> userList) {
        ArrayList<User> checkedUserList = new ArrayList<>();
        if (userList != null) {
            for (User user : userList) {
                if (nameList.contains(user.getName())) {
                    checkedUserList.add(user);
                }
            }
        }
        return checkedUserList;
    }

    public ArrayList<User> mergeUserList(ArrayList<User> liste1, ArrayList<User> liste2) {
        if (!liste1.isEmpty()) {
            if (!liste2.isEmpty()) {
                for (User user2 : liste2) {
                    for (User user1 : liste1) {
                        if (user1.getName().equals(user2.getName())) {
                            liste1.remove(user1);
                            break;
                        }
                    }
                    liste1.add(user2);
                }
            }
        } else {
            liste1 = liste2;
        }
        return liste1;
    }

    public ArrayList<User> syncList(ArrayList<User> alleList, ArrayList<User> vorgabeList) {
        ArrayList<User> result=new ArrayList<>();
        if (!alleList.isEmpty()) {
            if (!vorgabeList.isEmpty()) {
                for (User user2 : alleList) {
                    for (User user1 : vorgabeList) {
                        if (user1.getName().equals(user2.getName())) {
                            result.add(user2);
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    public ArrayList<User> löscheUserList(ArrayList<User> vorgabeListe, ArrayList<User> löschListe) {
        if (!vorgabeListe.isEmpty()) {
            if (!löschListe.isEmpty()) {
                for (User user2 : löschListe) {
                    for (User user1 : vorgabeListe) {
                        if (user1.getName().equals(user2.getName())) {
                            vorgabeListe.remove(user1);
                            break;
                        }
                    }
                }
            }
        }
        return vorgabeListe;
    }

    public ArrayList<User> filterUserListName(String name, ArrayList<User> liste) {
        ArrayList<User> resultat=new ArrayList<>();
        if(liste!=null) {
            if (!liste.isEmpty()) {
                for (User user : liste) {
                    if (user.getName().equals(name)) {
                        resultat.add(user);
                        break;
                    }
                }
            }
        }
        return resultat;
    }

        public ArrayList<String> convertBenutzerToString (ArrayList <User> userList) {
            ArrayList<String> nameList = new ArrayList<String>();
            if (userList != null) {
                for (User user : userList) {
                    nameList.add(user.getName());
                }
            }
            return nameList;
        }


        class DataLoader extends AsyncTask<User, Void, Boolean> {
            @Override
            protected Boolean doInBackground(User... params) {
                ArrayList<User> received = null;
                mBusy = true;
                try {
                    InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                    socket = new Socket(serverAddr, SERVERPORT);
                    ObjectOutputStream outObj = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream inObj = new ObjectInputStream(socket.getInputStream());
                    //send User Object
                    outObj.writeObject(params[0]);
                    outObj.flush();
                    //receive ArrayList of User
                    received = (ArrayList<User>) inObj.readObject();

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

    public void saveToFile(ArrayList<User> list, String path, String fileName) {
        StringBuilder data = new StringBuilder();
        data.append("{\n\t\"User\": [{\n");
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

    private ArrayList<User> parseToList(JSONObject obj) {
        ArrayList<User> userListe = new ArrayList<>();
        try {
            JSONArray benutzer = obj.getJSONArray("User");
            for (int i = 0; i < benutzer.length(); i++) {
                //String date = benutzer.getJSONObject(i).getString("TimeStamp");
                //DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss");
                //Date result =  df.parse(date);
                //TODO parse date correct!
                Date result = new Date();
                userListe.add(new User(benutzer.getJSONObject(i).getString("Name"),
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
        return userListe;
    }
}
