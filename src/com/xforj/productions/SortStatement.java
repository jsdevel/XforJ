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
public class SortStatement extends Production {
   final private Output sortCaseSensitivityOutput;
   final private Output sortContextOutput;
   final private Output sortParamOutput;

   public SortStatement(
      Output sortContextOutput, 
      Output sortFunctionOutput, 
      Output sortCaseSensitivityOutput,
      ProductionContext context
   ){
      super(sortContextOutput);
      this.sortCaseSensitivityOutput=sortCaseSensitivityOutput;
      this.sortContextOutput=sortContextOutput;
      this.sortParamOutput=sortFunctionOutput;

      context.getParams().
      put(js_GetSortArray,
         context.jsCode.getJSSortArray()      
      );
   }

   private boolean hasContextSelector;
   private boolean hasSortFunction;
   private boolean hasSortDirection;
   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {

      characters.removeSpace();

      if(!hasContextSelector){
         hasContextSelector=true;
         Output contextSelectorOutput = new Output();
         sortContextOutput.
            add(",function("+js_context+"){return ").
               add(contextSelectorOutput).
            add("}");
         context.addProduction(new ContextSelector(contextSelectorOutput, true));
         return;
      } else {
         if(characters.charAt(0) == '}'){
            if(!hasSortFunction && !hasSortDirection){
               sortParamOutput.
                  add(",1,0");
            }
            characters.shift(1);
            context.removeProduction();
            return;
         }
         if(!hasSortDirection && !hasSortFunction){
            Matcher sortDirection = characters.match(SORT_DIRECTION);
            if(sortDirection.find()){
               hasSortDirection=true;
               hasSortFunction=true;

               String direction = sortDirection.group(1);
               characters.shift(direction.length());

               boolean asc = direction.startsWith("a");
               boolean promoteNum = false;

               Matcher sortModifiers = characters.match(SORT_MODIFIERS);
               if(sortModifiers.find()){
                  String modifiers = sortModifiers.group(1);
                  characters.shift(1);//pipe
                  characters.shift(modifiers.length());//pipe

                  if(modifiers.contains("i")){
                     sortCaseSensitivityOutput.add(",1");//added to the params for GetSortArray
                  }
                  promoteNum=modifiers.contains("n");
               }
               sortParamOutput.add(","+(asc?1:0)+","+(promoteNum?1:0));
               return;
            }
         }
      }
      exc();
   }
}
