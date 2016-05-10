/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.mobile.android.server.socket;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Manuel
 */
public class FileHandler {
    private String mFileName;
    private JSONObject jsonObject;
    private JSONParser parser;
    
    public FileHandler(String file){
        jsonObject = null;
        parser = new JSONParser();
        mFileName = file;
    }
    
    
    public void saveToFile(JSONObject data){
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
            Object obj = parser.parse(new FileReader(mFileName));
            jsonObject = (JSONObject) obj;
            
            //Debug print out json from file
            System.out.print("[DEBUG]Read from File" + obj + "\n");
            //jsonObject.writeJSONString(out);            
        } catch(IOException | ParseException ex){
            System.out.print(ex.getMessage());
        }   
        return jsonObject;
    }
}
