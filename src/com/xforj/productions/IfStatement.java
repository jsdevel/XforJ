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
public class IfStatement extends AbstractConditionBlock {
   public IfStatement(Output output) {
      super(output);
      output.
         add("if(").
         add(expressionOutput).
         add("){").
         add(bodyOutput).
         add("}");
   }

   @Override
   protected Production getVariableExpression(Output output) {
      return new VariableExpression(output);

   }

   @Override
   protected Production getBodyStatements(Output output) {
      return new TemplateBodyStatements(output);
   }

   @Override
   protected Pattern getClosingPattern() {
      return IF_CLOSING;
   }


}
