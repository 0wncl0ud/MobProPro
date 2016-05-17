package mobpro.hslu.ch.teamsrmf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class NeueFreunde extends Activity {

    ArrayAdapter<String> adapter;
    int clickCounter;
    private CheckedTextView myCheckedTextView;
    private  ListView userListView;
    private ArrayList<String> neueFreundeStringList = new ArrayList<String>();
    private ArrayList<Benutzer> neueFreundeList = new ArrayList<Benutzer>();
    private boolean selectedFlag=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neue_freunde);

        //--------DEBUG-----
        Benutzer dummy1=new Benutzer("Dummy1", "Elektrotechnik", "2","blue", 200, 100, new Date());
        Benutzer dummy2=new Benutzer("Dummy2", "Elektrotechnik", "2","blue", 250, 100, new Date());
        Benutzer dummy3=new Benutzer("Dummy3", "Elektrotechnik", "2","blue", 300, 100, new Date());
        //getList()
        neueFreundeList.add(dummy1);
        neueFreundeList.add(dummy2);
        neueFreundeList.add(dummy3);
        neueFreundeList=MainActivity.manager.getmDatenbank();

        neueFreundeStringList=MainActivity.manager.convertBenutzerToString(neueFreundeList);
        userListView=(ListView)findViewById(R.id.neueFreundeList);
        if(userListView!=null) {
            adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice, android.R.id.text1, neueFreundeStringList);
            userListView.setAdapter(adapter);
        }
    }



    public void AllClicked(View v){
        //// TODO: 10.05.2016 Markiere alle

    }

    public void NoneClicked(View v){
        //// TODO: 10.05.2016 demarkiere alle
    }

    public void wendeFilterAnClicked(View v){
        //Todo  Wende den Filter an und gib die restlichen Objekte auf der Liste aus
    }

    public void AbbrechenNeueFreundeClicked(View v){
        Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.putExtra("Tab","Freunde");
        startActivity(mainActivity);
    }


    public void speicherBenutzerListeClicked(View v){
        ArrayList<String> neueFreundeStringListChecked = new ArrayList<String>();
        SparseBooleanArray checked = userListView.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if (checked.valueAt(i)){
                neueFreundeStringListChecked.add(adapter.getItem(position));
            }
        }
        MainActivity.manager.addmMeineFreunde(MainActivity.manager.convertStringToBenutzer(neueFreundeStringListChecked,neueFreundeList));
        Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.putExtra("Tab","Freunde");
        startActivity(mainActivity);
    }

    
    
}
