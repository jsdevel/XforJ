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
import com.xforj.LOGGER;

/**
 *
 * @author Joseph Spencer
 */
public class GlobalStatements extends Production {
   public GlobalStatements(Output output) {
      super(output);
   }

   private boolean hasStatements;

   @Override
   public void execute(CharWrapper characters, ProductionContext context) throws Exception {
      if(characters.charAt(0) == '{'){
         hasStatements=true;
         Output templateDeclarationOutput = new Output();
         output.add(templateDeclarationOutput);
         context.addProduction(new TemplateDeclaration(templateDeclarationOutput)); 
      } else if(!characters.removeSpace()){
         throw new Exception("Invalid character found while evaluating GlobalStatements.");
      }
   }

   @Override
   public void close(ProductionContext context) throws Exception {
      if(!hasStatements){
         context.handleFileWarning("No Statements found in: ");
      }
   }
}
