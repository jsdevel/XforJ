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
   //Exit codes
   public static final int UNABLE_TO_PARSE_FILE=1;

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      LOGGER.out(compileFile(args[0]).toString());
   }

   public static Output compileFile(String path) {
      File testFile = new File(path);
      StrategyContext context = new StrategyContext(testFile.getAbsolutePath());

      try {
         CharWrapper characters = new CharWrapper(MainUtil.getChars(testFile));
         while(characters.length() > 0){
            context.executeCurrent(characters);
         }
      } catch(Exception exc){
         LOGGER.out("Unable to parse \""+testFile.getAbsolutePath()+"\" for the following reason:\n"+exc.getMessage());
         System.exit(UNABLE_TO_PARSE_FILE);
      }
      return context.output;
   }
}
