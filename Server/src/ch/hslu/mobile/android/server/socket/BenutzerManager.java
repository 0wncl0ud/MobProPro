/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.mobile.android.server.socket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author Manuel
 */
public class BenutzerManager {
    private FileHandler file;
    private ArrayList<Benutzer> mList;
    
    public BenutzerManager(){
        file = new FileHandler("test.txt");
        JSONObject JsonObj = file.readFromFile();
        this.parseToList(JsonObj);
    }
    
    public void addBenutzer(Benutzer user){
        
    }
    
    public void editBenutzer(Benutzer user){
        
    }
    
    private ArrayList<Benutzer> parseToList(JSONObject obj){
        JSONArray benutzer = obj.getJSONArray("Benutzer");
        for(int i = 0; i < benutzer.length(); i++){
                String date = benutzer.getJSONObject(i).getString("TimeStamp");
                DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss");
                Date result =  df.parse(date);
                mList.add(new Benutzer(benutzer.getJSONObject(i).getString("Name"),
                                benutzer.getJSONObject(i).getString("Studiengang"),
                                "rot",
                                Integer.parseInt(benutzer.getJSONObject(i).getJSONArray("Position").getString(0)),
                                Integer.parseInt(benutzer.getJSONObject(i).getJSONArray("Position").getString(1)),
                                result));
        }        
    }
    
    public ArrayList<Benutzer> getList(){
        ArrayList<Benutzer> list = null;
        return list;
    }
}
