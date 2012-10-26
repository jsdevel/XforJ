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
public class OtherwiseStatement extends Production {
   Output templateBodyOutput;
   public OtherwiseStatement(Output output, boolean followsWhen) {
      super(output);
      if(followsWhen){
         templateBodyOutput=new Output();
         output.
            prepend("else{").
            prepend(templateBodyOutput).
            prepend("}");
      } else {
         templateBodyOutput=output;
      }
   }

   private boolean isOpen;

   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      Matcher match;

      if(characters.charAt(0) == open){
         if(isOpen){
            match = characters.match(OTHERWISE_CLOSING);
            if(match.find()){
               characters.shift(match.group(1).length());
               context.removeProduction();
               return;
            }
         } else {
            match = characters.match(OTHERWISE);
            if(match.find()){
               isOpen=true;
               characters.shift(match.group(1).length());
               context.addProduction(new TemplateBodyStatements(templateBodyOutput));
               return;
            }
         }
      }

      throw new Exception("Invalid OtherwiseStatement.");
   }

}
