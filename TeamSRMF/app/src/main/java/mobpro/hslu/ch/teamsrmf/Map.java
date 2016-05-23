package mobpro.hslu.ch.teamsrmf;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Map extends AppCompatActivity {
    private ArrayList<User> freundeList = new ArrayList<User>();
    TextView friendText1;
    TextView friendText2;
    TextView friendText3;
    TextView friendText4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        friendText1 =(TextView)findViewById(R.id.userView1);
        friendText2 =(TextView)findViewById(R.id.userView2);
        friendText3 =(TextView)findViewById(R.id.userView3);
        friendText4 =(TextView)findViewById(R.id.userView4);
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
        freundeList=MainActivity.manager.getmViewFriends();
        Canvas canvas = new Canvas(mutableBitmap);
        friendText1.setText("-");
        friendText2.setText("-");
        friendText3.setText("-");
        friendText4.setText("-");
        for (User freund:freundeList){
            int userColor = 0;
            switch (colorCounter){
                case 0: userColor=Color.BLUE;
                    friendText1.setText(freund.getName());
                    friendText1.setTextColor(Color.BLUE);
                    break;
                case 1: userColor=Color.RED;
                    friendText2.setText(freund.getName());
                    friendText2.setTextColor(Color.RED);
                    break;
                case 2: userColor=Color.GREEN;
                    friendText3.setText(freund.getName());
                    friendText3.setTextColor(Color.GREEN);
                break;
                case 3: userColor=Color.DKGRAY;
                    friendText4.setText(freund.getName());
                    friendText4.setTextColor(Color.DKGRAY);
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
            Thread.sleep(500);
        }catch (InterruptedException ex){
            //error
        }
       MainActivity.manager.setmViewFriends(MainActivity.manager.syncList(MainActivity.manager.getmDatabase(),MainActivity.manager.getmViewFriends()));
       setPicture();
   }
}
