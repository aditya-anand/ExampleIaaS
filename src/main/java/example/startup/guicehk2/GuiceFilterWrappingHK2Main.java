package example.startup.guicehk2;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import example.service.ExampleIaaS;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.FilterRegistration;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * Created by aanand on 10/7/14.
 */
public class GuiceFilterWrappingHK2Main {
    static final URI BASE_URI = getBaseURI();

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(9998).build();
    }

    public void startServer(Module... modules) throws IOException {
        HttpServer server = HttpServer.createSimpleServer(null, BASE_URI.getPort());

        WebappContext webApp = new WebappContext(getClass().getSimpleName(), BASE_URI.getRawPath());
        webApp.addListener(new InjectorInitializer(Arrays.asList(modules)));
        FilterRegistration guiceFilter = webApp.addFilter("guiceFilter", GuiceFilter.class);
        guiceFilter.addMappingForUrlPatterns(null, "/*");

        ServletRegistration jaxrsServletReg = webApp.addServlet("dummyServeletToActivateGuiceFilter", ServletContainer.class);
        jaxrsServletReg.addMapping("/DummyServeletToActivateGuiceFilter");
        webApp.deploy(server);


        server.start();

    }

    public void startServer() throws IOException {
        startServer(new IaaSServletModule());
    }

    public static void main(String... args) throws Exception {
        new GuiceFilterWrappingHK2Main().startServer();
        synchronized (ExampleIaaS.class) {
            ExampleIaaS.class.wait();
        }

    }


    private static class InjectorInitializer extends GuiceServletContextListener {
        private final List<Module> modules;

        public InjectorInitializer(List<Module> modules) {
            this.modules = modules;
        }

        @Override
        protected Injector getInjector() {
            return Guice.createInjector(modules);
        }
    }
}
