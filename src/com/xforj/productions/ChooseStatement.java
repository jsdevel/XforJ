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

import com.xforj.*;
import java.util.regex.Matcher;

/**
 *
 * @author Joseph Spencer
 */
public class ChooseStatement extends Production {
   public ChooseStatement(Output output) {
      super(output);
   }

   private boolean hasOtherwise;
   private boolean hasWhen;

   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      Matcher match;
      Output statementOutput;

      characters.removeSpace();
      if(characters.charAt(0) == '{'){
         switch(characters.charAt(1)){
         case 'w':
            if(!hasOtherwise){
               hasWhen=true;
               statementOutput=new Output();
               context.addProduction(new WhenStatements(statementOutput));
               output.add(statementOutput);
               return;
            }
            break;
         case 'o':
            hasOtherwise=true;
            statementOutput=new Output();
            context.addProduction(new OtherwiseStatement(statementOutput, hasWhen));
            output.add(statementOutput);
            return;
         case '/':
            if(hasOtherwise){
               match=characters.match(CHOOSE_CLOSING);
               if(match.find()){
                  characters.shift(match.group(1).length());
                  context.removeProduction();
                  return;
               }
            }
            break;
         }
      }
      throw new UnsupportedOperationException("Invalid ChooseStatement.");
   }

}
