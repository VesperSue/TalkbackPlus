package pcg.talkbackplus.Variables;


import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;

import pcg.talkbackplus.AccessibilityNodeInfoRecord;

public class ButtonVariable extends BaseVariable {
    AccessibilityNodeInfoRecord button;

    public ButtonVariable(AccessibilityNodeInfoRecord node){
        button = node;
        buildVariableHierarchy();
    }

    boolean click(){
        return button.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    boolean longClick(){
        return button.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
    }

    boolean isClickable(){
        return button.isClickable();
    }

    boolean isLongClickable(){
        return button.isLongClickable();
    }


    @Override
    void buildVariableHierarchy() {
        children = new ArrayList<>();
        // 后续进行节点的添加
    }

    @Override
    public String toString() {
        return "";
    }
}
