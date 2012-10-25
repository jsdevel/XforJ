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
public class VariableValue extends GlobalVariableValue {
   public VariableValue(Output output) {
      super(output);
   }

   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      Matcher match;
      characters.removeSpace();
      switch(characters.charAt(0)){
      case at:
      case squote:
      case quote:
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
         super.execute(characters, context);
         break;
      case p:
         match=characters.match(POSITION);
         if(match.find()){
            characters.shift(match.group(1).length());
            output.prepend(js_position);
            context.removeProduction();
         } else {
            super.execute(characters, context);
         }
         break;
      case c:
         //needs work
         match=characters.match(COUNT);
         if(match.find()){
            characters.shift(match.group(1).length());
            output.prepend(js_count);
            context.removeProduction();
         } else {
            super.execute(characters, context);
         }
         break;
      case l:
         match=characters.match(LAST);
         if(match.find()){
            characters.shift(match.group(1).length());
            output.prepend(js_last);
            context.removeProduction();
         } else {
            super.execute(characters, context);
         }
         break;
      case n:
         match = characters.match(NULL);
         if(match.find()){
            super.execute(characters, context);
            break;
         }
      default:
         context.removeProduction();
         context.addProduction(new ContextSelector(output));
      }
   }
}
