package com.example.joystick;

import android.graphics.Color;
import android.graphics.Paint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class App extends SurfaceView implements SurfaceHolder.Callback
{

    Paint paint;
    Bitmap ball,ball2;
    MainTherad thread;
    Point image;
    int angle;



    App(Context context)
    {
        super(context);
        setFocusable(true);
        setConstans();
    }

    public void surfaceCreated(SurfaceHolder holder)
    {

        thread = new MainTherad(getHolder(),this);
        thread.setRunning(true);
        thread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder)
    {


        while(true)
        {
            try
            {
                thread.setRunning(false);
                thread.join();
            }
            catch(Exception e) {e.printStackTrace();}
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h)
    {

    }

    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        canvas.drawRGB(255,255,255);
        canvas.drawText(Integer.toString(image.x-542),250,100,paint);
        canvas.drawText(Integer.toString(image.y-1140),250,150,paint);
        canvas.drawText(Integer.toString(angle),250,200,paint);
        canvas.drawText("cord x:",100,100,paint);
        canvas.drawText("cord y:",100,150,paint);
        canvas.drawText("angle :",100,200,paint);
        canvas.drawBitmap(ball2,242,840,null);
        canvas.drawBitmap(ball,image.x-ball.getWidth()/2,image.y-ball.getHeight()/2,null);


    }

    public void calculate() {
        int dx = image.x - 542;
        int dy = image.y - 1140;
        angle = (int) (Math.atan2(dy, dx)*180.0/Math.PI)*(-1);
        if(angle<0)
        {
            angle+=360;
        }

    }

    public void setConstans()
    {
        thread = new MainTherad(getHolder(),this);
        getHolder().addCallback(this);
        paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.rgb(22,155,222));
        ball = BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        ball = Bitmap.createScaledBitmap(ball,
                200, 200, false);
        ball2 = BitmapFactory.decodeResource(getResources(),R.drawable.ball2);
        ball2 = Bitmap.createScaledBitmap(ball2,
                600, 600, false);
        image = new Point(542,1140);


    }




    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_UP:
            {
                image.set(542,1140);
                break;
            }
            case MotionEvent.ACTION_MOVE:

                double abs = Math.sqrt((542 - (int) event.getX()) * (542 - (int) event.getX())
                        + (1140 - (int) event.getY()) * (1140 - (int) event.getY()));
                int radius = 200;
                int posX = (int) ((event.getX() -542  ) * radius / abs + 542);
                int posY = (int) ((event.getY() - 1140) * radius / abs + 1140);


                image.set(posX,posY);





                break;
        }

        return true;
    }

}


