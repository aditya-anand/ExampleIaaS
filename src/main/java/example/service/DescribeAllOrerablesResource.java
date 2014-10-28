package example.service;

import com.gs.collections.impl.list.mutable.FastList;
import com.wordnik.swagger.annotations.ApiOperation;
import example.ordermgmt.OrderSystem;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by aanand on 10/9/14.
 */
public class DescribeAllOrerablesResource {
    @Inject
    private OrderSystem orderSystem;

    @ApiOperation("Get orderables")
    @GET
    @Produces(APPLICATION_JSON)
    public DescribeOrderablesResponse doGet() {
        return new DescribeOrderablesResponse(orderSystem.getOrderableTypes().collect(o -> o.getFqn(), new FastList<String>()));
    }
}
