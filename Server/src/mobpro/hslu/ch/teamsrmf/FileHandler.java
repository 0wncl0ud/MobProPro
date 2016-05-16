/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobpro.hslu.ch.teamsrmf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author Manuel
 */
public class FileHandler {
    private String mFileName;
    private JSONObject jsonObject;
    
    public FileHandler(String file){
        jsonObject = null;
        mFileName = file;
    }
    
    
    public void saveToFile(ArrayList<Benutzer> list){
        StringBuilder data = new StringBuilder();
        data.append("{\n\t\"Benutzer\": [{\n");
        for(int i=0; i < list.size(); i++){
            data.append("\t\t\"Name\": \"" + list.get(i).getName()                    + "\",\n");
            data.append("\t\t\"Studiengang\": \"" + list.get(i).getStudienrichtung()  + "\",\n");
            data.append("\t\t\"Farbe\": \"" + list.get(i).getFarbe()                  + "\",\n");
            data.append("\t\t\"Semester\": \"" + list.get(i).getSemester()            + "\",\n");
            data.append("\t\t\"TimeStamp\": \"" + list.get(i).getTimeStamp()          + "\",\n");
            data.append("\t\t\"Position\": [\n");
            data.append("\t\t\t\"" + list.get(i).getXposition() + "\",\n");
            data.append("\t\t\t\"" + list.get(i).getYposition() + "\"\n");
            data.append("\t\t]\n");
            if(list.size() > (i+1))
                data.append("\t}, {\n");
        }
        data.append("\t}]\n");
        data.append("}");
        
        try{
            Writer writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(mFileName)));
            writer.write(data.toString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
    }
        
    public JSONObject readFromFile(){ 
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(mFileName)));
            String line = null;
            StringBuilder stringBuilder = new StringBuilder();
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            jsonObject = new JSONObject(stringBuilder.toString());
            
            //Debug print out json from file
            System.out.print("[DEBUG]Read from File" + jsonObject + "\n");
            //jsonObject.writeJSONString(out);            
        } catch(IOException | JSONException ex){
            System.out.print(ex.getMessage());
        }   
        return jsonObject;
    }
    
}
