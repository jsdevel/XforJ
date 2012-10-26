/*
 * Copyright 2012 Joseph Spencer
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
import java.util.regex.Matcher;

/**
 *
 * @author Joseph Spencer
 */
public class Program extends Production {
   Output programNamespaceOutput;
   Output importOutput;
   Output variableOutput;
   Output globalStatementsOutput;
   public Program(Output output, boolean imported){
      super(output);
      programNamespaceOutput=new Output();
      importOutput=new Output();
      variableOutput=new Output();
      globalStatementsOutput=new Output();

      output.
         prepend("(function("+js_StringBuffer+","+js_CountElements+"){").
            prepend(programNamespaceOutput).
            prepend(importOutput).
            prepend(variableOutput).
            prepend(globalStatementsOutput);

      if(imported){
         output.prepend("})("+js_StringBuffer+","+js_CountElements+");");
      } else {
         output.prepend("})(").
         prepend(
               //StringBuffer
               "function(){"+
                  "var v=[],i=0;"+
                  "return{"+
                     js_append+":function(s){"+
                        "v[i++]=s||''"+
                     "},"+
                     "toString:function(){"+
                        "return v.join('');"+
                     "}"+
                  "};"+
               "}"
            ).
         prepend(",").
            prepend(
               //CountElements
               "function(o){"+
                  "var c=0;"+
                  "var n;"+
                  "if(!!o && typeof(o)==='object'){"+
                     "if(o.slice&&o.join&&o.pop){"+
                        "return o.length>>>0;"+
                     "}else{"+
                        "for(n in o){"+
                           "c++;"+
                        "}"+
                     "}"+
                  "}"+
                  "return c"+
               "}"
            ).
         prepend(");");
      }
   }

   private boolean hasProgramNamespace;
   private boolean hasGlobalVariableDeclarations;

   @Override
   public void execute(CharWrapper characters, ProductionContext context) throws Exception {
      String exception = "The first Production must be a ProgramNamespace.";

      if(characters.charAt(0) == open && !hasProgramNamespace){
         hasProgramNamespace=true;
         context.addProduction(new ProgramNamespace(programNamespaceOutput));
         return;
      } else if(hasProgramNamespace){
         characters.removeSpace();
         if(characters.charAt(0) == open){
            if(characters.charAt(1) == i){
               if(!hasGlobalVariableDeclarations){
                  Output importStatementsOutput = new Output();
                  importOutput.
                     prepend("(function(){").
                     prepend(importStatementsOutput).
                     prepend("})();");
                  context.addProduction(new ImportStatements(importStatementsOutput));
                  return;
               } else {
                  throw new Exception("ImportStatements must appear before GlobalVariableStatements.");
               }
            } else if(characters.charAt(1) == v){
               hasGlobalVariableDeclarations=true;
               context.addProduction(new GlobalVariableDeclarations(variableOutput));
               return;
            }
            context.addProduction(new GlobalStatements(globalStatementsOutput));
            return;
         }
      }
      throw new Exception(exception);
   }

   @Override
   public void close(ProductionContext context) throws Exception {
      if(!hasProgramNamespace){
         throw new Exception("No ProgramNamespace was declared in: \""+context.filePath+"\"");
      }
   }
}
