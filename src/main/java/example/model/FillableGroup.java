package example.model;

import com.gs.collections.impl.factory.Lists;

import java.util.Collection;
import java.util.List;

public class FillableGroup extends Fillable {
    private List<Fillable> members;

    public FillableGroup() {
    }

    public FillableGroup(String name, FillableType type, Collection<Fillable> members) {
        super(name, type);
        this.members = Lists.mutable.withAll(members);
    }
}
