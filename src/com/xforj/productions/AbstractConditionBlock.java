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
import java.util.regex.*;

/**
 *
 * @author Joseph Spencer
 */
public abstract class AbstractConditionBlock extends Production {
   Output expressionOutput;
   Output bodyOutput;
   boolean canSelfClose;

   public AbstractConditionBlock(Output output) {
      super(output);
      expressionOutput=new Output();
      bodyOutput=new Output();
   }

   public AbstractConditionBlock(Output output, boolean canSelfClose){
      this(output);
      this.canSelfClose=canSelfClose;
   }

   private boolean expectingVariableExpression=true;
   private boolean expectingBodyStatements=true;

   @Override
   final void execute(CharWrapper characters, ProductionContext context) throws Exception {
      if(expectingVariableExpression){
         context.addProduction(getVariableExpression(expressionOutput));
         expectingVariableExpression=false;
         return;
      }

      characters.removeSpace();

      switch(characters.charAt(0)){
      case '/':
         if(expectingBodyStatements && canSelfClose && characters.charAt(1) == '}'){
            characters.shift(2);
            context.removeProduction();
            return;
         } else {
            exc("Unexpected '/'");
         }
         break; 
      case '}':
         if(expectingBodyStatements){
            characters.shift(1);
            expectingBodyStatements=false;
            context.addProduction(getBodyStatements(bodyOutput));
            return;
         }
         break;
      case '{':
         if(!expectingBodyStatements){
            if(characters.charAt(1) == '/'){
               Matcher match = characters.match(getClosingPattern());
               if(match.find()){
                  characters.shift(match.group(1).length());
                  context.removeProduction();
                  return;
               }
            }
         }
      }
      throw new Exception("Invalid Expression found in "+getClassName());
   }

   protected abstract Production getVariableExpression(Output output);
   protected abstract Production getBodyStatements(Output output);
   protected abstract Pattern getClosingPattern();
}
