package pcg.talkbackplus.UIControllers;

import java.util.List;

import pcg.talkbackplus.TalkBackPlusEventType;
import pcg.talkbackplus.UIControllerManager;

abstract public class BaseController {
    protected BaseController parent = null;
    protected List<BaseController> children = null;

    abstract public void onFocus();
    abstract public void onUpdate(List<Object> objectList);
    abstract public boolean onEvent(TalkBackPlusEventType eventType);

    public String toString() { return "Class:BaseController Func:toString."; }

    public void setParent(BaseController parent) { this.parent = parent; }
    public BaseController getParent() { return this.parent; }


    public void setChildren(List<BaseController> children) {this.children = children; }
    public List<BaseController> getChildren() { return this.children; }

    public void finish() { UIControllerManager.getInstance().makeControllerInActive(this); }
}
