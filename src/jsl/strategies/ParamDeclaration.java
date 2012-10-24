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
public class ParamDeclaration extends Production {
   VariableOutput variableOutput;
   public ParamDeclaration(VariableOutput variableOutput) {
      super(variableOutput);
      this.variableOutput=variableOutput;
   }

   private boolean hasValue;

   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      characters.removeSpace();
      Matcher param = characters.match(PARAM);
      if(!hasValue && param.find()){
         characters.shift(param.group(1).length());
         characters.removeSpace();
         Matcher nameMatch = characters.match(NAME);
         if(nameMatch.find()){
            String name = nameMatch.group(1);
            characters.shift(name.length());
            if(characters.removeSpace()){
               hasValue=true;
               Output paramAssignmentOutput = new Output();
               paramAssignmentOutput.prepend("_prams."+name+"||");
               variableOutput.add(name, paramAssignmentOutput);
               context.addProduction(new ParamAssignment(paramAssignmentOutput));
            } else {
               variableOutput.add(name, "_params."+name);
            }
            return;
         }
      } else if(characters.charAt(0) == close){
         characters.shift(1);
         context.removeProduction();
         return;
      }
      throw new Exception("Invalid ParamDeclaration.");
   }

}
