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

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import java.util.regex.Matcher;
import jsl.*;

/**
 *
 * @author Joseph Spencer
 */
public class TemplateDeclaration implements Strategy, Characters {
   Output output;

   public TemplateDeclaration(Output output) {
      this.output = output;
   }

   private boolean isOpened;
   private boolean expectingTemplateBody;

   @Override
   public void execute(CharWrapper characters, StrategyContext context) throws Exception {
      characters.removeSpace();

      if(expectingTemplateBody){
         expectingTemplateBody=false;
         Output templateBodyOutput = new Output();
         output.prepend("return \"").prepend(templateBodyOutput).prepend("\"}");
         context.addStrategy(new TemplateBody(templateBodyOutput));
         return;
      }
      if(characters.charAt(0) == open){
         if(!isOpened){
            isOpened=true;
            characters.shift(1);

            Matcher template = characters.match(TEMPLATE);
            if(template.find()){
               characters.shift(template.group(1).length());

               if(characters.removeSpace()){
                  Matcher name = characters.match(NAME);
                  if(name.find()){
                     String nm = name.group(1);
                     VariableOutput paramDeclarationsOutput = new VariableOutput();

                     characters.shift(nm.length());
                     output.
                        prepend(context.getNS()+"."+nm+"="+nm+";function "+nm+"(_data, _params){var data=_data||{},params=_params||{};").
                        prepend(paramDeclarationsOutput);

                     if(characters.charAt(0) == close){
                        characters.shift(1);
                        context.addStrategy(new ParamDeclarations(paramDeclarationsOutput));                           
                        expectingTemplateBody=true;
                        return;
                     }
                  }
               }
            }
         } else if(characters.charAt(1) == forward && characters.charAt(2) == t){
               characters.shift(2);
               Matcher template = characters.match(TEMPLATE);
               if(template.find()){
                  characters.shift(template.group(1).length());
                  if(characters.charAt(0) == close){
                     characters.shift(1);
                     context.removeStrategy();
                     return;
                  }
               }
            } 
      }
      throw new Exception("Invalid Character found while evaluating TemplateDeclaration.");
   }
}
