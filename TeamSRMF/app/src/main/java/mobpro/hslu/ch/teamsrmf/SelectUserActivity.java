package mobpro.hslu.ch.teamsrmf;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;

public class SelectUserActivity extends ListActivity {

    ArrayList<CheckedTextView> listItems = new ArrayList<CheckedTextView>();
    ArrayAdapter<CheckedTextView> adapter;
    CheckedTextView myCheckedTextView= new CheckedTextView(this,null,android.R.attr.listChoiceIndicatorMultiple);
    int clickCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myCheckedTextView.setText("Hallo, wähle mich");
        setContentView(R.layout.activity_select_user);
        adapter = new ArrayAdapter<CheckedTextView>(this, android.R.layout.simple_list_item_1, listItems);
        setListAdapter(adapter);
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
