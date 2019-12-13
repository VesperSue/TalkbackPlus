package pcg.talkbackplus;


import java.util.ArrayList;

import pcg.talkbackplus.Variables.BaseVariable;

public class TalkBackPlusRefreshThread extends Thread {
    public final Integer REFRESH_THREAD_WAIT_VAR = 0;
    int countRefreshThread;
    TalkBackPlusService service;
    String crtWindowTitle;

    boolean hasFinished;

    TalkBackPlusRefreshThread(TalkBackPlusService service){
        super();
        this.service = service;
        countRefreshThread = 0;
        crtWindowTitle = null;
    }

    @Override
    public void run() {
        hasFinished = false;
        while (!hasFinished) {

            synchronized (Utility.THREAD_MUTEX) {
                handlerWindowChange();
            }

            synchronized (REFRESH_THREAD_WAIT_VAR) {
                try {
                    if(countRefreshThread == 0) {
                        REFRESH_THREAD_WAIT_VAR.wait();
                    }
                    countRefreshThread = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handlerWindowChange(){
        // todo 根据页面识别的结果 将消息发送给 service打开talkback 或者 UIControllerManager刷新我们的东西
        int pageIndex = 0;
        ArrayList<BaseVariable> variables = new ArrayList<>();
//        Utility.sendMsgToService(Utility.CMD_SHOW_OVERLAY);
        return ;
//        // 是我们的界面
//        Utility.sendMsgToService(Utility.CMD_SHOW_OVERLAY);
//        // 不是我们的界面
//        Utility.sendMsgToService(Utility.CMD_HIDE_OVERLAY);
//
//        // 整个页面变化
//        UIControllerManager.getInstance().onWindowChange(pageIndex);
//        // 当前页面变化
//        UIControllerManager.getInstance().onVariableChange(variables);

    }


}
