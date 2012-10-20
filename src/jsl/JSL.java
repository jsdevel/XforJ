package jsl;

import java.io.File;
import jsl.*;
import jsl.strategies.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * /
package jsl;

/**
 *
 * @author Joseph W.S. Spencer
 */
public class JSL implements Characters {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      File testFile = new File(args[0]);
      CharWrapper characters = new CharWrapper(MainUtil.getChars(testFile));
      StrategyContext context = new StrategyContext();

      try {
         while(characters.length() > 0){
            context.executeCurrent(characters);
         }
         LOGGER.out(context.output.toString());
      } catch(Exception exc){
         LOGGER.out("Unable to parse \""+testFile.getAbsolutePath()+"\" for the following reason:\n"+exc.getMessage());
      }

   }
}
