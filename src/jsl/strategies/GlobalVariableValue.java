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
   VariableOutput variableOutput;
   public GlobalVariableValue(VariableOutput variableOutput, Output output) {
      super(output);
      this.variableOutput=variableOutput;
   }
   
   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      characters.removeSpace();
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
      case at:
         characters.shift(1);
         Matcher name = characters.match(NAME);
         if(name.find()){
            String value = name.group(1);
            boolean lastVarEquals = variableOutput.lastVariableNameEquals(value);
            boolean hasVar = variableOutput.hasVariableBeenDeclared(value);
            if(lastVarEquals || !hasVar){
               throw new Exception("Error while evaluating GlobalVariableValue.  Variable \""+value+"\" hasn't been declared yet.");
            }
            characters.shift(value.length());
            output.prepend(value);
            context.removeProduction();
            return;
         }
         break;
      case squote:
      case quote:
         Matcher string = characters.match(STRING);
         if(string.find()){
            String value = string.group(1);
            characters.shift(value.length());
            output.prepend(value);
            context.removeProduction();
            return;
         }
         break;
      case n:
         Matcher nullMatch = characters.match(NULL);
         if(nullMatch.find()){
            String value = nullMatch.group(1);
            characters.shift(value.length());
            output.prepend(value);
            context.removeProduction();
            return;
         }
      }
      throw new Exception("Invalid value found while evaluating GlobalVariableValue.");
   }

}
