package mobpro.hslu.ch.teamsrmf;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.ArrayList;

public class NeueFreunde extends Activity {

    ArrayList<CheckedTextView> listItems;
    ArrayAdapter<String> adapter;
    int clickCounter;
    CheckedTextView myCheckedTextView;
    ListView userListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neue_freunde);
        listItems = new ArrayList<CheckedTextView>();
        String[] values = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        userListView=(ListView)findViewById(R.id.neueFreundeList);
        if(userListView!=null) {
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
            userListView.setAdapter(adapter);
        }
        //myCheckedTextView= new CheckedTextView(this,null,android.R.attr.listChoiceIndicatorMultiple);
       // myCheckedTextView.setText("Hallo, wähle mich");

        //setListAdapter(adapter);
    }

    /*public void addItems(View v) {
        listItems.add(0,myCheckedTextView);
        adapter.notifyDataSetChanged();
    }*//*
    public void

    }*/
}
