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

package jsl.strategies;

import java.util.regex.*;
import jsl.*;

/**
 *
 * @author Joseph Spencer
 */
public class JSLNamespace implements Strategy, Characters {
   Output output;
   JSLNamespace(Output output){
      this.output = output;
   }

   @Override
   public void execute(CharWrapper characters, StrategyContext context) throws Exception {
      String chunk;
      if(characters.charAt(0) == open){
         characters.shift(1);
         Matcher jsl = characters.match(JSL);

         if(jsl.find()){
            String js = jsl.group(1);
            characters.shift(3);
            Matcher space = characters.match(SPACE);

            if(space.find()){
               String sp = space.group(1);
               characters.shift(sp.length());
               Matcher namespace = characters.match(NAMESPACE);

               if(namespace.find()){
                  chunk = namespace.group(1);
                  characters.shift(chunk.length());
                  context.addNS(chunk);

                  if(characters.charAt(0) == close){
                     characters.shift(1);
                     context.removeStrategy();
                     return;
                  }
               }
            }

         }
      }
      throw new Exception("Invalid JSLNamespace was declared.");
   }

}
