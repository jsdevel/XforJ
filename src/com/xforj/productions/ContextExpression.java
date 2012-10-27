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

/**
 *
 * @author Joseph Spencer
 */
public class ContextExpression extends AbstractExpression {
   private boolean isNested;

   public ContextExpression(Output output, boolean isNested) {
      super(output);
      this.isNested = isNested;
   }

   @Override
   protected Production getValue() {
      return new VariableValue(output, isNested);
   }

   @Override
   protected Production getParenthesizedExpression(Output output) {
      return new ContextExpressionParenthesized(output);
   }
}
