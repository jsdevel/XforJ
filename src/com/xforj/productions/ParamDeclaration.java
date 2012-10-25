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
import com.xforj.VariableOutput;
import java.util.regex.*;

/**
 *
 * @author Joseph Spencer
 */
public class ParamDeclaration extends AbstractVariableDeclaration {
   VariableOutput variableOutput;
   public ParamDeclaration(VariableOutput variableOutput) {
      super(variableOutput);
      this.variableOutput=variableOutput;
   }

   @Override
   protected Pattern getPattern() {
      return PARAM;
   }

   @Override
   protected Production getProduction(Output output) {
      return new VariableAssignment(output);
   }

   @Override
   protected void doAssignment(String name, Output output) {
      output.prepend("params."+name+"||");
   }

   @Override
   protected void doNoAssignment(String name, ProductionContext context) throws Exception {
      context.getCurrentVariableOutput().add(name, "params."+name);
   }

   @Override
   protected String getErrorMsg() {
      return "Invalid ParamDeclaration.";
   }

   

}
