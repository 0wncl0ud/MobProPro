package mobpro.hslu.ch.teamsrmf;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MeineFreunde extends AppCompatActivity {
    private ArrayList<String> freundeStringList = new ArrayList<String>();
    private ArrayList<Benutzer> freundeList = new ArrayList<Benutzer>();
    private  ListView meinFreundeListView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meine_freunde);
        //freundeStringList.add("Hallo");
        freundeList=MainActivity.manager.getMeineFreunde();
        freundeStringList=MainActivity.manager.convertBenutzerToString(freundeList);
        meinFreundeListView=(ListView)findViewById(R.id.freundeList);
        if(meinFreundeListView!=null) {
            adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice, android.R.id.text1, freundeStringList);
            meinFreundeListView.setAdapter(adapter);
        }
    }


   /* @Override
    protected void onResume() {
        super.onResume();
        freundeList=MainActivity.manager.getMeineFreunde();
        freundeStringList=MainActivity.manager.convertBenutzerToString(freundeList);
        adapter.addAll(freundeStringList);
    }*/




    public void löscheFreundeClicked(View v){
        //// TODO: 09.05.2016 lösche hier die markierten freunde
    }

    public void anzeigenFreundeClicked(View v){
        TabActivity tabs = (TabActivity) getParent();
        tabs.getTabHost().setCurrentTabByTag("Karte");
    }

    public void hinzufügenFreundeClicked(View v){
        Intent neueFreundeActivity = new Intent(this, NeueFreunde.class);
        startActivity(neueFreundeActivity);
    }
}
