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
         output.prepend("(function("+js_context+"){try{return "). 
         prepend(contextSelectorOutput).
         prepend("}catch(e){}})("+js_context+")");
      } else {
         contextSelectorOutput=output;
      }
   }

   private boolean hasContextSelector;
   private boolean contextHasBeenPrependedToOutput;
   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      Matcher match;

      characters.removeSpace();

      switch(characters.charAt(0)){
      case '.':
         if(!hasContextSelector){
            throw new Exception("Invalid ContextSeletor.  Unexpected \".\".");
         }
         hasContextSelector=false;
         characters.shift(1);
         if(parseNamespace(characters)){
            return;
         }
         break;
      case '[':
         if(!hasContextSelector){
            contextSelectorOutput.prepend(js_context);
         }
         hasContextSelector=true;
         context.addProduction(new ContextDynamicRefinement(contextSelectorOutput));
         return;
      case ']':
         //ContextDynamicRefinement should handle this.
         break;
      case 'c':
         match = characters.match(CURRENT);
         if(match.find()){
            hasContextSelector=true;
            contextHasBeenPrependedToOutput=true;
            characters.shift(match.group(1).length());
            contextSelectorOutput.prepend(js_context);
            return;
         }
         //c could be a name start so we let it flow through.
      default:
         if(parseNamespace(characters)){
            return;
         }
      }
      if(hasContextSelector){
         context.removeProduction();
      } else {
         throw new Exception("Invalid ContextSelector.  Empty statement");
      }
   }

   private boolean parseNamespace(CharWrapper characters) throws Exception {
      if(!hasContextSelector){
         Matcher match = characters.match(CONTEXT_STATIC_REFINEMENT_NAMESPACE);
         if(match.find()){
            hasContextSelector=true;
            String namesp = match.group(1);
            characters.shift(namesp.length());
            //name
            if(!contextHasBeenPrependedToOutput){
               contextHasBeenPrependedToOutput=true;
               contextSelectorOutput.prepend(js_context);
            }
            contextSelectorOutput.prepend(".");
            contextSelectorOutput.prepend(namesp.replaceAll("\\s*+", ""));
            return true;
         }
      }
      return false;
   }
}