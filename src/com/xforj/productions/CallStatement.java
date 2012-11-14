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
import java.util.regex.*;

/**
 *
 * @author Joseph Spencer
 */
public class CallStatement extends AbstractConditionBlock {
   final Output namespaceOutput;
   final Output contextOutput;
   final Output paramOutput;
   public CallStatement(Output output){
      super(output, true);
      namespaceOutput=new Output();
      contextOutput=new Output();
      paramOutput=new Output();
      output.add(js_bld).add("(").add(namespaceOutput).add("(").add(contextOutput).add(paramOutput).add(")").add(");");
   }

   @Override
   protected Production getVariableExpression(Output output) {
      return new CallExpression(namespaceOutput, contextOutput);
   }

   @Override
   protected Production getBodyStatements(Output output) {
      return new CallParams(paramOutput);
   }

   @Override
   protected Pattern getClosingPattern() {
      return CALL_CLOSING;
   }

}
