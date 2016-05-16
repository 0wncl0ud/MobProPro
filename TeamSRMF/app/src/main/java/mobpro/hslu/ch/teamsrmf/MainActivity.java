package mobpro.hslu.ch.teamsrmf;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends TabActivity {
    private TabHost tabHost;
    public static BenutzerManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager=BenutzerManager.getInstance();
        tabHost = (TabHost) findViewById(android.R.id.tabhost);


        TabHost.TabSpec tabDaten = tabHost.newTabSpec("Meine Daten");
        TabHost.TabSpec tabKarte = tabHost.newTabSpec("Karte");
        TabHost.TabSpec tabFreunde = tabHost.newTabSpec("Freunde");


        tabDaten.setIndicator("Meine Daten");
        tabDaten.setContent(new Intent(this, MeineDaten.class));

        tabKarte.setIndicator("Karte");
        tabKarte.setContent(new Intent(this, Karte.class));

        tabFreunde.setIndicator("Freunde");
        tabFreunde.setContent(new Intent(this, MeineFreunde.class));

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tabDaten);
        tabHost.addTab(tabKarte);
        tabHost.addTab(tabFreunde);


        //Welcher Tab soll angezeigt werden?
        Intent intent = getIntent();
        try {
            String indicator = intent.getExtras().getString("Tab");
            if (indicator != null) {
                Log.i("hslu_mobApp", "Indicator:" + indicator);
                Parcelable[] temp = intent.getExtras().getParcelableArray("Benutzerliste");
                tabHost.setCurrentTabByTag(indicator);
            }
        } catch (Exception e) {
        }

        //-----------------------------TEST KOMUNIKATION-------------------------------------------
        //BenutzerManager manager = new BenutzerManager();
        //manager.addUser(new Benutzer("Manuel Felber", "ET", "5", "blau", 12, 345, new Date()));
        //while (manager.getBusy()){

        //}
        //ArrayList<Benutzer> database = manager.getmDatenbank();

        //----------------------------------------------------------------------------------------------

//        while (!client.getState()){
//
//      }
//        ArrayList<Benutzer> benutzers = client.getListOfBenutzer();
    }
}
