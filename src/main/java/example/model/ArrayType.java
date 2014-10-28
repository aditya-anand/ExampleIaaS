package example.model;

/**
 * Created by aanand on 10/8/14.
 */
public class ArrayType implements FillableType {
    private final FillableType componentType;

    public ArrayType(FillableType componentType) {
        this.componentType = componentType;
    }

    @Override
    public String getTypeFQN() {
        return "Array<" + componentType.getTypeFQN() + ">";
    }

    public FillableType getComponentType() {
        return componentType;
    }
}
