package pdem.stackoverflow;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 
 * <br><b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 * 
 * @author pdemanget
 * @version 29 mars 2016
 */
public class StackOverflowServerSocket {
  public static void main (String[] args) {
    try {
      ServerSocket serverSocket = new ServerSocket(18080);
      new Thread() {
        @Override
        public void run () {
          try {
            Thread.sleep(1000);
            serverSocket.close();
          } catch (Exception e) {
            e.printStackTrace();
          }
          
        }
      }.start();
      
      serverSocket.accept();
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
