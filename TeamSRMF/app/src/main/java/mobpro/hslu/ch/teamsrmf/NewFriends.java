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

public class NewFriends extends Activity {

    ArrayAdapter<String> adapter;
    int clickCounter;
    private CheckedTextView myCheckedTextView;
    private  ListView userListView;
    private ArrayList<String> newFriendsStringList = new ArrayList<String>();
    private ArrayList<User> newFriendsList = new ArrayList<User>();
    private boolean selectedFlag=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends);
        newFriendsList =MainActivity.manager.getmDatabase();
        userListView=(ListView)findViewById(R.id.newFriendsList);
        displayList();
    }

    private void displayList(){
        newFriendsStringList =MainActivity.manager.convertUserToString(newFriendsList);
        if(userListView!=null) {
            adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice, android.R.id.text1, newFriendsStringList);
            userListView.setAdapter(adapter);
        }
    }

    public void wendeFilterAnClicked(View v){
        //Todo  Wende den Filter an und gib die restlichen Objekte auf der Liste aus
        newFriendsList =MainActivity.manager.getmDatabase();
        EditText filterName=(EditText)findViewById(R.id.UsernameFilter);
        if(!filterName.equals(null)){
            newFriendsList =MainActivity.manager.filterUserListName(filterName.getText().toString(), newFriendsList);
            displayList();
        }
    }

    public void AbortNewFriendClicked(View v){
        Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.putExtra("Tab","Freunde");
        startActivity(mainActivity);
    }


    public void saveUserListClicked(View v){
        ArrayList<String> newFrinedsStringListChecked = new ArrayList<String>();
        SparseBooleanArray checked = userListView.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if (checked.valueAt(i)){
                newFrinedsStringListChecked.add(adapter.getItem(position));
            }
        }
        MainActivity.manager.addmMyFriends(MainActivity.manager.convertStringToUser(newFrinedsStringListChecked, newFriendsList));
        Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.putExtra("Tab","Freunde");
        startActivity(mainActivity);
    }

    
    
}
