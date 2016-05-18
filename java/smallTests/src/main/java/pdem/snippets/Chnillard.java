package pdem.snippets;

import java.awt.AWTException;

import com.sun.glass.events.KeyEvent;


public class Chnillard {
public static void main (String[] args) {
  
  //Robot robot = WinPlatformFactory.getPlatformFactory ().createApplication ().createRobot ();
  for(int i=0;i<3;i++){
    //robot.keyPress (KeyEvent.VK_NUM_LOCK);
    try {
      java.awt.Robot robot = new java.awt.Robot();
      try {
        keyHit(robot, KeyEvent.VK_CAPS_LOCK);
        keyHit(robot, KeyEvent.VK_NUM_LOCK);
        Thread.sleep (500);
        keyHit(robot, KeyEvent.VK_CAPS_LOCK);
        keyHit (robot,KeyEvent.VK_SCROLL_LOCK);
        Thread.sleep (500);
        keyHit (robot,KeyEvent.VK_SCROLL_LOCK);
        keyHit (robot,KeyEvent.VK_NUM_LOCK);
        Thread.sleep (500);
        
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } catch (AWTException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
  }
}

private static void keyHit (java.awt.Robot robot, int vkCapsLock) {
  robot.keyPress (vkCapsLock);
  robot.keyRelease (vkCapsLock);
}
}
