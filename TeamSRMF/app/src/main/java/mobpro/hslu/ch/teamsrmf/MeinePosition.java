package mobpro.hslu.ch.teamsrmf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MeinePosition extends AppCompatActivity {
    ImageView imVmensaplan;
    int[] viewCoords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meine_position);
        imVmensaplan = (ImageView) findViewById(R.id.imageViewMeinePosition);
        setPicture();
        viewCoords = new int[2];
        imVmensaplan.getLocationOnScreen(viewCoords);
        imVmensaplan.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (v.equals(imVmensaplan)) {
                    int touchX = (int) event.getX();
                    int touchY = (int) event.getY();
                    int imageX = touchX - viewCoords[0]; // viewCoords[0] is the X coordinate
                    int imageY = touchY - viewCoords[1];
                }
                return true;
            }
        });

    }

    void setPicture() {
        Bitmap bmpMensaPlan = BitmapFactory.decodeResource(getResources(), R.drawable.mensaplan);
        Bitmap mutableBitmap = bmpMensaPlan.copy(Bitmap.Config.ARGB_8888, true);


        Canvas canvas = new Canvas(mutableBitmap);
        //add Marker here

        imVmensaplan.setImageDrawable(new BitmapDrawable(getResources(), mutableBitmap));
    }

    public void NichtMehrDaClicked(View v) {
        //TODO speicher die Positionsdaten
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void speichereMeinePositionClicked(View v) {
        //TODO speicher die Positionsdaten
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void abbrechenClicked(View v) {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

}
