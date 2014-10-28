package example.ordermgmt;

import com.gs.collections.api.list.ListIterable;
import example.model.Orderable;

/**
 * Created by aanand on 10/7/14.
 */
public interface OrderSystem {


    ListIterable<Orderable> getOrderableTypes();

}
