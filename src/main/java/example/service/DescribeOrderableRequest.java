package example.service;

import java.util.List;

/**
 * Created by aanand on 10/9/14.
 */
public class DescribeOrderableRequest {
    private List<String> fqns;

    public DescribeOrderableRequest() {
    }

    public List<String> getFqns() {
        return fqns;
    }

    public void setFqns(List<String> fqns) {
        this.fqns = fqns;
    }
}
