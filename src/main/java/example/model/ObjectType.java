package example.model;

import com.gs.collections.impl.list.mutable.FastList;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Created by aanand on 10/8/14.
 */
public class ObjectType implements FillableType {
    private String fqn;
    private List<Fillable> components;

    protected ObjectType() {
    }

    @NotNull
    public ObjectType(String fqn) {
        this.fqn = Optional.of(fqn).get();
        this.components = new FastList<>();
    }

    @NotNull
    public ObjectType withFillable(Fillable fillable) {
        components.add(fillable);
        return this;
    }

    @Override
    public String getTypeFQN() {
        return fqn;
    }

    public List<Fillable> getComponents() {
        return components;
    }

    public void setComponents(List<Fillable> components) {
        this.components = components;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjectType that = (ObjectType) o;

        if (!fqn.equals(that.fqn)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return fqn.hashCode();
    }

    @Override
    public String toString() {
        return "ObjectType{" +
                "fqn='" + fqn + '\'' +
                ", components=" + components +
                '}';
    }
}
