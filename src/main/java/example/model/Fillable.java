package example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by aanand on 10/8/14.
 */
public class Fillable {
    private String name;
    private FillableType type;
    private String typeFQN;
    private boolean required;

    public Fillable() {
        required = false;
    }

    public Fillable(String name, FillableType type) {
        this.name = name;
        this.type = type;
        this.typeFQN = type.getTypeFQN();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeFQN() {
        return typeFQN;
    }

    @JsonIgnore
    public FillableType getType() {
        return type;
    }

    /**
     * @param typeFQN
     * @throws java.lang.IllegalStateException if typeFQN is attempted and type is already known.
     */
    public void setTypeFQN(String typeFQN) {
        if (type != null) {
            throw new IllegalStateException();
        }
        this.typeFQN = typeFQN;
    }

    Fillable withRequired(boolean required) {
        setRequired(required);
        return this;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isRequired() {
        return required;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fillable fillable = (Fillable) o;

        if (!name.equals(fillable.name)) return false;
        if (!typeFQN.equals(fillable.typeFQN)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + typeFQN.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Fillable{" +
                "name='" + name + '\'' +
                ", typeFQN='" + typeFQN + '\'' +
                '}';
    }
}
