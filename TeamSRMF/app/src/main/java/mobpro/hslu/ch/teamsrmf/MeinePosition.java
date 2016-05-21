package mobpro.hslu.ch.teamsrmf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.Date;

public class MeinePosition extends AppCompatActivity {
    ImageView imVmensaplan;
    Benutzer meinBenutzer;
    BenutzerManager manager;
    int[] viewCoords;
    int imageX=0;
    int imageY=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meine_position);
        manager=BenutzerManager.getInstance(getApplicationContext());
        imVmensaplan = (ImageView) findViewById(R.id.imageViewMeinePosition);
        getMeinBenutzer();
        setPicture(meinBenutzer.getXposition(),meinBenutzer.getYposition());
        viewCoords = new int[2];
        imVmensaplan.getLocationOnScreen(viewCoords);
        imVmensaplan.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (v.equals(imVmensaplan)) {
                    int touchX = (int) event.getX();
                    int touchY = (int) event.getY();
                    imageX = touchX - viewCoords[0]; // viewCoords[0] is the X coordinate
                    imageY = touchY - viewCoords[1];
                    setPicture(imageX,imageY);
                }
                return true;
            }
        });

    }

    void setPicture(int x,int y) {
        Bitmap bmpMensaPlan = BitmapFactory.decodeResource(getResources(), R.drawable.mensaplan);
        Bitmap mutableBitmap = bmpMensaPlan.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        if(x!=0 && y!=0){
            addNewMarker(canvas,x,y);
        }
        imVmensaplan.setImageDrawable(new BitmapDrawable(getResources(), mutableBitmap));
    }

    Canvas addNewMarker(Canvas canvas,int x, int y){
        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        canvas.drawCircle(x, y, 15, mPaint);
        return canvas;
    }
    public void NichtMehrDaClicked(View v) {
        getMeinBenutzer();
        meinBenutzer.setXposition(0);
        meinBenutzer.setYposition(0);
        meinBenutzer.setTimeStamp();
        manager.editUser(meinBenutzer);
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void speichereMeinePositionClicked(View v) {
        getMeinBenutzer();
        meinBenutzer.setXposition(imageX);
        meinBenutzer.setYposition(imageY);
        meinBenutzer.setTimeStamp();
        meinBenutzer.setOldname(meinBenutzer.getName());
        manager.editUser(meinBenutzer);
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void abbrechenClicked(View v) {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
    private void getMeinBenutzer(){
        meinBenutzer=manager.getmMeineDaten();
        if(meinBenutzer==null){
            meinBenutzer=new Benutzer("Hans Muster","Informatik", "3","Blue",0,0,new Date());
        }
    }
}
