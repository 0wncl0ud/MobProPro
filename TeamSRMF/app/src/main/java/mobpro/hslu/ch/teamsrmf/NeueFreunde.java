package mobpro.hslu.ch.teamsrmf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class NeueFreunde extends Activity {

    ArrayAdapter<String> adapter;
    int clickCounter;
    private CheckedTextView myCheckedTextView;
    private  ListView userListView;
    private ArrayList<String> neueFreundeStringList = new ArrayList<String>();
    private ArrayList<User> neueFreundeList = new ArrayList<User>();
    private boolean selectedFlag=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neue_freunde);

        //--------DEBUG-----
        User dummy1=new User("Dummy1", "Elektrotechnik", "1","blue", 200, 100, new Date());
        User dummy2=new User("Dummy2", "Elektrotechnik", "2","blue", 250, 100, new Date());
        User dummy3=new User("Dummy3", "Elektrotechnik", "3","blue", 300, 100, new Date());
        //getList()
        neueFreundeList.add(dummy1);
        neueFreundeList.add(dummy2);
        neueFreundeList.add(dummy3);
        neueFreundeList=MainActivity.manager.getmDatenbank();
        userListView=(ListView)findViewById(R.id.neueFreundeList);
        displayList();
    }

    private void displayList(){
        neueFreundeStringList=MainActivity.manager.convertBenutzerToString(neueFreundeList);
        if(userListView!=null) {
            adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice, android.R.id.text1, neueFreundeStringList);
            userListView.setAdapter(adapter);
        }
    }

    public void wendeFilterAnClicked(View v){
        //Todo  Wende den Filter an und gib die restlichen Objekte auf der Liste aus
        neueFreundeList=MainActivity.manager.getmDatenbank();
        EditText filterName=(EditText)findViewById(R.id.BenutzernameFilter);
        String userTofind = filterName.getText().toString();
        if(userTofind.equals("")){
            neueFreundeList = MainActivity.manager.getmDatenbank();
            displayList();
        }else if(!filterName.equals(null)){
            neueFreundeList=MainActivity.manager.filterUserListName(filterName.getText().toString(),neueFreundeList);
            displayList();
        }
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
