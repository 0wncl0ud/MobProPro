package mobpro.hslu.ch.teamsrmf;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

import java.util.Date;

public class MeineDaten extends Activity {
    private  EditText fieldUsername;
    private Spinner studienrichtung;
    private Spinner semester;
    private static int posSem, posStudrich;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meine_daten);
        fieldUsername = (EditText)findViewById(R.id.txtUsername);
        studienrichtung = (Spinner)findViewById(R.id.spinnerStudienrichtung);
        semester = (Spinner) findViewById(R.id.spinnerSemester);
        loadData();
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

    public void setztePositionClicked(View v){
        saveData();
        Intent meinePositionActivity = new Intent(this, MeinePosition.class);
        startActivity(meinePositionActivity);
    }

    public void freundeAufrufenClicked(View v){
        Intent freundeActivity = new Intent(this, MeineFreunde.class);
        startActivity(freundeActivity);
    }

    public void karteAufrufenClicked(View v){
        Intent karteActivity = new Intent(this, Karte.class);
        startActivity(karteActivity);
    }

    private void saveData(){
        Benutzer newUser;
        if(MainActivity.manager.getmMeineDaten() == null){
            newUser = new Benutzer(fieldUsername.getText().toString(),
                    studienrichtung.getItemAtPosition(studienrichtung.getSelectedItemPosition()).toString(),
                    semester.getItemAtPosition(semester.getSelectedItemPosition()).toString(),
                    "Blau",
                    0,0,
                    new Date());
        } else{
            newUser = MainActivity.manager.getmMeineDaten();
            newUser.setName(fieldUsername.getText().toString());
            String tempName=newUser.getName();
            newUser.setName(fieldUsername.getText().toString());
            if(fieldUsername.getText().toString().equals(tempName)){
                newUser.setOldname(null);
            }
            newUser.setSemster(semester.getItemAtPosition(semester.getSelectedItemPosition()).toString());
            newUser.setStudienrichtung(studienrichtung.getItemAtPosition(studienrichtung.getSelectedItemPosition()).toString());
            newUser.setFarbe("Blau");
        }
        if(MainActivity.manager.filterUserListName(newUser.getName(),MainActivity.manager.getmDatenbank()).isEmpty()){
            MainActivity.manager.editUser(newUser);
        }
        else{
            //username bereits vorhanden
            Toast.makeText(this, "Dieser Name existiert bereist", Toast.LENGTH_LONG).show();
        }
        posSem = semester.getSelectedItemPosition();
        posStudrich = studienrichtung.getSelectedItemPosition();

    }

    private void loadData(){
        fieldUsername = (EditText)findViewById(R.id.txtUsername);
        studienrichtung = (Spinner)findViewById(R.id.spinnerStudienrichtung);
        semester = (Spinner) findViewById(R.id.spinnerSemester);
        if(MainActivity.manager.getmMeineDaten() != null) {
            Benutzer meinBenutzer=MainActivity.manager.getmMeineDaten();
            fieldUsername.setText(meinBenutzer.getName());
            //TODO      setzte den Spinner auch auf den richtigen wert
            semester.setSelection(posSem);
            studienrichtung.setSelection(posStudrich);
        }
    }
}
