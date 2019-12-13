package pcg.talkbackplus.Views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


import java.util.Locale;

import pcg.talkbackplus.MyGestureListener;
import pcg.talkbackplus.TalkBackPlusEventThread;
import pcg.talkbackplus.TalkBackPlusEventType;
import pcg.talkbackplus.TalkBackPlusService;

public class OverlayView extends View {
    Context context;
    TalkBackPlusService service;
    Paint mPaint;
    public MyGestureListener myGestureListener;
    public GestureDetector myDetector;
    int lastX;
    int lastY;

    TalkBackPlusEventThread eventThread;

    public interface GestureListener{
        boolean onGestureDetected(int gestureId);
    }

    GestureListener mGestureListener;

    public OverlayView(final Context context, TalkBackPlusEventThread eventThread) {
        super(context);
        this.context = context;
        service = (TalkBackPlusService) context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAlpha(127);
        mPaint.setTextSize(20);


        setAlpha(0.7f);
        setBackgroundColor(Color.parseColor("#0E6251"));
        setClickable(true);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        this.setContentDescription("GestureDetector");
        lastX = lastY = -1;
        this.eventThread = eventThread;
        myGestureListener= new MyGestureListener();
        myDetector = new GestureDetector(TalkBackPlusService.getInstance(),this.myGestureListener);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        lastX = (int) event.getRawX();
        lastY = (int) event.getRawY();
        invalidate();

        // 手势检测
//        if(event.getAction() == MotionEvent.ACTION_UP){
//            if(mGestureListener != null){
//                boolean res = mGestureListener.onGestureDetected(AccessibilityService.GESTURE_SWIPE_RIGHT);
//                return res;
//            }
        final TalkBackPlusEventType event1 =recognizeGesture(event);
        new Thread("motion event add"){
            @Override
            public void run() {
                synchronized (eventThread.actionEventRecords){
                    eventThread.actionEventRecords.offer(event1);
                    eventThread.actionEventRecords.notify();
                }
            }
        }.start();

        return true;
    }

    TalkBackPlusEventType recognizeGesture(MotionEvent event){

        TalkBackPlusEventType type= TalkBackPlusEventType.EVENT_NONE;
        if(myDetector.onTouchEvent(event)==true){
            type=myGestureListener.type;
            Log.d("context","-------------------------RECOGNIZE_type="+type);
        }

       return type;
        /*@Override
        protected void onDestroy() {
            super.onDestroy();
            //移除当前activity的点击
            ClickUtil.removeClickView(this);
        }*/


    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawRect(25,25,125,125, mPaint);
        canvas.drawText(String.format(Locale.US, "%d-%d", lastX, lastY), 0, 75, mPaint);
    }

    public void hide(){
        setVisibility(INVISIBLE);
    }

    public void show(){
        setVisibility(VISIBLE);
    }

    public void setGestureListener(GestureListener listener){
        mGestureListener = listener;
    }

}
