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

package jsl.strategies;

import java.util.regex.Matcher;
import jsl.*;

/**
 *
 * @author Joseph Spencer
 */
public class GlobalVariableDeclaration extends Production {
   public GlobalVariableDeclaration(Output output) {
      super(output);
   }

   private boolean hasOpen;

   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      characters.removeSpace();
      if(!hasOpen && characters.charAt(0) == open){
         hasOpen=true;
         Matcher variable = characters.match(VARIABLE);
         if(variable.find()){
            characters.shift(variable.group(1).length());

            if(characters.removeSpace()){
               Matcher name = characters.match(NAME);
               if(name.find()){

                  String newVar = name.group(1);
                  characters.shift(newVar.length());
                  Output expressionOutput = new Output();
                  context.getCurrentVariableOutput().add(newVar, expressionOutput);
                  if(characters.removeSpace()){
                     context.addProduction(new GlobalVariableAssignment(expressionOutput));
                  }
                  return;
               }
            }
         }
         context.removeProduction();
      } else if(hasOpen && characters.charAt(0) == close){
         characters.shift(1);
         context.removeProduction();
         return;
      }
      throw new Exception("Invalid character found while evaluating GlobalVariableDeclaration.");
   }

}
