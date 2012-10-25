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

import jsl.*;

/**
 *
 * @author Joseph Spencer
 */
public class ContextDynamicRefinement extends Production {
   public ContextDynamicRefinement(Output output) {
      super(output);
   }

   boolean hasOpenBracket;

   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      Output contextExpressionOutput;

      characters.removeSpace();

      switch(characters.charAt(0)){
      case obracket:
         if(!hasOpenBracket){
            hasOpenBracket=true;
            characters.shift(1);
            contextExpressionOutput = new Output();
            output.
               prepend("[").
               prepend(contextExpressionOutput);
            context.addProduction(new ContextExpression(contextExpressionOutput, true));
            return;
         }
         break;
      case cbracket:
         if(hasOpenBracket){
            hasOpenBracket=false;
            characters.shift(1);
            output.
               prepend("]");
            context.removeProduction();
            return;
         }
      }
      throw new Exception("Invalid ContextDynamicRefinement.");
   }

}
