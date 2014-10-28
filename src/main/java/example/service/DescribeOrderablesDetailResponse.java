package example.service;

import com.gs.collections.api.RichIterable;
import com.gs.collections.api.block.procedure.Procedure;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.set.mutable.UnifiedSet;
import example.model.ArrayType;
import example.model.Fillable;
import example.model.ObjectType;
import example.model.Orderable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * Created by aanand on 10/8/14.
 */
public class DescribeOrderablesDetailResponse {
    private Set<ObjectType> complexTypes;
    private List<Orderable> orderables;

    @NotNull
    public DescribeOrderablesDetailResponse withComplexType(ObjectType type) {
        if (complexTypes == null) {
            complexTypes = new UnifiedSet<>();
        }

        if (complexTypes.add(type)) {
            registerComplexTypes(type.getComponents());
        }
        return this;
    }

    @NotNull
    public DescribeOrderablesDetailResponse withOrderable(Orderable orderable) {
        if (orderables == null) {
            orderables = new FastList<>();
        }
        registerComplexTypes(orderable.getFillables());
        orderables.add(orderable);
        return this;
    }

    private void registerComplexTypes(Iterable<Fillable> fillables) {
        ((RichIterable<Fillable>) fillables)
                .select(f -> f.getType() instanceof ObjectType)
                .collect(f -> (ObjectType) f.getType())
                .forEach((Procedure<ObjectType>) o -> withComplexType(o));

        ((RichIterable<Fillable>) fillables)
                .select(f -> f.getType() instanceof ArrayType && ((ArrayType) f.getType()).getComponentType() instanceof ObjectType)
                .collect(f -> (ObjectType) ((ArrayType) f.getType()).getComponentType())
                .forEach((Procedure<ObjectType>) o -> withComplexType(o));
    }

    public List<ObjectType> getComplexTypes() {
        return new FastList<>(complexTypes);
    }

    public void setComplexTypes(List<ObjectType> complexTypes) {
        this.complexTypes = new UnifiedSet<>(complexTypes);
    }

    public List<Orderable> getOrderables() {
        return orderables;
    }

    public void setOrderables(List<Orderable> orderables) {
        this.orderables = orderables;
    }
}
