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
public class TemplateDeclaration extends Production {
   public TemplateDeclaration(Output output) {
      super(output);
   }

   private boolean isOpened;
   private boolean expectingTemplateBody;
   private boolean allowVariableDeclarations;

   @Override
   public void execute(CharWrapper characters, ProductionContext context) throws Exception {
      characters.removeSpace();

      if(characters.charAt(0) == open){
         if(!isOpened){
            isOpened=true;
            characters.shift(1);
            context.addVaribleOutput();

            Matcher template = characters.match(TEMPLATE);
            if(template.find()){
               characters.shift(template.group(1).length());

               if(characters.removeSpace()){
                  Matcher name = characters.match(NAME);
                  if(name.find()){
                     String nm = name.group(1);

                     characters.shift(nm.length());
                     output.
                        prepend(
                           js_currentNS+"."+nm+"=$"+nm+";"+
                           "function $"+nm+"("+js__data+", "+js__params+"){"+
                              "var "+js_data+"="+js__data+"||{},"+
                                 js_params+"="+js__params+"||{},"+
                                 js_bld+"=new "+js_StringBuffer+"(),"+
                                 js_last+"=''/0,"+
                                 js_position+"="+js_last+";"
                        ).
                        prepend(context.getCurrentVariableOutput()).
                        append("return "+js_bld+".toString()}");

                     if(characters.charAt(0) == close){
                        characters.shift(1);
                        context.addProduction(new ParamDeclarations(context.getCurrentVariableOutput()));                           

                        allowVariableDeclarations=true;
                        expectingTemplateBody=true;
                        return;
                     }
                  }
               }
            }
         } else if(allowVariableDeclarations && characters.charAt(1) == v){
            context.addProduction(new VariableDeclarations(context.getCurrentVariableOutput()));
            allowVariableDeclarations=false;
            return;
         } else if(characters.charAt(1) == forward && characters.charAt(2) == t){
            characters.shift(2);
            Matcher template = characters.match(TEMPLATE);
            if(template.find()){
               characters.shift(template.group(1).length());
               if(characters.charAt(0) == close){
                  characters.shift(1);
                  context.removeProduction();
                  context.removeVariableOutput();
                  return;
               }
            }
         } 
      } else if(expectingTemplateBody){
         expectingTemplateBody=false;
         Output templateBodyOutput = new Output();
         output.prepend(templateBodyOutput);
         context.addProduction(new TemplateBody(templateBodyOutput));
         return;
      }

      throw new Exception("Invalid Character found while evaluating TemplateDeclaration.");
   }
}
