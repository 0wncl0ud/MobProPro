package mobpro.hslu.ch.teamsrmf;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Manuel on 03.05.2016.
 */
public class TCPClient extends Activity{

    public static final int SERVERPORT = 4711;
    private static final String SERVER_IP = "10.0.2.2";
    private Socket socket;

    private String name;
    private String studienrichtung;
    private int positionX, positionY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpclient);
        new Thread(new ClientThread()).start();
        JsonLoader loader = new JsonLoader();
        loader.execute("test");
    }


    class JsonLoader extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String test = "";
            try {
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

            TextView txtText = (TextView)findViewById(R.id.TxtTest);
            try{
                JSONObject json = new JSONObject(s);
                name = json.getString("Name");
                studienrichtung = json.getString("Studiengang");
                positionX = json.getJSONArray("Position").getJSONObject(0).getInt("x");
                positionY = json.getJSONArray("Position").getJSONObject(0).getInt("y");
                txtText.setText("Name: " + name + "\nStudienrichtung:  " + studienrichtung + "\nPosition: " + positionX + " " + positionY);
            }
            catch (JSONException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

                socket = new Socket(serverAddr, SERVERPORT);

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }

}
