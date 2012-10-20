/*
 * Copyright 2012 Joseph Spencer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jsl.strategies;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jsl.*;

/**
 *
 * @author Joseph Spencer
 */
public class Template implements Strategy, Characters {
   private boolean hasStatement;
   private Pattern statementPattern;
   private Output output;
   Template(Output output){
      this.output=output;
   }

   @Override
   public void execute(CharWrapper characters, StrategyContext context) throws Exception {
      characters.removeSpace();

      if(characters.charAt(0) == open){
         //closing
         if(characters.charAt(1) == forward){
            //template
            if(characters.charAt(2) == t){
               context.removeStrategy();
               return;
            //others
            } else {


            }
         } else if(characters.match(RESERVED_SEQUENCE).find()){

         } else {//assuming access to data here;
            characters.shift(1);
            Matcher name = characters.match(NAME);
            if(name.find()){
               String nm = name.group(1);
               characters.shift(nm.length());
               output.prepend("\"+data."+nm+"+\"");
               characters.shift(1);//close block
               context.removeStrategy();
               return;
            }
         }
      }
      Matcher data = characters.match(DATA);
      if(data.find()){
         String match = data.group(1);
         output.prepend(match);
         characters.shift(match.length());
         return;
      }
      throw new Exception("Invalid Template");
   }
}
