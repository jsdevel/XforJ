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
import java.util.regex.Matcher;

/**
 *
 * @author Joseph Spencer
 */
public class GlobalVariableValue extends Production {
   public GlobalVariableValue(Output output) {
      super(output);
   }
   
   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      characters.removeSpace();
      switch(characters.charAt(0)){
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
         Matcher integer = characters.match(INTEGER);
         if(integer.find()){
            String intStr = integer.group(1);
            characters.shift(intStr.length());
            output.add(intStr);
            context.removeProduction();
            return;
         } else {
            Matcher decimal = characters.match(DECIMAL);
            if(decimal.find()){
               String decStr = decimal.group(1);
               characters.shift(decStr.length());
               output.add(decStr);
               context.removeProduction();
               return;
            }
         }
         break;
      case '@':
         characters.shift(1);
         Matcher name = characters.match(NAME);
         if(name.find()){
            String value = name.group(1);
            context.validateVariableReference(value);
            characters.shift(value.length());
            output.add("__"+value);
            context.removeProduction();
            return;
         }
         break;
      case '\'':
      case '"':
         Matcher string = characters.match(STRING);
         if(string.find()){
            String value = string.group(1);
            characters.shift(value.length());
            output.add(value);
            context.removeProduction();
            return;
         }
         break;
      case 'n':
         Matcher nullMatch = characters.match(NULL);
         if(nullMatch.find()){
            String value = nullMatch.group(1);
            characters.shift(value.length());
            output.add(value);
            context.removeProduction();
            return;
         }
         break;
      case 't':
      case 'f':
         Matcher booleanMatch = characters.match(BOOLEAN);
         if(booleanMatch.find()){
            String value = booleanMatch.group(1);
            characters.shift(value.length());
            output.add(value);
            context.removeProduction();
            return;
         }
      }
      throw new Exception("Invalid value found while evaluating GlobalVariableValue.");
   }

}
