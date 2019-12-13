package pcg.talkbackplus;
import android.os.Looper;
import android.util.Log;
import android.view.GestureDetector;
import pcg.talkbackplus.TalkBackPlusEventType;

import java.util.LinkedList;
import java.util.Queue;


public class TalkBackPlusEventThread extends Thread {

    public final Queue<TalkBackPlusEventType> actionEventRecords;
    boolean hasFinished;
    TalkBackPlusService service;
    private TalkBackPlusEventType lastTalkBackPlusEventType;



    TalkBackPlusEventThread(TalkBackPlusService service){
        actionEventRecords = new LinkedList<>();
        this.service = service;
    }

    @Override
    public void run() {
        Looper.prepare();
        TalkBackPlusEventType crtAction;
        while (!hasFinished){
            synchronized (actionEventRecords){
                if(actionEventRecords.isEmpty()){
                    try {
                        actionEventRecords.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                crtAction = actionEventRecords.poll();
            }
            synchronized (Utility.THREAD_MUTEX){
                handleTalkBackPlusEventType(crtAction);
            }
        }
    }

    private void handleTalkBackPlusEventType(TalkBackPlusEventType event)
    {

//        if(eventType != TalkBackPlusEventType.EVENT_NONE)
        //UIControllerManager.getInstance().onEvent(event);
        lastTalkBackPlusEventType = event;
        //Utility.toastLogger(event.toString());
        Utility.speak("事件");
    }

}
