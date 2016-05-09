package mobpro.hslu.ch.teamsrmf;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectUserActivity extends Activity {

    ArrayList<CheckedTextView> listItems = new ArrayList<CheckedTextView>();
    ArrayAdapter<CheckedTextView> adapter;
    int clickCounter;
    CheckedTextView myCheckedTextView;
    ListView userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
       // userListView=ge
       /* myCheckedTextView= new CheckedTextView(this,null,android.R.attr.listChoiceIndicatorMultiple);
        myCheckedTextView.setText("Hallo, wähle mich");
        adapter = new ArrayAdapter<CheckedTextView>(this, android.R.layout.simple_list_item_1, listItems);
        setListAdapter(adapter);
        addItems();*/
    }

    public void addItems(View v) {
        listItems.add(0,myCheckedTextView);
        adapter.notifyDataSetChanged();
    }


   /* protected class ListView extends ListActivity {
        @Override
        public void onCreate(Bundle icicle) {
            super.onCreate(icicle);

        }


    }*/
}
