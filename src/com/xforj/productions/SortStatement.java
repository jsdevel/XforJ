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
public class SortStatement extends Production {
   final private Output sortContextOutput;
   final private Output sortFunctionOutput;

   public SortStatement(Output sortContextOutput, Output sortFunctionOutput) {
      super(sortContextOutput);
      this.sortContextOutput=sortContextOutput;
      this.sortFunctionOutput=sortFunctionOutput;
   }

   private boolean hasContextSelector;
   private boolean hasSortFunction;
   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {

      characters.removeSpace();

      if(!hasContextSelector){
         hasContextSelector=true;
         Output contextSelectorOutput = new Output();
         sortContextOutput.
            prepend(",function("+js_context+"){return ").
               prepend(contextSelectorOutput).
            prepend("}");
         context.addProduction(new ContextSelector(contextSelectorOutput, true));
         return;
      } else {
         if(characters.charAt(0) == '}'){
            if(!hasSortFunction){
               sortFunctionOutput.
                  prepend(",function(a,b){return a.k>b.k;}");
            }
            characters.shift(1);
            context.removeProduction();
            return;
         }
      }
      exc();
   }
}
