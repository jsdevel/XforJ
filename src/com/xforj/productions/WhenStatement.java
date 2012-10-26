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
public class WhenStatement extends Production {
   Output variableExpressionOutput = new Output();
   Output templateBodyOutput = new Output();
   public WhenStatement(Output output, boolean hasWhen) {
      super(output);
      if(hasWhen){
         output.prepend("else ");
      }
      output.
         prepend("if(").
         prepend(variableExpressionOutput).
         prepend("){").
         prepend(templateBodyOutput).
         prepend("}");
   }

   private boolean isOpen;
   private boolean expectingTemplateBody=true;

   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      Matcher match;

      characters.removeSpace();

      switch(characters.charAt(0)){
      case open:
         if(!isOpen){
            if(characters.charAt(1) == forward){
               break;
            }
            match = characters.match(WHEN);
            if(match.find()){
               characters.shift(match.group(1).length());
               isOpen=true;
               context.addProduction(new VariableExpression(variableExpressionOutput));
               return;
            }
         } else if(!expectingTemplateBody){
            match = characters.match(WHEN_CLOSING);
            if(match.find()){
               characters.shift(match.group(1).length());
               context.removeProduction();
               return;
            }
         }
         break;
      case close:
         if(isOpen && expectingTemplateBody){
            expectingTemplateBody=false;
            characters.shift(1);
            context.addProduction(new TemplateBodyStatements(templateBodyOutput));
            return;
         }
      }
      throw new Exception("Invalid WhenStatement.");
   }

}
