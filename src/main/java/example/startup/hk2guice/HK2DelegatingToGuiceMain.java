package example.startup.hk2guice;

import com.gs.collections.impl.factory.Iterables;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsConfig;
import com.wordnik.swagger.jaxrs.filter.JaxrsFilter;
import example.CorsFilter;
import example.service.ExampleIaaS;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;

import javax.inject.Singleton;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

/**
 * Created by aanand on 10/7/14.
 */
@Singleton
public class HK2DelegatingToGuiceMain {
    static final URI BASE_URI = getBaseURI();

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(9998).build();
    }

    public void startServer() throws IOException {
        HttpServer server = HttpServer.createSimpleServer(null, BASE_URI.getPort());
        WebappContext webApp = new WebappContext(getClass().getSimpleName(), BASE_URI.getRawPath());
        ServletRegistration jaxrsServletReg = webApp.addServlet("foo", ServletContainer.class);
        jaxrsServletReg.addMapping("/*");
        jaxrsServletReg.setInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, HK2DelegatingToGuiceAppConfig.class.getName());
        ServletRegistration swaggerServletReg = webApp.addServlet("swagger", DefaultJaxrsConfig.class);
        swaggerServletReg.setInitParameters(Iterables.mMap("api.version", "1.0.0",
                "swagger.api.basepath", "http://duxdnx.duckdns.org:" + BASE_URI.getPort() + "/", "swagger.filter", JaxrsFilter.class.getName()));
        swaggerServletReg.setLoadOnStartup(1);
        webApp.addFilter("cors", CorsFilter.class).addMappingForUrlPatterns(null, "/*");
        webApp.deploy(server);

        server.start();
    }

    public static void main(String... args) throws Exception {
        new HK2DelegatingToGuiceMain().startServer();
        synchronized (ExampleIaaS.class) {
            ExampleIaaS.class.wait();
        }
    }

}
