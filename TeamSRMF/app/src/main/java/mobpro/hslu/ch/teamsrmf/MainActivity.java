package mobpro.hslu.ch.teamsrmf;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TCPClient client = new TCPClient();
//        while (!client.getState()){
//
//      }
//        ArrayList<Benutzer> benutzers = client.getListOfBenutzer();

    }
    public void saveOwnDataClicked(View v){
        Intent filterSettingsActivity = new Intent(this, FilterSettings.class);
        startActivity(filterSettingsActivity);
    }
}
