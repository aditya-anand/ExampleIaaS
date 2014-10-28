package example.startup.guicehk2;

import com.google.inject.Scopes;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.gs.collections.impl.factory.Iterables;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsConfig;
import com.wordnik.swagger.jaxrs.filter.JaxrsFilter;
import example.ordermgmt.OrderSystem;
import example.ordermgmt.RealOrderSystem;
import example.service.ExampleIaaS;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;

import javax.inject.Singleton;

/**
 * Created by aanand on 10/27/14.
 */
public class IaaSServletModule extends ServletModule {
    @Override
    protected void configureServlets() {
        System.out.println("Foo should be served");
        bind(OrderSystem.class).annotatedWith(Names.named("Messaging")).to(RealOrderSystem.class);
        bind(ExampleIaaS.class);
        serve("/*").with(SingletonJerseyServletContainer.class, Iterables.mMap(ServletProperties.JAXRS_APPLICATION_CLASS, GuiceFilterWrapplingHK2AppConfig.class.getName()));
        serve("/api-docs/").with(DefaultJaxrsConfig.class, Iterables.mMap("api.version", "1.0.0",
                "swagger.api.basepath", "http://duxdnx.duckdns.org:" + 9998 + "/", "swagger.filter", JaxrsFilter.class.getName()));
        bind(DefaultJaxrsConfig.class).in(Scopes.SINGLETON);
    }

    @Singleton
    protected static class SingletonJerseyServletContainer extends ServletContainer {
    }


}
