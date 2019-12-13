package pcg.talkbackplus;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;


import pcg.talkbackplus.Views.OverlayView;

import static android.content.Context.WINDOW_SERVICE;

public class OverlayController {
    TalkBackPlusService service;
    OverlayView detector;

    public OverlayController(TalkBackPlusService service, TalkBackPlusEventThread eventThread){
        this.service = service;

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(1079, 3000,0,0,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;

        detector = new OverlayView(service,eventThread);
        ((WindowManager) service.getSystemService(WINDOW_SERVICE)).addView(detector, params);
    }

    public void hideOverlay(){
        if(isOverlayActive())
            detector.hide();
    }

    public void showOverlay(){
        if(!isOverlayActive())
            detector.show();
    }

    public boolean isOverlayActive(){
        return detector.getVisibility() == View.VISIBLE;
    }

    public void setGestureListener(OverlayView.GestureListener listener){
        detector.setGestureListener(listener);
    }
}
