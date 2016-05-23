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

    private static ArrayList<User> mMyFriends, mViewFriends, mDatenbank;
    private static User mMyData;
    private static Socket socket;
    private static boolean mBusy;

    private String mUserFileName;
    private String mFriendsFileName;
    private static Context context;


    private UserManager() {
        mMyFriends = new ArrayList<>();
        mViewFriends = new ArrayList<>();
        mMyData = null;
        mBusy = false;
        mUserFileName = "User.txt";
        mFriendsFileName = "Freunde.txt";
        ArrayList<User> tempList = parseToList(readFromFile(context.getFilesDir().getPath(), mUserFileName));
        if (!tempList.isEmpty()) {
            mMyData = tempList.get(0);
            mMyData.setOldname(null);
        }
        tempList = parseToList(readFromFile(context.getFilesDir().getPath(), mFriendsFileName));
        if (!tempList.isEmpty()) {
            mMyFriends.addAll(tempList);
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
        mMyData = user;
        mBusy = true;
        new DataLoader().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user);
        ArrayList<User> temp = new ArrayList<User>();
        temp.add(mMyData);
        saveToFile(temp, context.getFilesDir().getPath(), mUserFileName);
    }

    public void loadList(){
        mBusy = true;
        User dummy = null;
        new DataLoader().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, dummy);
    }

    public ArrayList<User> getMyFriends() {
        return mMyFriends;
    }

    public ArrayList<User> getmDatabase() {
        return mDatenbank;
    }

    public User getmMyData() {
        return mMyData;
    }

    public ArrayList<User> getmViewFriends() {
        return mViewFriends;
    }

    public void setmViewFriends(ArrayList<User>newViewList) {
        mViewFriends = newViewList;
    }

    public void addmMyFriends(ArrayList<User> addList) {
        mMyFriends = mergeUserList(mMyFriends, addList);
        saveToFile(mMyFriends, context.getFilesDir().getPath(), mFriendsFileName);
    }

    public void deleteMyFriends(ArrayList<User> deleteList) {
        mMyFriends = deleteUserList(mMyFriends, deleteList);
        saveToFile(mMyFriends, context.getFilesDir().getPath(), mFriendsFileName);
    }

    public ArrayList<User> convertStringToUser(ArrayList<String> nameList, ArrayList<User> userList) {
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

    public ArrayList<User> mergeUserList(ArrayList<User> list1, ArrayList<User> list2) {
        if (!list1.isEmpty()) {
            if (!list2.isEmpty()) {
                for (User user2 : list2) {
                    for (User user1 : list1) {
                        if (user1.getName().equals(user2.getName())) {
                            list1.remove(user1);
                            break;
                        }
                    }
                    list1.add(user2);
                }
            }
        } else {
            list1 = list2;
        }
        return list1;
    }

    public ArrayList<User> syncList(ArrayList<User> unfilteredList, ArrayList<User> filteredList) {
        ArrayList<User> result=new ArrayList<>();
        if (!unfilteredList.isEmpty()) {
            if (!filteredList.isEmpty()) {
                for (User user2 : unfilteredList) {
                    for (User user1 : filteredList) {
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

    public ArrayList<User> deleteUserList(ArrayList<User> baseList, ArrayList<User> deleteList) {
        if (!baseList.isEmpty()) {
            if (!deleteList.isEmpty()) {
                for (User user2 : deleteList) {
                    for (User user1 : baseList) {
                        if (user1.getName().equals(user2.getName())) {
                            baseList.remove(user1);
                            break;
                        }
                    }
                }
            }
        }
        return baseList;
    }

    public ArrayList<User> filterUserListName(String name, ArrayList<User> list) {
        ArrayList<User> result=new ArrayList<>();
        if(list!=null) {
            if (!list.isEmpty()) {
                for (User user : list) {
                    if (user.getName().equals(name)) {
                        result.add(user);
                        break;
                    }
                }
            }
        }
        return result;
    }

    public ArrayList<User> filterUserTerm(String term, ArrayList<User> list) {
        ArrayList<User> result=new ArrayList<>();
        if(list!=null) {
            if (!list.isEmpty()) {
                for (User user : list) {
                    if (user.getTerm().equals(term)) {
                        result.add(user);
                    }
                }
            }
        }
        return result;
    }

    public ArrayList<User> filterUserFieldOfStudy(String fieldOfStudy, ArrayList<User> list) {
        ArrayList<User> result=new ArrayList<>();
        if(list!=null) {
            if (!list.isEmpty()) {
                for (User user : list) {
                    if (user.getFieldOfStudy().equals(fieldOfStudy)) {
                        result.add(user);
                    }
                }
            }
        }
        return result;
    }

        public ArrayList<String> convertUserToString(ArrayList <User> userList) {
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
            data.append("\t\t\"Studiengang\": \"" + list.get(i).getFieldOfStudy() + "\",\n");
            data.append("\t\t\"Farbe\": \"" + list.get(i).getColor() + "\",\n");
            data.append("\t\t\"Semester\": \"" + list.get(i).getTerm() + "\",\n");
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
        File fileUser = new File(path, fileName);
        try {
            Writer writer = new BufferedWriter(new FileWriter(fileUser));
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
            File fileUser = new File(path, fileName);
            BufferedReader reader = new BufferedReader(new FileReader(fileUser));
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
        ArrayList<User> userList = new ArrayList<>();
        try {
            JSONArray user = obj.getJSONArray("User");
            for (int i = 0; i < user.length(); i++) {
                //String date = benutzer.getJSONObject(i).getString("TimeStamp");
                //DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss");
                //Date result =  df.parse(date);
                //TODO parse date correct!
                Date result = new Date();
                userList.add(new User(user.getJSONObject(i).getString("Name"),
                        user.getJSONObject(i).getString("Studiengang"),
                        user.getJSONObject(i).getString("Semester"),
                        "rot",
                        Integer.parseInt(user.getJSONObject(i).getJSONArray("Position").getString(0)),
                        Integer.parseInt(user.getJSONObject(i).getJSONArray("Position").getString(1)),
                        result));
            }
        } catch (JSONException ex) {
            System.out.print(ex.getMessage());
        }
        return userList;
    }
}
