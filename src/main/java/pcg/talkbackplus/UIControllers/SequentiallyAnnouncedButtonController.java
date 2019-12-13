package pcg.talkbackplus.UIControllers;

import java.util.List;

import pcg.talkbackplus.TalkBackPlusEventType;
import pcg.talkbackplus.Variables.ButtonVariable;

public class SequentiallyAnnouncedButtonController extends BaseController {
    ButtonVariable buttonVariable;

    public SequentiallyAnnouncedButtonController(ButtonVariable buttonVariable) {
        this.buttonVariable = buttonVariable;
    }

    @Override
    public void onFocus() {

    }

    @Override
    public void onUpdate(List<Object> objectList) {
    }

    @Override
    public boolean onEvent(TalkBackPlusEventType eventType) {
        return false;
    }
}
