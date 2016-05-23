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
import android.widget.TextView;

import java.util.ArrayList;

public class Karte extends AppCompatActivity {
    private ArrayList<Benutzer> freundeList = new ArrayList<Benutzer>();
    TextView freundText1;
    TextView freundText2;
    TextView freundText3;
    TextView freundText4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karte);
        freundText1=(TextView)findViewById(R.id.userView1);
        freundText2=(TextView)findViewById(R.id.userView2);
        freundText3=(TextView)findViewById(R.id.userView3);
        freundText4=(TextView)findViewById(R.id.userView4);
        setPicture();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPicture();
    }

    void setPicture(){
        int colorCounter=0;
        Bitmap bmpMensaPlan = BitmapFactory.decodeResource(getResources(), R.drawable.mensaplan);
        Bitmap mutableBitmap = bmpMensaPlan.copy(Bitmap.Config.ARGB_8888, true);
        ImageView imVmensaplan=(ImageView)findViewById(R.id.imageView);
        freundeList=MainActivity.manager.getmViewFreunde();
        Canvas canvas = new Canvas(mutableBitmap);
        freundText1.setText("-");
        freundText2.setText("-");
        freundText3.setText("-");
        freundText4.setText("-");
        for (Benutzer freund:freundeList){
            int userColor = 0;
            switch (colorCounter){
                case 0: userColor=Color.BLUE;
                    freundText1.setText(freund.getName());
                    break;
                case 1: userColor=Color.RED;
                    freundText2.setText(freund.getName());
                    break;
                case 2: userColor=Color.GREEN;
                    freundText3.setText(freund.getName());
                break;
                case 3: userColor=Color.YELLOW;
                    freundText4.setText(freund.getName());
                break;
            }
            canvas=addNewMarker(canvas,freund.getXposition(),freund.getYposition(),userColor);
            colorCounter++;
        }
        imVmensaplan.setImageDrawable(null);
        imVmensaplan.setImageDrawable(new BitmapDrawable(getResources(),mutableBitmap));
    }

    Canvas addNewMarker(Canvas canvas,int posX, int posY,int color){
        Paint mPaint = new Paint();
        mPaint.setColor(color);
        canvas.drawCircle(posX, posY, 15, mPaint);
        return canvas;
    }

   public void refreshList (View v){
       MainActivity.manager.loadList();
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
            //error
        }
       MainActivity.manager.setmViewFreunde(MainActivity.manager.syncList(MainActivity.manager.getmDatenbank(),MainActivity.manager.getmViewFreunde()));
       setPicture();
   }
}
