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
      String chunk;
      if(characters.charAt(0) == '{'){
         characters.shift(1);
         Matcher namespace = characters.match(NAMESPACE);

         if(namespace.find()){
            characters.shift(9);

            if(characters.removeSpace()){
               Matcher declaredNS = characters.match(NS);

               if(declaredNS.find()){
                  chunk = declaredNS.group(1);
                  characters.shift(chunk.length());

                  String[] split = chunk.split("\\.");
                  String builtNS="";
                  int len = split.length;


                  for(int i=0;i<len;i++){
                     if(i==0){
                        builtNS = split[i];
                        output.prepend("var "+js_currentNS+";try{"+builtNS+"}catch(e){"+builtNS+"={}}");
                     } else {
                        builtNS+="."+split[i];
                        output.prepend("if(!"+builtNS+")"+
                           builtNS+"={};"
                        );
                     }
                  }
                  output.prepend(js_currentNS+"="+builtNS+";");

                  if(characters.charAt(0) == '}'){
                     characters.shift(1);
                     context.removeProduction();
                     return;
                  }
               }
            }

         }
      }
      throw new Exception("Invalid Namespace declaration.  Namespaces must begin with a lower case letter, and appear first.");
   }
         //throw new Exception("Namespace already declared for this template");

}
