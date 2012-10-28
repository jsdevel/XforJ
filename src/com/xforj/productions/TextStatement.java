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
public class TextStatement extends Production {
   public TextStatement(Output output) {
      super(output);
   }

   private boolean hasInputTokens;
   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      Matcher match;
      Output inputTokenOutput;
      switch(characters.charAt(0)){
      case '{':
         if(!hasInputTokens){
            break;
         }
         match = characters.match(TEXT_CLOSING);
         if(match.find()){
            characters.shift(match.group(1).length());
            context.removeProduction();
            return;
         }
         break;
      default:
         hasInputTokens=true;
         inputTokenOutput=new Output();
         context.addProduction(new InputTokens(inputTokenOutput));
         output.prepend(inputTokenOutput);
         return;
      }
      throw new Exception("Invalid TextStatement.");
   }
}
