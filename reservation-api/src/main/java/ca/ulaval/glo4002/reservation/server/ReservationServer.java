package ca.ulaval.glo4002.reservation.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class ReservationServer {
  private Server server;

  public ReservationServer(int port, Object[] resources) {
    server = buildServer(port, resources);
  }

  private Server buildServer(int port, Object[] resources) {
    server = new Server(port);
    ServletContextHandler contextHandler = new ServletContextHandler(server, "/");
    ResourceConfig packageConfig = createResourceConfig(resources);
    ServletContainer container = new ServletContainer(packageConfig);
    ServletHolder servletHolder = new ServletHolder(container);

    contextHandler.addServlet(servletHolder, "/*");

    return server;
  }

  private ResourceConfig createResourceConfig(Object[] resources) {
    ResourceConfig packageConfig = new ResourceConfig();
    for (Object resource : resources) {
      packageConfig.register(resource);
    }
    return packageConfig;
  }

  public void start() {
    try {
      server.start();
      server.join();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (server.isRunning()) {
        server.destroy();
      }
    }
  }
}
