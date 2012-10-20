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
public class GlobalStatement implements Strategy, Characters {
   boolean hasTemplate;
   Output currentOutput;
   Output parentOutput;

   GlobalStatement(Output output){
      currentOutput= new Output();
      parentOutput=output.prepend(currentOutput);
   }

   @Override
   public void execute(CharWrapper characters, StrategyContext context) throws Exception {
      if(!hasTemplate){
         if(characters.charAt(0) == open){
            hasTemplate=true;
            characters.shift(1);

            Matcher template = characters.match(TEMPLATE);
            if(template.find()){
               characters.shift(template.group(1).length());

               if(characters.removeSpace()){

                  Matcher name = characters.match(NAME);
                  if(name.find()){
                     String nm = name.group(1);
                     Output templateOutput = new Output();

                     characters.shift(nm.length());

                     currentOutput.
                        prepend(context.getNS()+"."+nm+"="+nm+";function "+nm+"(_data, _params){var data=_data||{},params=_params||{};return \"").
                        prepend(templateOutput).
                        prepend("\"}");

                     if(characters.charAt(0) == close){
                        characters.shift(1);
                        context.addStrategy(new Template(templateOutput));
                        return;
                     }
                  }
               }

            }
         } else {
            if(characters.removeSpace()){
               return;
            }
         }
      } else {
         if(characters.removeSpace()){
            return;
         }
         if(characters.charAt(0) == open && characters.charAt(1) == forward){
            characters.shift(2);
            Matcher template = characters.match(TEMPLATE);
            if(template.find()){
               characters.shift(template.group(1).length());
               if(characters.charAt(0) == close){
                  hasTemplate=false;
                  characters.shift(1);
                  return;
               }
            }
         }
      }
      throw new Exception("Something happened while evaluateing GlobalStatement.");
   }
}
