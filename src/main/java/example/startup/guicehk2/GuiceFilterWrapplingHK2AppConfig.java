package example.startup.guicehk2;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Injector;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jersey.listing.JerseyApiDeclarationProvider;
import com.wordnik.swagger.jersey.listing.JerseyResourceListingProvider;
import example.service.ExampleIaaS;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;
import javax.servlet.ServletContext;

public class GuiceFilterWrapplingHK2AppConfig extends ResourceConfig {
    @Inject
    public GuiceFilterWrapplingHK2AppConfig(ServiceLocator hk2ServiceLocator, ServletContext servletContext) {
        super();
        System.out.println("** Registering resources using " + hk2ServiceLocator);

        register(JacksonJsonProvider.class);
        register(ApiListingResourceJSON.class);
        register(JerseyApiDeclarationProvider.class);
        register(JerseyResourceListingProvider.class);
        packages(ExampleIaaS.class.getPackage().getName() + ";com.wordnik.swagger.jaxrs.listing;");


        GuiceBridge.getGuiceBridge().initializeGuiceBridge(hk2ServiceLocator);
        GuiceIntoHK2Bridge guice2Hk2 = hk2ServiceLocator.getService(GuiceIntoHK2Bridge.class);
        //guice2Hk2.bridgeGuiceInjector(injectorTL.get());
        guice2Hk2.bridgeGuiceInjector((Injector) servletContext.getAttribute(Injector.class.getName()));
    }
}
