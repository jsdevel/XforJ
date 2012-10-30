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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Joseph Spencer
 */
public class JSParameters {
   private ArrayList<String> keys = new ArrayList<>();
   private Map<String, Object> values = new HashMap<>();

   public JSParameters put(String key, Object value){
      if(!values.containsKey(key) && key != null && value != null){
         keys.add(key);
         values.put(key, value);
      }
      return this;
   }

   public String getParameters(){
      StringBuilder params = new StringBuilder();
      int size = keys.size();
      if(size > 0){
         Object[] keys = this.keys.toArray();
         params.append(keys[0].toString());
         for(int i=1;i<size;i++){
            params.append(",").append(keys[i].toString());
         }
      }
      return params.toString();
   }

   public String getArguments(){
      StringBuilder args = new StringBuilder();
      int size = keys.size();
      if(size > 0){
         Object[] keys = this.keys.toArray();
         args.append(values.get(keys[0]).toString());
         for(int i=1;i<size;i++){
            args.append(",").append(values.get(keys[i].toString()));
         }
      }
      return args.toString();
   }

}
