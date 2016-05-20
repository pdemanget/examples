package stackoverflow;

import java.io.IOException;

import org.openjdk.jmh.runner.RunnerException;

/**
 * 
 * <br><b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 * 
 * @author pdemanget
 * @version 20 mai 2016
 */
public class Bench {
  public static void main (String[] args) throws RunnerException, IOException {
    long start=System.currentTimeMillis();
    org.openjdk.jmh.Main.main(args);
    long end=System.currentTimeMillis();
    System.out.printf("Bench done in %d ms",end-start);
  }

}
