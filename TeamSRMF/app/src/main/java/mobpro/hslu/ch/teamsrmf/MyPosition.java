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

public class MyPosition extends AppCompatActivity {
    ImageView imVmensamap;
    User myUser;
    UserManager manager;
    int[] viewCoords;
    int imageX=0;
    int imageY=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_position);
        manager= UserManager.getInstance(getApplicationContext());
        imVmensamap = (ImageView) findViewById(R.id.imageViewMeinePosition);
        getMyUser();
        setPicture(myUser.getXposition(), myUser.getYposition());
        viewCoords = new int[2];
        imVmensamap.getLocationOnScreen(viewCoords);
        imVmensamap.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (v.equals(imVmensamap)) {
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
        Bitmap bmpMensaMap = BitmapFactory.decodeResource(getResources(), R.drawable.mensaplan);
        Bitmap mutableBitmap = bmpMensaMap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        if(x!=0 && y!=0){
            addNewMarker(canvas,x,y);
        }
        imVmensamap.setImageDrawable(new BitmapDrawable(getResources(), mutableBitmap));
    }

    Canvas addNewMarker(Canvas canvas,int x, int y){
        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        canvas.drawCircle(x, y, 15, mPaint);
        return canvas;
    }
    public void awayClicked(View v) {
        getMyUser();
        myUser.setXposition(0);
        myUser.setYposition(0);
        myUser.setTimeStamp();
        manager.editUser(myUser);
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void saveMyPositionClicked(View v) {
        getMyUser();
        myUser.setXposition(imageX);
        myUser.setYposition(imageY);
        myUser.setTimeStamp();
        myUser.setOldname(myUser.getName());
        manager.editUser(myUser);
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void abortClicked(View v) {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
    private void getMyUser(){
        myUser =manager.getmMyData();
        if(myUser ==null){
            myUser =new User("Hans Muster","Informatik", "3","Blue",0,0,new Date());
        }
    }
}
