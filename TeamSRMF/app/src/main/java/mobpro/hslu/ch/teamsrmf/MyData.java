package mobpro.hslu.ch.teamsrmf;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

//todo Refactor auf en, Aktuklaisiern button, Gelb andere Farbe,

public class MyData extends Activity {
    private EditText fieldUsername;
    private Spinner fieldOfStudy;
    private Spinner term;
    private static int posTerm, posFieldOfStudy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data);
        fieldUsername = (EditText)findViewById(R.id.txtUsername);
        fieldOfStudy = (Spinner)findViewById(R.id.spinnerFieldOfStudy);
        term = (Spinner) findViewById(R.id.spinnerTerm);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    public void saveOwnDataClicked(View v){
        saveData();
        TabActivity tabs = (TabActivity) getParent();
        tabs.getTabHost().setCurrentTabByTag("Freunde");
    }

    public void setPositionClicked(View v){
        saveData();
        Intent meinePositionActivity = new Intent(this, MyPosition.class);
        startActivity(meinePositionActivity);
    }

    public void friendsClicked(View v){
        Intent freundeActivity = new Intent(this, MyFriends.class);
        startActivity(freundeActivity);
    }

    public void mapClicked(View v){
        Intent karteActivity = new Intent(this, Map.class);
        startActivity(karteActivity);
    }

    private void saveData(){
        User newUser;
        if(MainActivity.manager.getmMyData() == null){
            newUser = new User(fieldUsername.getText().toString(),
                    fieldOfStudy.getItemAtPosition(fieldOfStudy.getSelectedItemPosition()).toString(),
                    term.getItemAtPosition(term.getSelectedItemPosition()).toString(),
                    "Blau",
                    0,0,
                    new Date());
        } else{
            newUser = MainActivity.manager.getmMyData();
            String tempName=newUser.getName();
            newUser.setName(fieldUsername.getText().toString());
            if(fieldUsername.getText().toString().equals(tempName)){
                newUser.setOldname(null);
            }
            newUser.setTerm(term.getItemAtPosition(term.getSelectedItemPosition()).toString());
            newUser.setFieldOfStudy(fieldOfStudy.getItemAtPosition(fieldOfStudy.getSelectedItemPosition()).toString());
            newUser.setColor("Blau");
        }
        //gibt es den Name bereits
        ArrayList<User> tempUserList=MainActivity.manager.filterUserListName(newUser.getName(),MainActivity.manager.getmDatabase());
        if(tempUserList.isEmpty()){
            MainActivity.manager.editUser(newUser);
        }
        else{
            //username bereits vorhanden
            newUser.setOldname(newUser.getName());
            MainActivity.manager.editUser(newUser);
        }
        posTerm = term.getSelectedItemPosition();
        posFieldOfStudy = fieldOfStudy.getSelectedItemPosition();

    }

    private void loadData(){
        fieldUsername = (EditText)findViewById(R.id.txtUsername);
        fieldOfStudy = (Spinner)findViewById(R.id.spinnerFieldOfStudy);
        term = (Spinner) findViewById(R.id.spinnerTerm);
        if(MainActivity.manager.getmMyData() != null) {
            User myUser =MainActivity.manager.getmMyData();
            fieldUsername.setText(myUser.getName());

            //TODO      setzte den Spinner auch auf den richtigen wert
            String [] tempTerm=getResources().getStringArray(R.array.Semester);
            posTerm =Arrays.asList(tempTerm).indexOf(myUser.getTerm());
            String[] tempFieldOfStudy = getResources().getStringArray(R.array.Studienrichtung);
            posFieldOfStudy =Arrays.asList(tempFieldOfStudy).indexOf(myUser.getFieldOfStudy());
            term.setSelection(posTerm);
            fieldOfStudy.setSelection(posFieldOfStudy);
        }
    }
}
