/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobpro.hslu.ch.teamsrmf;

import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Manuel
 */
public class BenutzerManager {
    private FileHandler file;
    private ArrayList<User> mList;
    
    public BenutzerManager(){
        file = new FileHandler("test.txt");
        mList = new ArrayList<>();
        JSONObject JsonObj = file.readFromFile();
        this.parseToList(JsonObj);
    }
    
    public void addBenutzer(User user){
        mList.add(user);    
        file.saveToFile(mList);
    }
    
    public void editUser(User user){
        //loader.execute(user);
    }
        
    public void editBenutzer(User user){
        for(int i=0; i < mList.size();i++){
            if(mList.get(i).getName().equalsIgnoreCase(user.getOldName())){
                mList.get(i).setName(user.getName());
                mList.get(i).setColor(user.getColor());
                mList.get(i).setTerm(user.getTerm());
                mList.get(i).setFieldOfStudy(user.getFieldOfStudy());
                mList.get(i).setXposition(user.getXposition());
                mList.get(i).setYposition(user.getYposition());        
                mList.get(i).setTimeStamp();               
            }
        }
        file.saveToFile(mList);
    }
    
    private ArrayList<User> parseToList(JSONObject obj){
        try{
            JSONArray benutzer = obj.getJSONArray("Benutzer");
            for(int i = 0; i < benutzer.length(); i++){
                    //String date = benutzer.getJSONObject(i).getString("TimeStamp");
                    //DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss");
                    //Date result =  df.parse(date);
                    //TODO parse date correct!
                    Date result = new Date();
                    mList.add(new User(benutzer.getJSONObject(i).getString("Name"),
                                    benutzer.getJSONObject(i).getString("Studiengang"),
                                    benutzer.getJSONObject(i).getString("Semester"),
                                    "rot",
                                    Integer.parseInt(benutzer.getJSONObject(i).getJSONArray("Position").getString(0)),
                                    Integer.parseInt(benutzer.getJSONObject(i).getJSONArray("Position").getString(1)),
                                    result));
            }    
        }
        catch(JSONException ex){
            System.out.print(ex.getMessage());
        }
        return mList;
    }
    
    public ArrayList<User> getList(){
        return mList;
    }
}
