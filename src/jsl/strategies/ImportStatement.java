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

import java.util.regex.Matcher;
import jsl.*;
import jsl.Output;

/**
 *
 * @author Joseph Spencer
 */
public class ImportStatement extends Production {
   public ImportStatement(Output output) {
      super(output);
   }

   @Override
   public void execute(CharWrapper characters, ProductionContext context) throws Exception {
      if(characters.charAt(0) == open && characters.charAt(1) == i){
         characters.shift(1);
         Matcher _import = characters.match(IMPORT);       
         if(_import.find()){
            characters.shift(_import.group(1).length());
            characters.removeSpace();
            Matcher path = characters.match(IMPORT_PATH);
            if(path.find()){
               String pth = path.group(1);
               //handle importing new JSL;
               characters.shift(pth.length());
               if(characters.charAt(0) == close){
                  characters.shift(1);
                  String newPath;
                  if(pth.charAt(0) == forward){
                     newPath = pth;
                  } else {
                     Matcher absPath = ABSOLUTE_PATH.matcher(context.filePath);
                     if(absPath.find()){
                        newPath = absPath.group(1)+pth;
                     } else {
                        throw new Exception("Invalid Absolute Path given in: "+pth);
                     }
                  }
                  output.prepend(context.importFile(newPath));
                  return;
               }
            }
         }
         throw new Exception("Invalid Statement found while parsing ImportStatement.");
      }
      context.removeProduction();
   }
}
