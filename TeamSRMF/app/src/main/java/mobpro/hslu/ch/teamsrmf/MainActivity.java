package mobpro.hslu.ch.teamsrmf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = new Intent(this, TCPClient.class);
        startActivity(intent);
    }
    public void saveOwnDataClicked(View v){
        Intent filterSettingsActivity = new Intent(this, FilterSettings.class);
        startActivity(filterSettingsActivity);
    }
}
