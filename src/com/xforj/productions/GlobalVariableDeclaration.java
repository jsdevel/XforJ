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
import java.util.regex.*;

/**
 *
 * @author Joseph Spencer
 */
public class GlobalVariableDeclaration extends AbstractVariableDeclaration {
   public GlobalVariableDeclaration(Output output) {
      super(output);
   }

   @Override
   protected Pattern getPattern() {
      return VAR;
   }

   @Override
   protected Production getProduction(Output output) {
      return new GlobalVariableAssignment(output);
   }

   @Override
   protected void doAssignment(String name, Output output) throws Exception {}

   @Override
   protected void doNoAssignment(String name, ProductionContext context) throws Exception {
      context.getCurrentVariableOutput().add(name, "");
   }

}
