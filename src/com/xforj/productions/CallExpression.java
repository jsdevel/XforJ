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
public class CallExpression extends Production {
   private Output namespaceOutput;
   private Output contextOutput;
   public CallExpression(Output namespace, Output context) {
      super(namespace);
      namespaceOutput=namespace;
      contextOutput=context;
   }

   private boolean hasNamespace;

   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      Matcher match;
      String extraExcMsg="";
      if(!hasNamespace){
         match = characters.match(NS_FORCED);
         if(match.find()){
            hasNamespace=true;
            String ns=match.group(1);
            characters.shift(ns.length());
            namespaceOutput.add(js_TemplateBasket+"."+ns);
            context.callManager.addCalledTemplate(ns);
         } else {
            match = characters.match(NAME);
            if(match.find()){
               hasNamespace=true;
               String name=match.group(1);
               characters.shift(name.length());
               namespaceOutput.add(js_currentNS+"."+name);
               context.callManager.addCalledTemplate(context.getNS()+"."+name);
            }
         }

         if(hasNamespace){
            context.removeProduction();
            characters.removeSpace();
            char firstChar = characters.charAt(0);
            if( firstChar != '/' && firstChar != '}'){
               Output selectorOutput = new Output();
               contextOutput.add(js_SafeValue+"(").add(selectorOutput).add(")");
               context.addProduction(new ContextSelector(selectorOutput, false));   
            }
            return;
         }
         extraExcMsg="  A valid Namespace must be given with a CallExpression.";
      }
      exc("Invalid CallExpression."+extraExcMsg);
   }
}
