package pcg.talkbackplus.UIControllers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pcg.talkbackplus.TalkBackPlusEventType;
import pcg.talkbackplus.Utility;
import pcg.talkbackplus.Variables.TextVariable;

public class TextController extends BaseController {
    private List<Object> component;

    public TextController(List<Object> objectList) {
        assert objectList != null;
        this.component = new ArrayList<>();
        this.component.addAll(objectList);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object comp : component) {
            if (comp instanceof TextVariable) {
                stringBuilder.append(((TextVariable)comp).toString());
            } else if (comp instanceof String) {
                stringBuilder.append((String)comp);
            } else if (comp instanceof TextController) {
                stringBuilder.append(((TextController)comp).toString());
            } else {
                Log.w("Controller", "Unexpected Type In Class:TextController Func:toString");
                continue;
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void onFocus() {
        Utility.speak(this.toString());
    }

    @Override
    public void onUpdate(List<Object> objectList) {

    }

    @Override
    public boolean onEvent(TalkBackPlusEventType eventType) {
        if (eventType.equals(TalkBackPlusEventType.EVENT_CENTER_DOUBLE_CLICK)) {
            //播放空音效
            return true;
        }
        return false;
    }
}
