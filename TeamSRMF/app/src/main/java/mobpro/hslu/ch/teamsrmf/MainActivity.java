package mobpro.hslu.ch.teamsrmf;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;


public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


        TabHost.TabSpec tabDaten = tabHost.newTabSpec("Meine Daten");
        TabHost.TabSpec tabKarte = tabHost.newTabSpec("Karte");
        TabHost.TabSpec tabFreunde = tabHost.newTabSpec("Freunde");


        tabDaten.setIndicator("Meine Daten");
        tabDaten.setContent(new Intent(this,MeineDaten.class));

        tabKarte.setIndicator("Karte");
        tabKarte.setContent(new Intent(this,Karte.class));

        tabFreunde.setIndicator("Freunde");
        tabFreunde.setContent(new Intent(this,MeineFreunde.class));

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tabDaten);
        tabHost.addTab(tabKarte);
        tabHost.addTab(tabFreunde);
      //  Intent intent = new Intent(this, TCPClient.class);
      //  startActivity(intent);
    }

}
