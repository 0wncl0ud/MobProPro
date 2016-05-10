package mobpro.hslu.ch.teamsrmf;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NeueFreunde extends Activity {

    ArrayAdapter<String> adapter;
    int clickCounter;
    private CheckedTextView myCheckedTextView;
    private  ListView userListView;
    private ArrayList<String> neueFreundeList = new ArrayList<String>();
    protected ArrayList<Benutzer> neueFreundeListChecked = new ArrayList<Benutzer>();
    private boolean selectedFlag=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neue_freunde);
        neueFreundeList.add("temp");        //// TODO: 10.05.2016 delete this und fülle die Daten aus dem Personen array ein, arbeite mit fillInData
        //fillInData(getAllUsers);
        userListView=(ListView)findViewById(R.id.neueFreundeList);
        if(userListView!=null) {
            adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice, android.R.id.text1, neueFreundeList);
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
        SparseBooleanArray checked = userListView.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if (checked.valueAt(i)){
                       //TODO     suche Objekt adapter.getItem(position))
                    //neueFreundeListChecked.add(   //// TODO: 10.05.2016 füge objekt der liste hinzu
            }
        }
        Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.putExtra("Tab","Freunde");
        mainActivity.putExtra("Benutzerliste",neueFreundeListChecked);
        startActivity(mainActivity);
    }

    private void fillInData(ArrayList<Benutzer> benutzerListe){
        for (Benutzer tempBenutzer : benutzerListe){
            neueFreundeList.add(tempBenutzer.getName());
        }
    }

    public ArrayList<Benutzer> getSelctedData(){
        if (selectedFlag==true){
            selectedFlag=false;
           return neueFreundeListChecked;
        }
        else{
            return null;
        }
    }
    
    
}
