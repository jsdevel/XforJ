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
public class GlobalExpression extends Production {
   public GlobalExpression(Output output) {
      super(output);
   }

   private boolean hasOperator=false;
   private boolean hasValue=false;
   
   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      characters.removeSpace();
      if(characters.charAt(0) != close){
         if(hasValue == false || hasOperator){//Go to Value
            hasOperator=false;
            hasValue=true;
            context.addProduction(new GlobalVariableValue(output));
            return;
         } else if(hasValue){//Go to Operator
            hasOperator=true;
            hasValue=false;
            context.addProduction(new Operator(output));
            return;
         }
      }
      if(hasValue && !hasOperator){
         context.removeProduction();
         return;
      }

      throw new Exception("Invalid GlobalExpression."+(hasOperator?"  Unclosed operator.":""));
   }

}
