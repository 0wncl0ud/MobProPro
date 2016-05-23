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

public class MyFriends extends AppCompatActivity {
    private ArrayList<String> friendsStringList = new ArrayList<String>();
    private ArrayList<User> friendsList = new ArrayList<User>();
    private  ListView myFriendsListView;
    ArrayAdapter<String> adapter;
    private UserManager manager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        context=getApplicationContext();
        //friendsStringList.add("Hallo");
        setListe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.manager.loadList();
    }

    public void deleteFriendsClicked(View v){
        //// TODO: 09.05.2016 lösche hier die markierten freunde
        ArrayList<String> friendsListChecked = new ArrayList<String>();
        SparseBooleanArray checked = myFriendsListView.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if (checked.valueAt(i)){
                friendsListChecked.add(adapter.getItem(position));
            }
        }
        ////TODO freundliste aktuallisieren mit aktuellerliste
        MainActivity.manager.deleteMyFriends(MainActivity.manager.convertStringToUser(friendsListChecked, friendsList));
        setListe();
    }

    private void setListe(){
        friendsList =MainActivity.manager.getMyFriends();
        friendsStringList =MainActivity.manager.convertUserToString(friendsList);
        myFriendsListView =(ListView)findViewById(R.id.freundeListView);
        manager= UserManager.getInstance(context);
        if(myFriendsListView !=null) {
            adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice, android.R.id.text1, friendsStringList);
            myFriendsListView.setAdapter(adapter);
        }
    }
    public void viewFriendsClicked(View v){
        ArrayList<String> friendsStringListChecked = new ArrayList<String>();
        ArrayList<String> friendsListChecked = new ArrayList<String>();
        SparseBooleanArray checked = myFriendsListView.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if (checked.valueAt(i)){
                friendsStringListChecked.add(adapter.getItem(position));
            }
        }
        MainActivity.manager.setmViewFriends(MainActivity.manager.syncList(manager.getmDatabase(),manager.convertStringToUser(friendsStringListChecked, friendsList)));
        TabActivity tabs = (TabActivity) getParent();
        tabs.getTabHost().setCurrentTabByTag("Map");
    }

    public void addFriendsClicked(View v){
        Intent newFriendsActivity = new Intent(this, NewFriends.class);
        startActivity(newFriendsActivity);
    }

    public void saveUserListClicked(){

    }
}
