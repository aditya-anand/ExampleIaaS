package example.service;

import java.util.List;

/**
 * Created by aanand on 10/8/14.
 */
public class DescribeOrderablesResponse {
    private List<String> orderables;

    public DescribeOrderablesResponse() {
    }

    public DescribeOrderablesResponse(List<String> orderables) {
        this.orderables = orderables;
    }

    public List<String> getOrderables() {
        return orderables;
    }

    public void setOrderables(List<String> orderables) {
        this.orderables = orderables;
    }

}
