package example.model;

import com.gs.collections.impl.factory.Iterables;

import static example.model.PrimitiveFillableType.*;

/**
 * Created by aanand on 10/8/14.
 */
public class MessagingOrderables {
    public static final Orderable DESTINATION_V1 = new Orderable(
            "JMSDestination", 1, Iterables.mList(
            new Fillable("name", PrimitiveFillableType.STRING_TYPE).withRequired(true),
            new Fillable("type", PrimitiveFillableType.STRING_TYPE).withRequired(true),
            new Fillable("guaranteed", PrimitiveFillableType.BOOLEAN_TYPE).withRequired(false)
    )
    );

    public static final Orderable AMQP_EXCHANGE_V1 = new Orderable(
            "AMQPExchange", 1, Iterables.mList(
            new Fillable("name", STRING_TYPE).withRequired(true),
            new Fillable("clusterName", STRING_TYPE).withRequired(true),
            new Fillable("type", STRING_TYPE).withRequired(true)
    ));

    public static final ObjectType RABBIT_BROKER = new ObjectType("iaas.messaging.rabbitmq.Broker")
            .withFillable(new Fillable("managementPort", NUMBER_TYPE).withRequired(true))
            .withFillable(new Fillable("logicalName", STRING_TYPE).withRequired(true))
            .withFillable(new Fillable("nodeType", STRING_TYPE).withRequired(true))
            .withFillable(new Fillable("plugins", new ArrayType(STRING_TYPE)));

    public static final Orderable AMQP_CLUSTER_V1 = new Orderable(
            "RabbitCluster", 1, Iterables.mList(
            new Fillable("name", STRING_TYPE).withRequired(true),
            new Fillable("rabbitMQVersion", STRING_TYPE).withRequired(true),
            new Fillable("autoUpgradeMinorVersion", BOOLEAN_TYPE).withRequired(false),
            new Fillable("type", STRING_TYPE).withRequired(true),
            new Fillable("brokers", new ArrayType(RABBIT_BROKER)).withRequired(true)
    ));

    private static Fillable reqiredStringProperty(String propName) {

        return new Fillable();
    }
}
