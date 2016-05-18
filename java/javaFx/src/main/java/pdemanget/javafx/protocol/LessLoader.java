package pdemanget.javafx.protocol;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import org.apache.commons.io.input.ReaderInputStream;

import com.github.sommeri.less4j.LessCompiler;
import com.github.sommeri.less4j.core.ThreadUnsafeLessCompiler;

/**
 * merge de https://github.com/SomMeri/less4j
 * et 
 * https://tomsondev.bestsolution.at/2013/08/07/using-less-in-javafx/
 * et 
 * http://stackoverflow.com/questions/24704515/in-javafx-8-can-i-provide-a-stylesheet-from-a-string
 * 
 * see also
 * <groupId>org.lesscss</groupId>
<artifactId>lesscss-maven-plugin</artifactId>
<version>1.3.3</version>
 */
public class LessLoader {


  // ...
  public String loadLess (URL lessFile) {
    try {
      // StringBuilder lessContent = new StringBuilder();
      // readFileContent(lessFile, lessContent);
      //
      // Object rv = ((Invocable)engine).invokeFunction("parseString", lessContent.toString());
      // File f = File.createTempFile("less_", ".css");
      // f.deleteOnExit();
      //
      // try(FileOutputStream out = new FileOutputStream(f) ) {
      // out.write(rv.toString().getBytes());
      // out.close();
      // }
      // return f.toURI().toURL();

      LessCompiler compiler = new ThreadUnsafeLessCompiler();
      return compiler.compile(lessFile).getCss();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
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
      String file = url.getFile();
      long start=System.currentTimeMillis();
      String css = loadLess(getClass().getResource(file));
      System.out.println("loadLess:"+(System.currentTimeMillis()-start));
      StringReader reader = new StringReader(css);
      return new ReaderInputStream(reader);
    }
  }

  private class StringURLStreamHandlerFactory implements URLStreamHandlerFactory {
    URLStreamHandler streamHandler = new URLStreamHandler() {
      @Override
      protected URLConnection openConnection (URL url) throws IOException {
        if (url.toString().toLowerCase().endsWith(".less")) {
          return new StringURLConnection(url);
        }
        throw new FileNotFoundException();
      }
    };

    @Override
    public URLStreamHandler createURLStreamHandler (String protocol) {
      if ("internal".equals(protocol)) {
        return streamHandler;
      }
      return null;
    }
  }

}