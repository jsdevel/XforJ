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
public class IfStatement extends Production {
   Output variableExpressionOutput;
   Output templateBodyStatementsOutput;
   public IfStatement(Output output) {
      super(output);
      variableExpressionOutput=new Output();
      templateBodyStatementsOutput=new Output();
      output.
         prepend("if(").
         prepend(variableExpressionOutput).
         prepend("){").
         prepend(templateBodyStatementsOutput).
         prepend("}");
   }

   private boolean expectingVariableExpression=true;
   private boolean expectingTemplateBodyStatements=true;
   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      if(expectingVariableExpression){
         context.addProduction(new VariableExpression(variableExpressionOutput));
         expectingVariableExpression=false;
         return;
      }

      characters.removeSpace();

      switch(characters.charAt(0)){
      case ccurly:
         if(expectingTemplateBodyStatements){
            characters.shift(1);
            expectingTemplateBodyStatements=false;
            context.addProduction(new TemplateBodyStatements(templateBodyStatementsOutput));
            return;
         }
         break;
      case ocurly:
         if(!expectingTemplateBodyStatements){
            if(characters.charAt(1) == forward){
               Matcher match = characters.match(IF_CLOSING);
               if(match.find()){
                  characters.shift(match.group(1).length());
                  context.removeProduction();
                  return;
               }
            }
         }
      }

      throw new Exception("Invalid IfStatement.");
   }

}
