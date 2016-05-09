package mobpro.hslu.ch.teamsrmf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.View;
=======
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
>>>>>>> origin/master

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
