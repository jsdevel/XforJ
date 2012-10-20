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

import java.io.File;
import jsl.*;
import jsl.strategies.*;

/**
 *
 * @author Joseph Spencer
 */
public class JSL implements Characters {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      File testFile = new File(args[0]);
      CharWrapper characters = new CharWrapper(MainUtil.getChars(testFile));
      StrategyContext context = new StrategyContext();

      try {
         while(characters.length() > 0){
            context.executeCurrent(characters);
         }
         LOGGER.out(context.output.toString());
      } catch(Exception exc){
         LOGGER.out("Unable to parse \""+testFile.getAbsolutePath()+"\" for the following reason:\n"+exc.getMessage());
      }

   }
}
