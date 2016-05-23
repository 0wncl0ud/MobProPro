package mobpro.hslu.ch.teamsrmf;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MeineFreunde extends AppCompatActivity {
    private ArrayList<String> freundeStringList = new ArrayList<String>();
    private ArrayList<User> freundeList = new ArrayList<User>();
    private  ListView meinFreundeListView;
    ArrayAdapter<String> adapter;
    private UserManager manager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meine_freunde);
        context=getApplicationContext();
        //freundeStringList.add("Hallo");
        setListe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.manager.loadList();
    }

    public void löscheFreundeClicked(View v){
        //// TODO: 09.05.2016 lösche hier die markierten freunde
        ArrayList<String> freundeStringListChecked = new ArrayList<String>();
        //ArrayList<String> freundeListChecked = new ArrayList<String>(); because not used?
        SparseBooleanArray checked = meinFreundeListView.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if (checked.valueAt(i)){
                freundeStringListChecked.add(adapter.getItem(position));
            }
        }
        ////TODO freundliste aktuallisieren mit aktuellerliste
        MainActivity.manager.löschemMeineFreunde(MainActivity.manager.convertStringToBenutzer(freundeStringListChecked,freundeList));
        setListe();
    }

    private void setListe(){
        freundeList=MainActivity.manager.getMeineFreunde();
        freundeStringList=MainActivity.manager.convertBenutzerToString(freundeList);
        meinFreundeListView=(ListView)findViewById(R.id.freundeListView);
        manager= UserManager.getInstance(context);
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
        MainActivity.manager.setmViewFreunde(MainActivity.manager.syncList(manager.getmDatenbank(),manager.convertStringToBenutzer(freundeStringListChecked,freundeList)));
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
