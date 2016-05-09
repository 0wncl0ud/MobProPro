package mobpro.hslu.ch.teamsrmf;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MeineFreunde extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meine_freunde);
    }

    public void löscheFreundeClicked(View v){
        //// TODO: 09.05.2016 lösche hier die markierten freunde
    }

    public void anzeigenFreundeClicked(View v){
        TabActivity tabs = (TabActivity) getParent();
        tabs.getTabHost().setCurrentTabByTag("Karte");
    }
}
