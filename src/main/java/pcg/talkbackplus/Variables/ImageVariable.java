package pcg.talkbackplus.Variables;

import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;

import pcg.talkbackplus.AccessibilityNodeInfoRecord;

public class ImageVariable extends BaseVariable {
    AccessibilityNodeInfoRecord imgNode;

    public ImageVariable(AccessibilityNodeInfoRecord node){
        imgNode = node;

    }

    boolean click(){
        return imgNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    boolean longClick(){
        return imgNode.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
    }

    boolean isClickable(){
        return imgNode.isClickable();
    }

    boolean isLongClickable(){
        return imgNode.isLongClickable();
    }

    @Override
    void buildVariableHierarchy() {
        this.children = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "图片";
    }
}
