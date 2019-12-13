package pcg.talkbackplus;
import java.util.Stack;

import pcg.talkbackplus.UIControllers.BaseController;

public class UIControllerManager
{
    Stack<BaseController> controllerStack;
    BaseController rootController;
    private static UIControllerManager uicm = null;

    private UIControllerManager()
    {
        controllerStack = new Stack<BaseController>();
        rootController = null;
    }

    public static UIControllerManager getInstance()
    {
        if(uicm==null)
        {
            uicm = new UIControllerManager();
            return uicm;
        }
        return uicm;
    }

    public void onEvent(TalkBackPlusEventType event)
    {
        BaseController controller = controllerStack.peek();
        while(controller!=null&&!controller.onEvent(event))
        {
            controller = controller.getParent();
        }
    }

    public void onWindowChange(int windowIndex)
    {
        controllerStack.clear();
        rootController = tryGenerateController(windowIndex,0);
    }

    public void onWindowUpdate(int windowIndex)
    {
        controllerStack.clear();
        updateController(rootController);
    }

    public BaseController tryGenerateController(int windowIndex, int controllerID)
    {
        // 获取这里所需的全部变量
        // 是否能够生成 正确的
        // 组合的Controller递归的调用 tryGenerateController
        // 按照类别创建Contorller setParent
        return null;
    }

    public boolean updateController(BaseController controller)
    {
        // 获取这里所需的全部变量
        // 是否有更新
        // 组合的Controller递归的调用 updateController
        // 按照是否有更新告知Controller本身
        return false;
    }


    public void makeControllerActive(BaseController controller)
    {
        controllerStack.push(controller);
        controller.onFocus();
    }

    public void makeControllerInActive(BaseController controller)
    {
        if(controllerStack.peek()==controller)
            controllerStack.pop();
    }

}
