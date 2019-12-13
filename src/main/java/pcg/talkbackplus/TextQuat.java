package pcg.talkbackplus;

import java.util.ArrayList;
import java.util.List;

import pcg.talkbackplus.Variables.TextVariable;

public class TextQuat {
    public final static int USE_TEXT = 1;
    public final static int USE_CONTENT = 2;

    private TextVariable tv;
    private String reg;
    private List<Integer> group;
    private int method;

    private TextQuat(TextVariable tv, int method, String reg, List<Integer> group){
        this.tv = tv;
        this.reg = reg;
        this.group = new ArrayList<>();
        this.group.addAll(group);
        this.method = method;
    }

    public static TextQuat obtain(TextVariable tv, int method, String reg, List<Integer> group){
        if(method != USE_TEXT && method != USE_CONTENT){
            return null;
        }

        return new TextQuat(tv, method, reg, group);
    }

    @Override
    public String toString() {
        return "";
    }
}
