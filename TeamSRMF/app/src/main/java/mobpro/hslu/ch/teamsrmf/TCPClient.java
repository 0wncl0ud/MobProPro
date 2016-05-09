package mobpro.hslu.ch.teamsrmf;


import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by Manuel on 03.05.2016.
 */
public class TCPClient{

    public static final int SERVERPORT = 4711;
    private static final String SERVER_IP = "10.0.2.2";
    private Socket socket;
    private boolean workDone = false;

    private ArrayList<Benutzer> list;


     public TCPClient() {
        list = new ArrayList<>();
         workDone = false;
        JsonLoader loader = new JsonLoader();
        loader.execute("");
    }

    public ArrayList<Benutzer> getListOfBenutzer(){
        return list;
    }

    public Boolean getState(){
        return workDone;
    }

    class JsonLoader extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String test = "";
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.println(params[0]);
                test = in.readLine();
                out.flush();
                out.close();
                in.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return test;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject json = new JSONObject(s);
                JSONArray benutzer = json.getJSONArray("Benutzer");
                for(int i = 0; i < benutzer.length(); i++){
                    list.add(new Benutzer(benutzer.getJSONObject(i).getString("Name"),
                            benutzer.getJSONObject(i).getString("Studiengang"),
                            "rot",
                            Integer.parseInt(benutzer.getJSONObject(i).getJSONArray("Position").getString(0)),
                            Integer.parseInt(benutzer.getJSONObject(i).getJSONArray("Position").getString(1))));
                }
                workDone = true;
            }
            catch (JSONException ex){
                System.out.println(ex.getMessage());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
