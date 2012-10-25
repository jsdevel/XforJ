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
package jsl.strategies;

import java.util.regex.Matcher;
import jsl.*;

/**
 *
 * @author Joseph Spencer
 */
public class Program extends Production {
   Output programNamespaceOutput;
   Output importOutput;
   Output variableOutput;
   Output globalStatementsOutput;
   public Program(Output output){
      super(output);
      programNamespaceOutput=new Output();
      importOutput=new Output();
      variableOutput=new Output();
      globalStatementsOutput=new Output();

      output.
         prepend("(function(){").
            prepend(programNamespaceOutput).
            prepend(importOutput).
            prepend(variableOutput).
            prepend(globalStatementsOutput).
         prepend("})();");


      globalStatementsOutput.
      append(
            "function StringBuffer(){"+
               "var v=[],i=0;"+
               "this.append=function(s){"+
                  "v[i++]=s||'';"+
               "};"+
               "this.toString=function(){"+
                  "return v.join('');"+
               "};"+
            "}"
         ).
         append(
            "function count(obj){"+
               "var count=0;"+
               "var name;"+
               "if(!!obj && typeof obj === 'object'){"+
                  "if(obj.slice){"+
                     "return obj.length>>>0;"+
                  "} else {"+
                     "for(name in obj){"+
                        "count++;"+
                     "}"+
                  "}"+
               "}"+
               "return count;"+
            "}"
         );
   }

   private boolean hasProgramNamespace;

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
               importOutput.prepend("(function(){");
               importOutput.append("})();");
               context.addProduction(new ImportStatements(importOutput));
               return;
            } else if(characters.charAt(1) == v){
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
