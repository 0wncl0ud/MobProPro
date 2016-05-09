package mobpro.hslu.ch.teamsrmf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FilterSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_settings);
    }


    public void saveFilterData(View v){
        Intent selectUser = new Intent(this, NeueFreunde.class);
        startActivity(selectUser);
    }
}
