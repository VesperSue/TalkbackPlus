package pcg.talkbackplus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pcg.talkbackplus.UIControllers.BaseController;
import pcg.talkbackplus.Variables.BaseVariable;

public class TalkBackPlusPageManager
{
    private int lastpage;
    private Set<BaseVariable> lastVariables;
    private TalkBackPlusService talkBackPlusService;

    // todo 通过页面编号获得所有变量
    public List<BaseVariable> getAllVariables(int pageIndex)
    {
        return new ArrayList<>();
    }

    // todo 通过页面编号获得所有UiController
    public List<BaseController> getAllUiController(int pageIndex)
    {
        return new ArrayList<>();
    }



}
