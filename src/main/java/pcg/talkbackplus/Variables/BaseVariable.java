package pcg.talkbackplus.Variables;

import java.util.List;

abstract public class BaseVariable {
    List<BaseVariable> children;
    abstract void buildVariableHierarchy();

    @Override
    abstract public String toString();
}