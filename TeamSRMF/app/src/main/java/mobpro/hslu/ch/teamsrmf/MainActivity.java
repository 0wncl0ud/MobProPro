package mobpro.hslu.ch.teamsrmf;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

public class MainActivity extends TabActivity {
    private TabHost tabHost;
    public static UserManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager= UserManager.getInstance(getApplicationContext());
        tabHost = (TabHost) findViewById(android.R.id.tabhost);


        TabHost.TabSpec tabData = tabHost.newTabSpec("Meine Daten");
        TabHost.TabSpec tabMap = tabHost.newTabSpec("Map");
        TabHost.TabSpec tabFriends= tabHost.newTabSpec("Freunde");


        tabData.setIndicator("Meine Daten");
        tabData.setContent(new Intent(this, MyData.class));

        tabMap.setIndicator("Map");
        tabMap.setContent(new Intent(this, Map.class));

        tabFriends.setIndicator("Freunde");
        tabFriends.setContent(new Intent(this, MyFriends.class));

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tabData);
        tabHost.addTab(tabMap);
        tabHost.addTab(tabFriends);


        //Welcher Tab soll angezeigt werden?
        Intent intent = getIntent();
        try {
            String indicator = intent.getExtras().getString("Tab");
            if (indicator != null) {
                Log.i("hslu_mobApp", "Indicator:" + indicator);
                Parcelable[] temp = intent.getExtras().getParcelableArray("Benutzerliste");
                tabHost.setCurrentTabByTag(indicator);
            }
        } catch (Exception e) {
        }

        //-----------------------------TEST KOMUNIKATION-------------------------------------------
        //manager.addUser(new User("Manuel Felber", "ET", "5", "blau", 12, 345, new Date()));

        //while (manager.getBusy()){
        //
        //}
        //ArrayList<User> database = manager.getmDatabase();

        //----------------------------------------------------------------------------------------------

//        while (!client.getState()){
//
//      }
//        ArrayList<User> benutzers = client.getListOfBenutzer();
    }
}
