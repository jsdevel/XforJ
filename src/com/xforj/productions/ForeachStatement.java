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
public class ForeachStatement extends AbstractConditionBlock {
   Output sortParamOutput=new Output();
   Output sortContextOutput=new Output();
   Output sortCaseSensitivityOutput=new Output();
   public ForeachStatement(Output output, ProductionContext context) {
      super(output);
      output.
         prepend(js_foreach+"(").
               prepend(js_GetSortArray+"(").
                  prepend(expressionOutput).
                  prepend(sortContextOutput).
                  prepend(sortCaseSensitivityOutput).
                  prepend(")").
            prepend(",").
               prepend(//callback
                  "function("+
                     js_context+","+
                     js_position+","+
                     js_last+","+
                     js_name+
                  "){"
               ).
               prepend(bodyOutput).
            prepend("}").//sortFunction if any
               prepend(sortParamOutput).
            prepend(");");
      context.getParams().
      put(js_foreach, //Foreach
         context.jsCode.getJSForeach()
      ).
      put(js_GetSortArray,
         context.jsCode.getJSSortArray()    
      );      
   }

   @Override
   protected Production getVariableExpression(Output output) {
      return new ContextSelector(output, false);
   }

   @Override
   protected Production getBodyStatements(Output output) {
      return new ForeachBody(output, sortContextOutput, sortParamOutput, sortCaseSensitivityOutput);
   }

   @Override
   protected Pattern getClosingPattern() {
      return FOREACH_CLOSING;
   }

}
