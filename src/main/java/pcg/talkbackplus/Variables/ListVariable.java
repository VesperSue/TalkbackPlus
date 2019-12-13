package pcg.talkbackplus.Variables;

import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;

import pcg.talkbackplus.AccessibilityNodeInfoRecord;

public class ListVariable extends BaseVariable {

    AccessibilityNodeInfoRecord listRoot;
    public ListVariable(AccessibilityNodeInfoRecord node){
        listRoot = node;
    }

    @Override
    void buildVariableHierarchy() {
        children = new ArrayList<>();
        // 进行对应的子变量的添加

    }

    public boolean scroll(boolean forward){
        return listRoot.performAction(forward? AccessibilityNodeInfo.ACTION_SCROLL_FORWARD: AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
    }

    @Override
    public String toString() {
        return "";
    }
}
