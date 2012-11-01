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
public class ProgramNamespace extends Production {
   ProgramNamespace(Output output){
      super(output);
   }


   @Override
   public void execute(CharWrapper characters, ProductionContext context) throws Exception {
      String extraExcMsg="";
      String chunk;
      if(characters.charAt(0) == '{'){

         Matcher namespace = characters.match(NAMESPACE);
         if(namespace.find()){
            characters.shift(namespace.group(1).length());

            Matcher declaredNS = characters.match(NS);

            if(declaredNS.find()){
               chunk = declaredNS.group(1);
               characters.shift(chunk.length());

               context.setNS(chunk);

               //only build the namespace if it hasn't been declared 
               //already.
               String[] split = chunk.split("\\.");
               String nextNS="";
               String currentNS=null;

               output.prepend("var "+js_currentNS+"="+js_TemplateBasket+";");

               int len = split.length;
               for(int i=0;i<len;i++){
                  nextNS=split[i];

                  if(currentNS == null){
                     currentNS=nextNS;
                  } else {
                     currentNS+="."+nextNS;
                  }
                  context.setNS(currentNS);

                  output.prepend(
                     js_currentNS+"="+js_currentNS+"."+nextNS+"||("+
                     js_currentNS+"."+nextNS+"={});");

               }


               if(characters.charAt(0) == '}'){
                  characters.shift(1);
                  context.removeProduction();
                  return;
               } else {
                  extraExcMsg="  Invalid character found after namespace value.";
               }
            } else {
               extraExcMsg="  Namespaces must appear first.";
            }
         }
      }
      throw new Exception("Invalid Namespace declaration."+extraExcMsg);
   }
         //throw new Exception("Namespace already declared for this template");

}
