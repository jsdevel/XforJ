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
package jsl;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joseph Spencer
 */
public class Output {
   List<Object> prependNodes = new ArrayList();
   List<Object> appendNodes = new ArrayList();
   
   public Output prepend(Object obj){
      prependNodes.add(obj);
      return this;
   }

   public Output append(Object obj){
      appendNodes.add(0, obj);
      return this;
   }

   @Override
   public String toString(){
      StringBuilder str = new StringBuilder();
      while(!prependNodes.isEmpty()){
         str.append(prependNodes.remove(0).toString());
      }
      while(!appendNodes.isEmpty()){
         str.append(appendNodes.remove(appendNodes.size()-1).toString());
      }
      return str.toString();
   }
   
}
