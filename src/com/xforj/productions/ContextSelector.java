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
public class ContextSelector extends Production {
   private Output contextSelectorOutput;
   public ContextSelector(Output output, boolean nested) {
      super(output);
      if(!nested){
         contextSelectorOutput=new Output();
         output.prepend("((function("+js_context+"){try{return "+js_context+"."). 
         prepend(contextSelectorOutput).
         append("}catch(e){}})("+js_context+")||\"\")");
      } else {
         contextSelectorOutput=output;
      }
   }

   private boolean hasContextSelector;
   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      Matcher match;

      characters.removeSpace();

      switch(characters.charAt(0)){
      case dot:
         if(!hasContextSelector){
            throw new Exception("Invalid ContextSeletor.  Unexpected \".\".");
         }
         hasContextSelector=false;
         characters.shift(1);
         contextSelectorOutput.prepend(".");
         match = characters.match(CONTEXT_STATIC_REFINEMENT_NAMESPACE);
         if(match.find()){
            hasContextSelector=true;
            String staticRefinement = match.group(1);
            contextSelectorOutput.prepend(staticRefinement.replaceAll("\\s*+", ""));
            characters.shift(staticRefinement.length());
            return;
         }
         break;
      case obracket:
         hasContextSelector=true;
         context.addProduction(new ContextDynamicRefinement(contextSelectorOutput));
         return;
      case cbracket:
         break;
      default:
         match = characters.match(CONTEXT_STATIC_REFINEMENT_NAMESPACE);
         if(match.find()){
            hasContextSelector=true;
            String namesp = match.group(1);
            characters.shift(namesp.length());
            contextSelectorOutput.prepend(namesp.replaceAll("\\s*+", ""));
            return;
         }
      }
      if(hasContextSelector){
         context.removeProduction();
      } else {
         throw new Exception("Invalid ContextSelector.  Empty statement");
      }
   }
}
