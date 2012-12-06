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

import com.xforj.*;
import com.xforj.arguments.*;
import com.xforj.javascript.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Joseph Spencer
 */
public class ProductionContext {
   private Production currentProduction;
   private ArrayList<Production> productionStack = new ArrayList<>();
   private VariableOutput currentVariableOutput = new VariableOutput();

   private String programNamespace="";

   //configuration
   final public boolean normalizespace;
   final public boolean minifyHTML;
   final public boolean assignTemplatesGlobally;
   final public boolean removeLogs;
   final public boolean escapexss;

   final public Output output;
   final private Map<String, Boolean> declaredNamespaces;
   final public File currentFile;
   final private Map<String, Boolean> importedFiles;
   final private JSParametersWrapper paramsWrapper;
   final private JSArgumentsWrapper argsWrapper;
   final private JSParameters params;
   final public CallManager callManager;
   final public JavascriptBuilder jsCode;

   public ProductionContext(
      File currentFile, 
      XforJArguments arguments,
      JavascriptBuilder javascriptBuilder,
      Output mainOutput
   ){
      normalizespace=arguments.getNormalizespace();
      minifyHTML=arguments.getMinifyhtml();
      assignTemplatesGlobally=arguments.getGlobal();
      removeLogs=arguments.getRemovelogs();
      escapexss=arguments.getEscapexss();
      jsCode=javascriptBuilder;
      output=mainOutput;

      declaredNamespaces = new HashMap<String, Boolean>();
      importedFiles = new HashMap<String, Boolean>();

      callManager = new CallManager();
      //parameters
      params=new JSParameters();
      paramsWrapper=new JSParametersWrapper(params);
      argsWrapper=new JSArgumentsWrapper(params);

      if(escapexss){
         params.put(Characters.js_EscapeXSS, jsCode.getJSEscapeXSS());
      }

      currentProduction= new Program(output, currentVariableOutput, this, false);
      productionStack.add(currentProduction);
      this.currentFile = currentFile;
   }

   public ProductionContext(
      File currentFile, 
      ProductionContext previousContext
   ){
      normalizespace=previousContext.normalizespace;
      minifyHTML=previousContext.minifyHTML;
      assignTemplatesGlobally=previousContext.assignTemplatesGlobally;
      removeLogs=previousContext.removeLogs;
      escapexss=previousContext.escapexss;
      jsCode=previousContext.jsCode;
      output = previousContext.output;

      declaredNamespaces=previousContext.declaredNamespaces;
      importedFiles=previousContext.importedFiles;
      
      callManager=previousContext.callManager;
      //parameters
      params=previousContext.params;
      paramsWrapper=previousContext.paramsWrapper;
      argsWrapper=previousContext.argsWrapper;

      this.currentFile=currentFile;
      currentProduction= new Program(output, currentVariableOutput, this, true);
      productionStack.add(currentProduction);
   }

   //Parameters
   public JSParameters getParams(){
      return params;
   }
   public JSParametersWrapper getJSParametersWrapper(){
      return paramsWrapper;
   }
   public JSArgumentsWrapper getArgumentsWrapper(){
      return argsWrapper;
   }

   //NAMESPACE
   /*
    * @param ns The namespace to set.
    */
   public void setNS(String ns) throws Exception {
      programNamespace=ns;
      declaredNamespaces.put(ns, true);
   }
   public String getNS(){
      return programNamespace;
   }

   //IMPORTS
   public Output importFile(String path) throws Exception {
      File targetFile = new File(path);
      String absolutePath = targetFile.getCanonicalPath();

      if(!absolutePath.endsWith(Characters.extension_xforj)){
         throw new IllegalArgumentException("Imported files must end with a .xforj extension.");
      }

      LOGGER.debug("Import request: "+path);
      LOGGER.debug("Actual file path: "+absolutePath);

      if(importedFiles.containsKey(absolutePath)){
         return new Output();
      }
      importedFiles.put(absolutePath, true);
      return XforJ.compileFile(targetFile, new ProductionContext(targetFile, this));
   }

   //OUTPUT
   public String escapeOutput(String input){
      return input.
         replace("\\{", "{").
         replace("\\#", "#").
         replaceAll("\\n|\\r", "\\\\n");
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
   public ProductionContext executeCurrent(CharWrapper wrap) throws Exception {
      currentProduction.execute(wrap, this);
      return this;
   }

   //VARIABLES
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
   public void validateVariableReference(String name) throws Exception {
      boolean hasVar = currentVariableOutput.hasVariableBeenDeclared(name);
      if(!hasVar){
         throw new Exception("Error while evaluating GlobalVariableValue.  Variable \""+name+"\" hasn't been declared yet.");
      }
   }

   //RESERVED WORDS
   private static Map<String, Boolean> reservedWords = new HashMap<String, Boolean>();
   static {
      reservedWords.put("break", true);
      reservedWords.put("case", true);
      reservedWords.put("catch", true);
      reservedWords.put("continue", true);
      reservedWords.put("debugger", true);//?
      reservedWords.put("default", true);
      reservedWords.put("delete", true);
      reservedWords.put("do", true);
      reservedWords.put("else", true);
      reservedWords.put("finally", true);
      reservedWords.put("for", true);
      reservedWords.put("function", true);
      reservedWords.put("if", true);
      reservedWords.put("in", true);
      reservedWords.put("instanceof", true);
      reservedWords.put("new", true);
      reservedWords.put("return", true);
      reservedWords.put("switch", true);
      reservedWords.put("this", true);
      reservedWords.put("throw", true);
      reservedWords.put("try", true);
      reservedWords.put("typeof", true);
      reservedWords.put("var", true);
      reservedWords.put("void", true);
      reservedWords.put("while", true);
      reservedWords.put("with", true);

      reservedWords.put("class", true);
      reservedWords.put("const", true);
      reservedWords.put("enum", true);//?
      reservedWords.put("export", true);//?
      reservedWords.put("extends", true);//?
      reservedWords.put("import", true);//?
      reservedWords.put("super", true);//?

      //future reserved words
      reservedWords.put("implements", true);//?
      reservedWords.put("interface", true);//?
      reservedWords.put("let", true);//?
      reservedWords.put("package", true);//?
      reservedWords.put("private", true);//?
      reservedWords.put("protected", true);//?
      reservedWords.put("public", true);//?
      reservedWords.put("static", true);//?
      reservedWords.put("yield", true);//?
   }
   public static void validateNamespacesAgainstReservedWords(String namespace) throws Exception {
      String[] names = namespace.split("\\.");

      for(String name:names){
         if(reservedWords.containsKey(name)){
            throw new Exception("   Usage of the following ECMAScript reserved word is not allowed: "+name);
         }
      }      
   }

   //CLOSING
   public void close() throws Exception {
      callManager.validateCalls();
      int size = productionStack.size();
      for(int i = size-1;i>-1;i--){
         productionStack.get(i).close(this);
      }
   }

   //ERRORS AND WARNINGS
   public void handleFileError(String msg) throws Exception {
   }
   public void handleFileWarning(String msg) throws Exception {
      LOGGER.warn(msg+currentFile.getCanonicalPath()+"\".");
   }
}
