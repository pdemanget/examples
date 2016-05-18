package pdemanget.javafx.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import org.apache.commons.io.input.ReaderInputStream;

/**
 * Génération d'un CSS (ou autre ressource) sur le protocole "gen:".
 *  
 * source
 * http://stackoverflow.com/questions/24704515/in-javafx-8-can-i-provide-a-stylesheet-from-a-string
 * 
 * see also
 * <groupId>org.lesscss</groupId>
<artifactId>lesscss-maven-plugin</artifactId>
<version>1.3.3</version>
 */
public class CssGenLoader {
  public String load (URL url) {
    System.out.println("URL:" + url);
    return "* { -c-color-1: #F2F2F2; -c-color-2: #E9DDF8; -c-color-3: #B49CD3;  -c-color-4: #795E9B; -c-color-5: #2C1C40; } .root {-fx-font-size: 10px ;}";
  }

  public void initialize () {
    // to be done only once.
    URL.setURLStreamHandlerFactory(new StringURLStreamHandlerFactory());
  }

  private class StringURLConnection extends URLConnection {
    public StringURLConnection (URL url) {
      super(url);
    }

    @Override
    public void connect () throws IOException {
    }

    @Override
    public InputStream getInputStream () throws IOException {
      long start = System.currentTimeMillis();

      String css = load(url);

      System.out.println("loadLess:" + (System.currentTimeMillis() - start));
      StringReader reader = new StringReader(css);
      return new ReaderInputStream(reader);
    }

  }

  private class StringURLStreamHandlerFactory implements URLStreamHandlerFactory {
    URLStreamHandler streamHandler = new URLStreamHandler() {
      @Override
      protected URLConnection openConnection (URL url) throws IOException {
        return new StringURLConnection(url);
      }
    };

    @Override
    public URLStreamHandler createURLStreamHandler (String protocol) {
      if ("gen".equals(protocol)) {
        return streamHandler;
      }
      return null;
    }
  }

}