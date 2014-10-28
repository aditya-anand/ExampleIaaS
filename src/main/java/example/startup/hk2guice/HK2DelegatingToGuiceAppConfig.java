package example.startup.hk2guice;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jersey2.InstrumentedResourceMethodApplicationListener;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jersey.listing.JerseyApiDeclarationProvider;
import com.wordnik.swagger.jersey.listing.JerseyResourceListingProvider;
import example.ordermgmt.OrderSystem;
import example.ordermgmt.RealOrderSystem;
import example.service.ExampleIaaS;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.jvnet.hk2.guice.bridge.api.HK2IntoGuiceBridge;

import javax.inject.Inject;

public class HK2DelegatingToGuiceAppConfig extends ResourceConfig {
    private Injector injector;

    @Inject
    public HK2DelegatingToGuiceAppConfig(ServiceLocator hk2ServiceLocator) {
        super();
        System.out.println("** Registering resources");

        register(JacksonJsonProvider.class);
        register(ApiListingResourceJSON.class);
        register(JerseyApiDeclarationProvider.class);
        register(JerseyResourceListingProvider.class);
        packages(ExampleIaaS.class.getPackage().getName() + ";com.wordnik.swagger.jaxrs.listing;");


        GuiceBridge.getGuiceBridge().initializeGuiceBridge(hk2ServiceLocator);
        GuiceIntoHK2Bridge guice2Hk2 = hk2ServiceLocator.getService(GuiceIntoHK2Bridge.class);
        HK2IntoGuiceBridge hk2Guice = new HK2IntoGuiceBridge(hk2ServiceLocator);
        guice2Hk2.bridgeGuiceInjector(getInjector(hk2Guice));
        MetricRegistry metricRegistry;
        metricRegistry = new MetricRegistry();
        register(metricRegistry);
        register(new InstrumentedResourceMethodApplicationListener(metricRegistry));
        JmxReporter reporter = JmxReporter.forRegistry(metricRegistry).build();
        reporter.start();
    }

    protected Injector getInjector(HK2IntoGuiceBridge hk2Guice) {
        return (injector != null) ? injector : (injector = Guice.createInjector(hk2Guice, new ExampleAppModule()));
    }


    public static class ExampleAppModule extends ServletModule {

        @Override
        protected void configureServlets() {
            super.configureServlets();
            bind(OrderSystem.class).annotatedWith(Names.named("Messaging")).to(RealOrderSystem.class);
            bind(ExampleIaaS.class);
        }
    }
}
