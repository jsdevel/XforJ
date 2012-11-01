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
 * CallManager provides a means to ensure that all called
 * templates within a file have actually been declared.
 * @author Joseph Spencer
 */
public class CallManager {
   private ArrayList<String> declaredTemplates = new ArrayList<>();
   private ArrayList<String> calledTemplates = new ArrayList<>();

   public void addDeclaredTemplate(String declared){
      declaredTemplates.add(declared);
   }

   public void addCalledTemplate(String called){
      calledTemplates.add(called);
   }

   public void validateCalls() throws Exception {
      for(String call : calledTemplates){
         if(!declaredTemplates.contains(call)){
            throw new Exception(call+" has not been declared.");
         }
      }
   }
}
