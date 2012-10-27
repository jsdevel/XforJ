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

/**
 *
 * @author Joseph Spencer
 */
public abstract class AbstractParenthesizedExpression extends Production {
   Output expressionOutput = new Output();
   public AbstractParenthesizedExpression(Output output) {
      super(output);
      output.
         prepend("(").
         prepend(expressionOutput).
         prepend(")");
   }

   private boolean hasExpression;

   @Override
   public final void execute(CharWrapper characters, ProductionContext context) throws Exception {
      characters.removeSpace();
      char firstChar = characters.charAt(0);

      switch(firstChar){
      case cparen:
         if(!hasExpression){
            exc("  Empty Expressions are not allowed.");
         }
         characters.shift(1);
         context.removeProduction();
         return;
      default:
         if(!hasExpression){
            hasExpression=true;
            context.addProduction(getExpression(expressionOutput));
            return;
         }
      }
      exc("  Possibly an unclosed paren was found.");
   }

   protected abstract Production getExpression(Output output) throws Exception;
}
