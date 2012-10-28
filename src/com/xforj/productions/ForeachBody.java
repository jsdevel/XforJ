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
public class ForeachBody extends Production {
   final Output sortContextOutput;
   final Output sortFunctionOutput;
   public ForeachBody(Output output, Output sortContextOutput, Output sortFunctionOutput) {
      super(output);
      this.sortContextOutput=sortContextOutput;
      this.sortFunctionOutput=sortFunctionOutput;
   }

   private boolean hasSort;
   private boolean hasVar;
   private boolean hasTemplateBody;
   private boolean hasVariableBody;

   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      Matcher match;
      characters.removeSpace();

      if(!hasVariableBody){
         hasVariableBody=true;
         context.addVaribleOutput();
      }

      switch(characters.charAt(0)){
      case '{':
         switch(characters.charAt(1)){
         case 's':
            if(!hasSort && !hasVar && !hasTemplateBody){
               hasSort=true;
               match = characters.match(SORT);
               if(match.find()){
                  characters.shift(match.group(1).length());
                  context.addProduction(new SortStatement(sortContextOutput, sortFunctionOutput));
                  return;
               }
            }
            break;
         case 'v':
            if(!hasVar && !hasTemplateBody){
               hasVar=true;
               match = characters.match(VARIABLE);
               if(match.find()){
                  output.prepend(context.getCurrentVariableOutput());
                  context.addProduction(new VariableDeclarations(context.getCurrentVariableOutput()));
                  return;
               }
            }
            break;
         case '/':
            context.removeProduction();
            context.removeVariableOutput();
            return;
         }
      default:
         hasTemplateBody=true;
         context.addProduction(new TemplateBodyStatements(output));
      }
   }

}
