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

import java.util.regex.*;
import jsl.*;

/**
 *
 * @author Joseph Spencer
 */
public class TemplateBodyStatements extends Production {
   public TemplateBodyStatements(Output output) {
      super(output);
   }
   


   @Override
   public void execute(CharWrapper characters, ProductionContext context) throws Exception {
      Matcher match;
      Output statementOutput;

      if(characters.charAt(0) == open){
         switch(characters.charAt(1)){
         case forward:
            context.removeProduction();
            return;
         case i:
            match = characters.match(IF);
            if(match.find()){
               characters.shift(match.group(1).length());
               statementOutput=new Output();
               context.addProduction(new IfStatement(statementOutput));
               output.prepend(statementOutput);
               return;
            }
         }

         //PrintStatement
         statementOutput=new Output();
         output.prepend(statementOutput);
         context.addProduction(new PrintStatement(statementOutput));
         return;
      } else {
         Matcher inputTokens = characters.match(INPUT_TOKENS);
         if(inputTokens.find()){
            String oldTokens = inputTokens.group(1);
            String newTokens;
            if(context.stripNewLines){
               newTokens = oldTokens.replaceAll("\\n|\\r", "");
            } else {
               newTokens = oldTokens.replaceAll("\\n|\\r", "\\\\\n");

            }
            if(context.minifyHTML){
               newTokens = newTokens.replaceAll("(>|<)\\s++|\\s++(>|<)", "$1$2");
            }
            newTokens = newTokens.replaceAll("\"", "\\\\\"").replaceAll("'", "\\\\'");
            characters.shift(oldTokens.length());
            output.prepend(js_bld+".append('"+newTokens+"');");
            return;
         }
      }
      throw new Exception("Invalid Template");
   }
}
