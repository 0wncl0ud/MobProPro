package mobpro.hslu.ch.teamsrmf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class Karte extends AppCompatActivity {
    private ArrayList<Benutzer> freundeList = new ArrayList<Benutzer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karte);
        freundeList=MainActivity.manager.getMeineFreunde();
        setPicture();
    }

    void setPicture(){
        Bitmap bmpMensaPlan = BitmapFactory.decodeResource(getResources(), R.drawable.mensaplan);
        Bitmap mutableBitmap = bmpMensaPlan.copy(Bitmap.Config.ARGB_8888, true);
        ImageView imVmensaplan=(ImageView)findViewById(R.id.imageView);

        Canvas canvas = new Canvas(mutableBitmap);
        for (Benutzer freund:freundeList){
            canvas=addNewMarker(canvas,freund.getXposition(),freund.getYposition());
        }
        imVmensaplan.setImageDrawable(new BitmapDrawable(getResources(),mutableBitmap));
    }

    Canvas addNewMarker(Canvas canvas,int posX, int posY){
        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        canvas.drawCircle(posX, posY, 15, mPaint);
        return canvas;
    }
}
