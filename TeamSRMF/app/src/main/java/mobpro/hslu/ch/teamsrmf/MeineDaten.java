package mobpro.hslu.ch.teamsrmf;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MeineDaten extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meine_daten);
    }

    public void saveOwnDataClicked(View v){
        //// TODO: 17.05.2016 fülle eigener Benutzer ab
        findViewById(R.id.)
        Benutzer newUser = Benutzer();
        MainActivity.manager.addUser(newUser);
        TabActivity tabs = (TabActivity) getParent();
        tabs.getTabHost().setCurrentTabByTag("Freunde");
    }

    public void setztePositionClicked(View v){
        Intent meinePositionActivity = new Intent(this, MeinePosition.class);
        startActivity(meinePositionActivity);
    }

    public void freundeAufrufenClicked(View v){
        Intent freundeActivity = new Intent(this, MeineFreunde.class);
        startActivity(freundeActivity);
    }

    public void karteAufrufenClicked(View v){
        Intent karteActivity = new Intent(this, Karte.class);
        startActivity(karteActivity);
    }
}
