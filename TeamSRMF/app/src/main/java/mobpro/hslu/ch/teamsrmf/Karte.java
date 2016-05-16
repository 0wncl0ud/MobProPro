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

public class Karte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karte);
        setPicture();
    }

    void setPicture(){
        Bitmap bmpMensaPlan = BitmapFactory.decodeResource(getResources(), R.drawable.mensaplan);
        Bitmap mutableBitmap = bmpMensaPlan.copy(Bitmap.Config.ARGB_8888, true);
        ImageView imVmensaplan=(ImageView)findViewById(R.id.imageView);

        Canvas canvas = new Canvas(mutableBitmap);
        canvas=addNewMarker(canvas);

        imVmensaplan.setImageDrawable(new BitmapDrawable(getResources(),mutableBitmap));
    }

    Canvas addNewMarker(Canvas canvas){
       /* Bitmap marker = BitmapFactory.decodeResource(getResources(), R.drawable.search_marker2);
        Paint mPaint = new Paint();
        ColorFilter filter = new LightingColorFilter(Color.BLUE, 2);
        mPaint.setColorFilter(filter);
        mPaint.setColor(Color.GREEN);
        canvas.drawBitmap(marker, 40, 40, null);*/
        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        canvas.drawCircle(60, 60, 15, mPaint);
        return canvas;
    }
}
