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

package jsl.strategies;

import java.util.regex.Matcher;
import jsl.*;

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
      switch(characters.charAt(0)){
      case zero:
      case one:
      case two:
      case three:
      case four:
      case five:
      case six:
      case seven:
      case eight:
      case nine:
         Matcher integer = characters.match(INTEGER);
         if(integer.find()){
            String intStr = integer.group(1);
            characters.shift(intStr.length());
            output.prepend(intStr);
            context.removeProduction();
            return;
         } else {
            Matcher decimal = characters.match(DECIMAL);
            if(decimal.find()){
               String decStr = decimal.group(1);
               characters.shift(decStr.length());
               output.prepend(decStr);
               context.removeProduction();
               return;
            }
         }
         break;
      }
   }

}
