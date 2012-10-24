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

   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      characters.removeSpace();
      Matcher param = characters.match(PARAM);
      if(param.find()){
         characters.shift(param.group(1).length());
         characters.removeSpace();
         Matcher name = characters.match(NAME);
         if(name.find()){
            String value = name.group(1);
            characters.shift(value.length());
            if(characters.removeSpace()){
               //make this fail if it's adding additional params'
               Output paramAssignmentOutput = new Output();
               variableOutput.add(value, paramAssignmentOutput);
               context.addProduction(new ParamAssignment(paramAssignmentOutput));
               
            } else if(characters.charAt(0) == close){
               variableOutput.add(value, "_params."+value);
               context.removeProduction();
               return;
            }
         }
         throw new Exception("Invalid ParamDeclaration.");
      }
      context.removeProduction();
   }

}
