package example.ordermgmt;

import com.gs.collections.api.list.ListIterable;
import com.gs.collections.impl.factory.Iterables;
import example.model.Orderable;

import static example.model.MessagingOrderables.*;

/**
 * Created by aanand on 10/7/14.
 */
public class RealOrderSystem implements OrderSystem {
    @Override
    public ListIterable<Orderable> getOrderableTypes() {
        return Iterables.iList(DESTINATION_V1, AMQP_EXCHANGE_V1, AMQP_CLUSTER_V1);
    }
}
