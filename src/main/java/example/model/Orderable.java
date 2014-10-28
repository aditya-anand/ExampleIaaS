package example.model;

import java.util.List;

/**
 * Created by aanand on 10/8/14.
 */
public class Orderable {
    private String fqn;
    private int version;
    private List<Fillable> fillables;

    public Orderable() {
    }

    public Orderable(String fqn, int version, List<Fillable> fillables) {
        this.fillables = fillables;
        this.fqn = fqn;
        this.version = version;
    }

    public String getFqn() {
        return fqn;
    }

    public void setFqn(String fqn) {
        this.fqn = fqn;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<Fillable> getFillables() {
        return fillables;
    }

    public void setFillables(List<Fillable> fillables) {
        this.fillables = fillables;
    }
}
