/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsl.strategies;

import java.util.regex.Matcher;
import jsl.*;

/**
 *
 * @author Joseph W.S. Spencer
 */
public class Program implements Strategy, Characters {
   private Output output;
   public Program(Output output){
      this.output=output;
   }

   @Override
   public void execute(CharWrapper characters, StrategyContext context) throws Exception {
      String exception = "The opening of JSL templtes must be a JSL declaration.";
      String chunk;

      if(characters.charAt(0) == open){
         characters.shift(1);
         Matcher jsl = characters.match(JSL);

         if(jsl.find()){
            String js = jsl.group(1);
            characters.shift(3);
            Matcher space = characters.match(SPACE);

            if(space.find()){
               String sp = space.group(1);
               characters.shift(sp.length());
               Matcher namespace = characters.match(NAMESPACE);

               if(namespace.find()){
                  chunk = namespace.group(1);
                  characters.shift(chunk.length());
                  context.addNS(chunk);

                  if(characters.charAt(0) == close){
                     characters.shift(1);
                     context.addStrategy(new GlobalStatement(output));
                     return;
                  }
               }
            }

         }
      }
      throw new Exception(exception);
   }
}
