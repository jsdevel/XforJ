/*
 * Copyright 2012 Joseph Spencer.
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

package com.xforj.productions;

import com.xforj.Output;
import com.xforj.CharWrapper;

/**
 *
 * @author Joseph Spencer
 */
public class Operator extends Production {
   public Operator(Output output) {
      super(output);
   }
   
   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      characters.removeSpace();
      switch(characters.charAt(0)){
      case '=':
         if(characters.charAt(1) == '='){
            characters.shift(2);
            String value = "==";
            if(characters.charAt(0) == '='){
               characters.shift(1);
               value = value + "=";
            }
            output.prepend(value);
            context.removeProduction();
            return;
         }
         break;
      case '!':
         if(characters.charAt(1) == '='){
            characters.shift(2);
            String value = "!=";
            if(characters.charAt(0) == '='){
               characters.shift(1);
               value = value + "=";
            }
            output.prepend(value);
            context.removeProduction();
            return;
         }
         break;
      case '|':
         if(characters.charAt(1) == '|'){
            characters.shift(2);
            output.prepend("||");
            context.removeProduction();
            return;
         }
         break;
      case '&':
         if(characters.charAt(1) == '&'){
            characters.shift(2);
            output.prepend("&&");
            context.removeProduction();
            return;
         }
         break;
      case '<':
      case '>':
         if(characters.charAt(1) == '='){
            output.prepend(characters.charAt(0)).prepend(characters.charAt(1));
            characters.shift(2);
            context.removeProduction();
            return;
         }
      case '+':
      case '-':
      case '%':
      case '*':
      case '/':
         output.prepend(characters.charAt(0));
         characters.shift(1);
         context.removeProduction();
         return;
      }
      throw new Exception("Invalid Operator.");
   }
}
