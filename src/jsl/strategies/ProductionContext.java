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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import jsl.*;
import jsl.Output;

/**
 *
 * @author Joseph Spencer
 */
public class ProductionContext {
   private Production currentProduction;
   private ArrayList<Production> productionStack = new ArrayList<>();

   public final Output output = new Output();

   public final boolean stripNewLines;
   public final boolean minifyHTML;

   private static Map<String, Boolean> declaredNamespaces = new HashMap<String, Boolean>();
   private String programNamespace="";

   public final String filePath;

   public ProductionContext(String absoluteFilePath, boolean imported) {
      //at some point these need to be configurable.
      stripNewLines=true;
      minifyHTML=true;
      currentProduction= new Program(output, imported);
      productionStack.add(currentProduction);
      filePath=absoluteFilePath;
   }

   //NAMESPACE
   public void setNS(String ns) throws Exception {
      if(declaredNamespaces.containsKey(ns)){
         throw new Exception("Namespace \""+ns+"\" has already been declared.");
      }
      programNamespace=ns;
      declaredNamespaces.put(ns, true);
   }
   public String getNS(){return programNamespace;}

   //IMPORTS
   private static Map<String, Boolean> importedFiles = new HashMap<String, Boolean>();
   public Output importFile(String path, boolean imported) throws Exception {

      File targetFile = new File(path);
      String absolutePath = targetFile.getCanonicalPath();

      if(importedFiles.containsKey(absolutePath)){
         return new Output();
      }
      importedFiles.put(absolutePath, true);
      return JSL.compileFile(path, imported);
   }

   //PRODUCTIONS
   public ProductionContext addProduction(Production add){
      productionStack.add(add);
      currentProduction=add;
      return this;
   }
   public ProductionContext removeProduction(){
      productionStack.remove(productionStack.size()-1);
      currentProduction=productionStack.get(productionStack.size()-1);
      return this;
   }
   public ProductionContext executeCurrent(CharWrapper wrap) throws Exception{
      currentProduction.execute(wrap, this);
      return this;
   }

   //VARIABLES
   private VariableOutput currentVariableOutput = new VariableOutput();
   private ArrayList<VariableOutput> variableOutputStack = new ArrayList<>();
   {
      variableOutputStack.add(currentVariableOutput);
   }
   public VariableOutput getCurrentVariableOutput(){
      return currentVariableOutput;
   }
   public ProductionContext addVaribleOutput(){
      VariableOutput newOutput = new VariableOutput(currentVariableOutput);
      currentVariableOutput=newOutput;
      variableOutputStack.add(newOutput);
      return this;
   }
   public ProductionContext removeVariableOutput() throws Exception {
      int size = variableOutputStack.size();
      if(size > 1){
         variableOutputStack.remove(size-1);
         currentVariableOutput=variableOutputStack.get(size-2);
         return this;
      }
      throw new Exception("Illegal attempt to remove VariableOutput.");
   }

   //CLOSING
   public void close() throws Exception {
      int size = productionStack.size();
      for(int i = size-1;i>-1;i--){
         productionStack.get(i).close(this);
      }
   }
}
