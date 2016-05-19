package pdem.snippets;

/**
 * 
 * <br><b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 * 
 * @author pdemanget
 * @version 19 mai 2016
 */
public class Password {
  
  static String chars1="!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~ ¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ";
  static String chars2="!\"$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~£¤¥¦§©°²µÉàçèéêëù";
  static char[] chars=chars2.toCharArray();
  
  public static void main (String[] args) {
    showChars();
    System.out.println(chars2.length() );
    genPass();
  }

  
   private static void showChars () {
    char[] bytes=new char [256];
    for(int i=30;i<bytes.length;i++){
      bytes[i]=(char)(i=='\r'||i=='\n'?'#': i);
    }
    System.out.println(new String(bytes));
    
    for(int i=0;i<bytes.length;i++){
      bytes[i]=(char)( i%10+0x30);
    }
    System.out.println(new String(bytes));
  }
   
  private static void genPass () {
    char[] bytes=new char [10];
    for(int i=0;i<bytes.length;i++){
      bytes[i]=randLetter();
    }
    System.out.println(new String(bytes));
  }

  private static char randLetter () {
    double l =Math.random()*111;
    //return (char)(l+30);
    return chars[(int)l];
   
  }

}
