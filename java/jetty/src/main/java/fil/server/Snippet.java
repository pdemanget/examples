package fil.server;

import java.io.Closeable;

import com.sun.jersey.api.container.filter.GZIPContentEncodingFilter;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.simple.container.SimpleServerFactory;

public class Snippet {
   // ===========================================================================
    // CONSTANTES
    // ===========================================================================

    // ===========================================================================
    // ATTRIBUTS
    // ===========================================================================

    // ===========================================================================
    // METHODES
    // ===========================================================================



  public static void main(String[] args) throws Exception{
      DefaultResourceConfig resourceConfig = new DefaultResourceConfig(RestServer.class);
      // The following line is to enable GZIP when client accepts it
      resourceConfig.getContainerResponseFilters().add(new GZIPContentEncodingFilter());
      Closeable server = SimpleServerFactory.create("http://0.0.0.0:5555", resourceConfig);
      try {
          System.out.println("Press any key to stop the service...");
          System.in.read();
      } finally {
          server.close();
      }
  }
}

/**
 * TODO
 *
 * <p><b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 *
 * @author pdemanget
 * @version 1.0.0, 7 janv. 2016
 */
