package pdem.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipInputStream;

/**
 * 
 * <br><b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 * 
 * @author pdemanget
 * @version 1 avr. 2016
 */
public class StreamUtils {

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
