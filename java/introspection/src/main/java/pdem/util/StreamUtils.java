package pdem.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.zip.ZipInputStream;

/**
 *
 *
 * @author pdemanget
 * @version 1 avr. 2016
 */
public class StreamUtils {


  public static String streamToString(InputStream inputStream,Charset encoding) throws IOException {
    final int bufferSize = 1024;
    final char[] buffer = new char [bufferSize];
    final StringBuilder out = new StringBuilder();

    Reader in = new InputStreamReader(inputStream, encoding);
    for (;;) {
      int rsz = in.read(buffer, 0, buffer.length);
      if (rsz < 0)
        break;
      out.append(buffer, 0, rsz);
    }
    return out.toString();
  }

  public String readFile (String pMsg) {

    StringBuilder result = new StringBuilder();
    BufferedReader lBis = null;
    try {
      String line;
      if (pMsg.endsWith(".gz")) {
        lBis = new BufferedReader(new InputStreamReader(new ZipInputStream(new FileInputStream(pMsg)), "UTF-8"));
      }
      else {
        lBis = new BufferedReader(new InputStreamReader(new FileInputStream(pMsg), "UTF-8"));
      }
      while ((line = lBis.readLine()) != null) {
        result.append(line);
        result.append("\n");
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (lBis != null) {
          lBis.close();
        }

      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    }
    return result.toString();
  }

}
