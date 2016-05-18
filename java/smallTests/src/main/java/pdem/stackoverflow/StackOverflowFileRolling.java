package pdem.stackoverflow;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

    /** 
     * usage:
     *  new StackOverflowFileRolling("/var/log/stackoverflowfilerolling","yyyy-MM-dd").write("test");
     */
    public class StackOverflowFileRolling extends Writer {
      String baseFilePath;
      String datePattern;
    
      public StackOverflowFileRolling (String baseFilePath, String datePattern) {
        super();
        this.baseFilePath = baseFilePath;
        this.datePattern = datePattern;
      }
    
      @Override
      public void write (char[] cbuf, int off, int len) throws IOException {
        String name = baseFilePath + new SimpleDateFormat(datePattern).format(new Date()) + ".log";
        File file = new File(name);
        file.getParentFile().mkdirs();
        try (FileWriter fileWriter = new FileWriter(file, true)) {
          fileWriter.write(cbuf, off, len);
        }
    
      }
    
      @Override
      public void flush () throws IOException {
        // TODO Auto-generated method stub
    
      }
    
      @Override
      public void close () throws IOException {
        // TODO Auto-generated method stub
    
      }
    
      public static void main (String[] args) {
        try (
            PrintWriter logger = new PrintWriter(
                new StackOverflowFileRolling("/var/log/stackoverflowfilerolling", "yyyy-MM-dd"))
            ) {
          
          logger.println("test");
          logger.println("test2");
        }
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
      }
    
    }
