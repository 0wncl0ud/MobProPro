package mobpro.hslu.ch.teamsrmf;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MeineFreunde extends AppCompatActivity {
    private ArrayList<String> freundeStringList = new ArrayList<String>();
    private ArrayList<Benutzer> freundeList = new ArrayList<Benutzer>();
    private  ListView meinFreundeListView;
    ArrayAdapter<String> adapter;
    private BenutzerManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meine_freunde);
        //freundeStringList.add("Hallo");
        setListe();
    }

    public void löscheFreundeClicked(View v){
        //// TODO: 09.05.2016 lösche hier die markierten freunde
        ArrayList<String> freundeStringListChecked = new ArrayList<String>();
        ArrayList<String> freundeListChecked = new ArrayList<String>();
        SparseBooleanArray checked = meinFreundeListView.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if (checked.valueAt(i)){
                freundeStringListChecked.add(adapter.getItem(position));
            }
        }
        MainActivity.manager.löschemMeineFreunde(MainActivity.manager.convertStringToBenutzer(freundeStringListChecked,freundeList));
        setListe();
    }

    private void setListe(){
        freundeList=MainActivity.manager.getMeineFreunde();
        freundeStringList=MainActivity.manager.convertBenutzerToString(freundeList);
        meinFreundeListView=(ListView)findViewById(R.id.freundeListView);
        manager=BenutzerManager.getInstance();
        if(meinFreundeListView!=null) {
            adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice, android.R.id.text1, freundeStringList);
            meinFreundeListView.setAdapter(adapter);
        }
    }
    public void anzeigenFreundeClicked(View v){
        ArrayList<String> freundeStringListChecked = new ArrayList<String>();
        ArrayList<String> freundeListChecked = new ArrayList<String>();
        SparseBooleanArray checked = meinFreundeListView.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if (checked.valueAt(i)){
                freundeStringListChecked.add(adapter.getItem(position));
            }
        }
        MainActivity.manager.setmViewFreunde(MainActivity.manager.convertStringToBenutzer(freundeStringListChecked,freundeList));
        TabActivity tabs = (TabActivity) getParent();
        tabs.getTabHost().setCurrentTabByTag("Karte");
    }

    public void hinzufügenFreundeClicked(View v){
        Intent neueFreundeActivity = new Intent(this, NeueFreunde.class);
        startActivity(neueFreundeActivity);
    }

    public void speicherBenutzerListeClicked(){

    }
}
