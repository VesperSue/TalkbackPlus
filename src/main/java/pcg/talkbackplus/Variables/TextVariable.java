package pcg.talkbackplus.Variables;

import java.util.ArrayList;

import pcg.talkbackplus.AccessibilityNodeInfoRecord;

public class TextVariable extends BaseVariable {
    AccessibilityNodeInfoRecord textNode;

    public TextVariable(AccessibilityNodeInfoRecord node){
        textNode = node;
        buildVariableHierarchy();
    }

    @Override
    void buildVariableHierarchy() {
        children = new ArrayList<>();
        // 文本变量不包含子变量
    }

    @Override
    public String toString() {
        if(textNode.getText() != null && textNode.getText().length() > 0){
            return textNode.getText().toString();
        } else if(textNode.getContentDescription() != null && textNode.getContentDescription().length() > 0){
            return textNode.getContentDescription().toString();
        } else {
            return "";
        }
    }

    public String getText(){
        CharSequence text = textNode.getText();
        if(text == null){
            return "";
        }
        return text.toString();
    }

    public String getContentDescription(){
        CharSequence content = textNode.getContentDescription();
        if(content == null){
            return "";
        }

        return content.toString();
    }
}
