package pdem.stackoverflow;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 
 * <br><b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 * 
 * @author pdemanget
 * @version 1 avr. 2016
 */
    public class SOResourceLoad {
      
      public static void main (String[] args) {
        new SOResourceLoad().run();
      }
    
      private void run () {
        try {
          URL resource = getClass().getResource("SOResourceLoad.class");
          System.out.println( resource );
          System.out.println( resource.toURI() );
          System.out.println( new File(resource.toURI()) );
        } catch (URISyntaxException e) {
          e.printStackTrace();
        }
        
      }
    
    }
