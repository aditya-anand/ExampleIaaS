package example.model;

/**
 * Created by aanand on 10/8/14.
 */
public final class PrimitiveFillableType implements FillableType {
    public static final PrimitiveFillableType STRING_TYPE = new PrimitiveFillableType("system." + String.class.getSimpleName());
    public static final PrimitiveFillableType BOOLEAN_TYPE = new PrimitiveFillableType("system." + Boolean.class.getSimpleName());
    public static final PrimitiveFillableType NUMBER_TYPE = new PrimitiveFillableType("system." + Number.class.getName());

    private String typeFQN;

    private PrimitiveFillableType() {
    }

    private PrimitiveFillableType(String typeFQN) {
        this.typeFQN = typeFQN;
    }

    @Override
    public String getTypeFQN() {
        return typeFQN;
    }
}
