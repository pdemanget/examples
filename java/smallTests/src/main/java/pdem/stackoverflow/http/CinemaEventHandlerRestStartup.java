//package pdem.stackoverflow.http;
//
//import java.net.URI;
//import com.sun.net.httpserver.HttpServer;
//
//
//public class CinemaEventHandlerRestStartup {
//
//  private final static int port = 9998;
//  private final static String host = "http://localhost/";
//
//  public static void main(String[] args) {
//    URI baseUri = UriBuilder.fromUri(host).port(port).build();
//    ResourceConfig config = new ResourceConfig(CinemaEventHandler.class);
//    HttpServer server = JdkHttpServerFactory.createHttpServer(baseUri, config);
//  }
//}