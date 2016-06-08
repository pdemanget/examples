package fr.pdemanget

import java.util.Map
import java.util.HashMap

/**
 * Xtend Notes.
 * 
 * One of my first class, here are some notes:
 * from java to xtend
 *          |java              | xtend
 * methods  | «Type» method()  | def method()
 * variables| «Type» variable  | var variable
 * variables| «Type» final v   | val v
 * lambda   | (a)->b           | [a |b ]
 * arrays   [ a[i]             | a.get(i)
 * 
 * 
 * 
 * @author pdemanget
 * @version 8 juin 2016
 */
class MainUtils {
	/**
     * parse arguments nix-style.
     */
    def static Map<String,String> parseArgs( String[] args ) {
        val param = new HashMap<String,String>();
        var otherArgs = "";
        var sep = "";
        for ( var i=0;i <args.length - 1;i++ ) {
            if ( args.get( i ).startsWith( "--" ) ) {
                param.put( args.get( i ).substring( 2 ), "" );
            }
            else if ( args.get( i ).startsWith( "-" ) ) {
                param.put( args.get( i ).substring( 1 ), args.get( i + 1 ) );
                i++;
            }
            else {
                otherArgs += sep + args.get( i );//bouh, c'est mal, tant pis.
                sep = ",";
            }
        }
        if (args.length > 0) {
          if (args.get(args.length - 1).startsWith("--")) {
    
            param.put(args.get(args.length - 1).substring(2), "");
          } else {
            otherArgs += sep + args.get(args.length - 1);// bouh, c'est mal, tant pis.
            sep = ",";
          }
        }
        param.put( "zargs", otherArgs );
        return param;

    }
}