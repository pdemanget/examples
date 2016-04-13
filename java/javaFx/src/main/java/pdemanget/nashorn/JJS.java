package pdemanget.nashorn;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 
 * <br><b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 * 
 * @author pdemanget
 * @version 11 avr. 2016
 */
public class JJS {
  public static void main (String[] args) {
    try {
      new JJS().run();
    } catch (FileNotFoundException | NoSuchMethodException | ScriptException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void run () throws ScriptException, FileNotFoundException, NoSuchMethodException {
    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    engine.eval(new FileReader("script.js"));

    Invocable invocable = (Invocable) engine;

    Object result = invocable.invokeFunction("fun1", "Peter Parker");
    System.out.println(result);
    System.out.println(result.getClass());
  }
}
