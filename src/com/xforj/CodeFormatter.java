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

import java.util.ArrayList;

/**
 *
 * @author Joseph Spencer
 */
public class CodeFormatter extends Output {
   private final String indent;
   int indentCount;
   
   public CodeFormatter(String indent){
      this.indent=indent;
   }

   public CodeFormatter addIndent(int amount){
      indentCount+=amount;
      return this;
   }
   public CodeFormatter addIndent(){
      indentCount++;
      return this;
   }
   public CodeFormatter doIndent(String input){
      for(int i=0;i<indentCount;i++){
         add(indent);
      }
      add(input);
      return this;
   }
   public CodeFormatter doIndent(){
      return doIndent("");
   }
   public CodeFormatter removeIndent(int amount){
      if(indentCount - amount < 0){
         indentCount = 0;
      } else {
         indentCount-=amount;
      }
      return this;
   }
   public CodeFormatter removeIndent(){
      if(indentCount > 0){
         indentCount--;
      }
      return this;
   }
   public CodeFormatter addLine(String line){
      doIndent(line);
      addLine();
      return this;
   }
   public CodeFormatter addLine(){
      add("\n");
      return this;
   }

   @Override
   public CodeFormatter add(Object obj){
      super.add(obj);
      return this;
   }
}
