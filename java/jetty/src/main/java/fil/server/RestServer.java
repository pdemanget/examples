package fil.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * http://stackoverflow.com/questions/8277409/jax-rs-with-embedded-server
 */
@Path("/helloworld")
public class RestServer {

    @GET
    @Produces("text/html")
    public String getMessage(){
        System.out.println("sayHello()");
        return "Hello, world!";
    }
}