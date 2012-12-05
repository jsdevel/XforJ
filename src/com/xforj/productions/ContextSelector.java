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
import java.util.regex.Pattern;

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
         output.
            add(js_SafeValue+"(function(){return ").
               add(contextSelectorOutput).
            add("})");
      } else {
         contextSelectorOutput=output;
      }
   }

   private static final Pattern dotWithFurtherRefinement = Pattern.compile("^\\.[a-zA-Z$_].*+");
   private boolean hasContextSelector;
   private boolean contextHasBeenPrependedToOutput;

   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      Matcher match;

      characters.removeSpace();

      if(!contextHasBeenPrependedToOutput && characters.charAt(0) == '.'){
         addContextToOutput();

         //because . is also used in refinement, we need to do a little extra
         //work the first time to see if we should allow the rest of this method
         //to add to the output.

         if(characters.charAt(1) == '['){
            characters.shift(1);            
            return;
         } else {
            Matcher furtherRefinement = characters.match(dotWithFurtherRefinement);
            if(furtherRefinement.find()){
               hasContextSelector=true;
               return;
            } else {
               characters.shift(1);
               context.removeProduction();
               return;
            }
         }
      }

      switch(characters.charAt(0)){
      case '.':
         if(!hasContextSelector){
            throw new Exception("Invalid ContextSeletor.  Unexpected \".\".");
         }
         hasContextSelector=false;
         characters.shift(1);
         if(parseNamespace(characters, context)){
            return;
         }
         break;
      case '[':
         if(!hasContextSelector && !contextHasBeenPrependedToOutput){
            addContextToOutput();
         }
         hasContextSelector=true;
         context.addProduction(new ContextDynamicRefinement(contextSelectorOutput));
         return;
      case ']':
         //ContextDynamicRefinement should handle this.
         break;
      case 'c':
         match = characters.match(CURRENT_FN);
         if(match.find()){
            hasContextSelector=true;
            characters.shift(match.group(1).length());
            addContextToOutput();
            return;
         }
         //c could be a name start so we let it flow through.
      default:
         if(parseNamespace(characters, context)){
            return;
         }
      }
      if(hasContextSelector){
         context.removeProduction();
      } else {
         throw new Exception("Invalid ContextSelector.  Empty statement");
      }
   }

   private void addContextToOutput() throws Exception {
      if(contextHasBeenPrependedToOutput){
         throw new Exception("Invlid ContextSelector.  Attempted to add the current context more than once.");
      }
      contextHasBeenPrependedToOutput=true;
      contextSelectorOutput.add(js_context);
   }

   private boolean parseNamespace(CharWrapper characters, ProductionContext context) throws Exception {
      boolean contextIsNotVariableReference = true;
      if(!hasContextSelector){
         if(characters.charAt(0) == '@'){
            if(contextHasBeenPrependedToOutput){
               throw new Exception("Invalid ContextSelector.  Unexpected VariableReference.");
            }
            characters.shift(1);
            contextIsNotVariableReference = false;
         }
         Matcher match = characters.match(CONTEXT_STATIC_REFINEMENT_NAMESPACE);
         if(match.find()){
            hasContextSelector=true;
            String namesp = match.group(1);
            characters.shift(namesp.length());

            namesp = namesp.replaceAll("^\\s+|\\s+$", "");

            context.validateNamespacesAgainstReservedWords(namesp);

            //Make sure it doesn't start with a reserved word.
            Matcher potentialReservedWord = RESERVED_WORDS.matcher(namesp);
            if(potentialReservedWord.find()){
               throw new Exception("Unexpected Reserved Word: "+potentialReservedWord.group(1));
            }

            //we need to add the context variable to the beginning the first time
            if(!contextHasBeenPrependedToOutput && contextIsNotVariableReference){
               addContextToOutput();
            }
            if(contextIsNotVariableReference){
               contextSelectorOutput.add(".");
            } else {
               contextSelectorOutput.add(context.getCurrentVariableOutput().variablePrefix);
            }
            contextSelectorOutput.add(namesp.replaceAll("\\s*+", ""));
            return true;
         }
      }
      return false;
   }
}
