package pdem.snippets;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * <br><b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 * 
 * @author pdemanget
 * @version 31 mars 2016
 */
public class Regex {
  public static void main (String[] args) {
    Pattern p = Pattern.compile("cat");
    Matcher m = p.matcher("one cat two cats in the yard");
    StringBuffer sb = new StringBuffer();
    
    while (m.find()) {
        m.appendReplacement(sb, "dog");
    }
    m.appendTail(sb);
    System.out.println(sb.toString());
  }

}
