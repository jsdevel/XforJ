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
public class LogStatement extends Production {
   Output expressionOutput = new Output();
   public LogStatement(Output output, ProductionContext context) {
      super(output);
      if(!context.removeLogs){
         output.add("console.log(").
            add(expressionOutput).
         add(");");
      } else {
         context.removeProduction();
      }
   }
   private boolean hasExpression;
   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      characters.removeSpace();
      if(!hasExpression){
         hasExpression = true;
         context.addProduction(new VariableExpression(expressionOutput));
         return;
      } else if(characters.charAt(0) == '}'){
         characters.shift(1);
         context.removeProduction();
         return;
      }
      throw new Exception("Invalid LogStatement.");
   }
}
