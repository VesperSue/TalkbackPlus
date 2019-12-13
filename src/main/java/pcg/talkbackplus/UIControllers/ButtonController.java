package pcg.talkbackplus.UIControllers;

import android.util.Log;

import java.util.List;

import pcg.talkbackplus.TalkBackPlusEventType;
import pcg.talkbackplus.Utility;
import pcg.talkbackplus.Variables.ButtonVariable;

public class ButtonController extends BaseController {
    private ButtonVariable buttonVariable;
    private TextController textController;

    public ButtonController(ButtonVariable buttonVariable, TextController textController) {
        this.buttonVariable = buttonVariable;
        this.textController = textController;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (textController != null) {
            stringBuilder.append(textController.toString());
        } else if (buttonVariable != null) {
            stringBuilder.append(buttonVariable.toString());
        } else {
            Log.w("Controller", "No String In Class:ButtonController Func:toString.");
            return "没有标签";
        }
        return stringBuilder.toString();
    }

    @Override
    public void onFocus(){
        Utility.speak(this.toString());
    }

    @Override
    public void onUpdate(List<Object> objectList) {

    }

    @Override
    public boolean onEvent(TalkBackPlusEventType eventType) {
        if (eventType.equals(TalkBackPlusEventType.EVENT_CENTER_DOUBLE_CLICK)) {
            if (buttonVariable == null) {
                //不可点击音效
            }
            if (buttonVariable != null) {
//                buttonVariable.click();
                //click 音效
            }
            return true;
        }
        return false;
    }
}
