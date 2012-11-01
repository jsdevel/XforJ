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

package com.xforj;

import java.util.*;

/**
 *
 * @author Joseph Spencer
 */
public class AbstractVariableOutput extends Output {
   private final AbstractVariableOutput parentScope;

   private final String variablePrefix;
   private final String variableAssignmentOperator;
   private final String variableStatementPrefix;
   private final String variableStatementPostfix;

   protected Map<String, Object> variables = new HashMap<String, Object>();
   protected ArrayList<String> keys = new ArrayList<>();

   public AbstractVariableOutput(
      String variableStatementPrefix,
      String variablePrefix,
      String variableAssignmentOperator,
      String variableStatementPostfix,
      AbstractVariableOutput parentScope
   ) {
      this.parentScope = parentScope;

      if(variablePrefix==null){
         this.variablePrefix="";
      } else {
         this.variablePrefix=variablePrefix;
      }

      if(variableAssignmentOperator==null) {
         throw new NullPointerException("Seperator is required.");
      } else {
         this.variableAssignmentOperator=variableAssignmentOperator;
      }

      if(variableStatementPrefix==null) {
         throw new NullPointerException("Prefix is required.");
      } else {
         this.variableStatementPrefix=variableStatementPrefix;
      }

      if(variableStatementPostfix==null) {
         throw new NullPointerException("Postfix is required");
      } else {
         this.variableStatementPostfix=variableStatementPostfix;
      }
   }

   public final AbstractVariableOutput add(String name, Object value) throws Exception {
      if(variables.containsKey(variablePrefix+name)){
         throw new Exception("The following has been declared twice: "+name);
      }
      if(value == null){
         throw new Exception("Null value was discovered for the following: \""+name+"\"");
      }
      variables.put(variablePrefix+name, value);
      keys.add(variablePrefix+name);
      return this;
   }

   public final boolean hasVariableBeenDeclared(String name){
      String proposedName = variablePrefix+name;
      int size = keys.size();
      if(
         variables.containsKey(proposedName) || 
         size > 0 && 
         proposedName.equals(keys.get(size-1))||
         parentScope != null && parentScope.hasVariableBeenDeclared(name)
      ){
         return true;
      } else {
         return false;
      }
   }

   @Override
   public final String toString(){
      if(keys.size() > 0){
         String first = keys.remove(0);
         String firstValue = variables.get(first).toString();
         prepend(variableStatementPrefix+first);

         if(!"".equals(firstValue)){
            prepend(variableAssignmentOperator+firstValue);
         }
         for(String key : keys){
            prepend(","+key);
            String value = variables.get(key).toString();
            if(!"".equals(value)){
               prepend(variableAssignmentOperator+value);
            }
         }
         prepend(variableStatementPostfix);
         keys.clear();
         variables.clear();
      }
      return super.toString();
   }
}
