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

/**
 *
 * @author Joseph Spencer
 */
public abstract class AbstractExpression extends Production {
   public AbstractExpression(Output output) {
      super(output);
   }

   private boolean hasOperator=false;
   private boolean hasValue=false;
   private String excMsg="  Empty Expression.";
   
   @Override
   public final void execute(CharWrapper characters, ProductionContext context) throws Exception {
      characters.removeSpace();
      if(characters.charAt(0) != '}'){
         if(hasValue == false || hasOperator){//Go to Value
            hasOperator=false;
            hasValue=true;
            if(characters.charAt(0) == '('){
               characters.shift(1);
               Output parenthesizedExpressionOutput = new Output();
               output.prepend(parenthesizedExpressionOutput);
               context.addProduction(getParenthesizedExpression(parenthesizedExpressionOutput));
            } else {
               context.addProduction(getValue());
            }
            excMsg="";
            return;
         } else if(hasValue){//Go to Operator
            switch(characters.charAt(0)){
            case ']':
            case ')':
               break;
            default:
               hasOperator=true;
               hasValue=false;
               context.addProduction(new Operator(output));
               excMsg="  Unclosed Operator.";
               return;
            }
         }
      }
      if(hasValue && !hasOperator){
         context.removeProduction();
         return;
      }

      exc(excMsg);
   }

   /**
    * Used to get the Value Production.
    * 
    * @return 
    */
   abstract protected Production getValue();
   /**
    * Used when parenthesis are found.
    * 
    * @param
    * @return 
    */
   abstract protected Production getParenthesizedExpression(Output output);

}
