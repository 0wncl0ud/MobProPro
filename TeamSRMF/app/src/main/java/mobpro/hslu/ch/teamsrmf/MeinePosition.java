package mobpro.hslu.ch.teamsrmf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MeinePosition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meine_position);
    }

   public void NichtMehrDaClicked(View v){
       //TODO speicher die Positionsdaten
       Intent mainActivity = new Intent(this, MainActivity.class);
       startActivity(mainActivity);
   }

    public void speichereMeinePositionClicked(View v){
        //TODO speicher die Positionsdaten
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void abbrechenClicked(View v){
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}
