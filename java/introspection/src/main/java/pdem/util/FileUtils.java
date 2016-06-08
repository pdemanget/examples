package pdem.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * http://winterbe.com/posts/2015/03/25/java8-examples-string-number-math-files/
 */
public class FileUtils {
  public static String loadFile (String path) throws IOException, URISyntaxException {
    java.net.URL url = FileUtils.class.getResource(path);
    java.nio.file.Path resPath = java.nio.file.Paths.get(url.toURI());
    return new String(java.nio.file.Files.readAllBytes(resPath), StandardCharsets.UTF_8);
  }

  public static void readFileAppendLine (String file) {
    try {
      List<String> lines = Files.readAllLines(Paths.get(file));
      for (int i = 0; i < lines.size(); i++) {
        lines.set(i, lines.get(i) + ";");
      }
      Files.write(Paths.get(file), lines);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
  
  public static String normalize(String file){
    return Paths.get(file).normalize().toString();
  }
  
  //lazy test -- its bad -- don't care
  public static void main (String[] args) {
    System.out.println(normalize("toto/../titi/tata/"));
  }
}
