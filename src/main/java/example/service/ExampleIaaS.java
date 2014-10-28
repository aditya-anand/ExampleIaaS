package example.service;

import com.gs.collections.api.block.procedure.Procedure;
import com.gs.collections.impl.set.mutable.UnifiedSet;
import com.wordnik.swagger.annotations.*;
import example.model.Orderable;
import example.ordermgmt.OrderSystem;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/IaaS/v1")
@Api(value = "/IaaS/v1", description = "An example IaaS service")
public class ExampleIaaS {

    @Inject
    @Named("Messaging")
    private OrderSystem orderSystem;


    /**
     * Example of how to externalize implementation of a "sub-resource"
     *
     * @return
     */
    @Path("/DescribeAllOrderables")
    @ApiOperation(value = "Get all orderables", response = DescribeOrderablesResponse.class)
    @ApiResponses({
            @ApiResponse(code = 404, message = "No orderables found")
    })
    public Class<DescribeAllOrerablesResource> describeAllOrderables() {
        return DescribeAllOrerablesResource.class;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/DescribeOrderables")
    @ApiOperation(value = "Describe given orderables in detail", response = DescribeOrderablesDetailResponse.class)
    public DescribeOrderablesDetailResponse describeAllOrderables(@ApiParam(name = "describeOrdablesRequest", required = true) DescribeOrderableRequest describeOrderableRequest) {
        DescribeOrderablesDetailResponse response = new DescribeOrderablesDetailResponse();
        Procedure<Orderable> addOrderableToResponse = (o) -> response.withOrderable(o);

        Set<String> reqestedFqns = new UnifiedSet<>();
        if (describeOrderableRequest != null && describeOrderableRequest.getFqns() != null) {
            reqestedFqns.addAll(describeOrderableRequest.getFqns());
            orderSystem.getOrderableTypes().select(o -> reqestedFqns.contains(o.getFqn())).forEach(addOrderableToResponse);
        } else {
            orderSystem.getOrderableTypes().forEach(addOrderableToResponse);
        }
        return response;
    }


    @GET
    @Produces(APPLICATION_JSON)
    @Path("/dev/null")
    @ApiOperation(value = "Empty call for Apigee test", response = Map.class, hidden = true)
    public Map<String, String> devNull() {
        return Collections.emptyMap();
    }

}
