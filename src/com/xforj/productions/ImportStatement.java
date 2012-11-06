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

package com.xforj.productions;

import com.xforj.CharWrapper;
import java.util.regex.Matcher;
import com.xforj.Output;

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
      if(characters.charAt(0) == '{' && characters.charAt(1) == 'i'){
         characters.shift(1);
         Matcher _import = characters.match(IMPORT);       
         if(_import.find()){
            characters.shift(_import.group(1).length());
            characters.removeSpace();
            Matcher path = characters.match(IMPORT_PATH);
            if(path.find()){
               String importedPath = path.group(1);
               characters.shift(importedPath.length());

               //we do this to ensure that we aren't building on the file to import, but
               //rather the file to import's directory.
               String newPath;
               if(importedPath.charAt(0) == '/'){
                  newPath = importedPath;
               } else {
                  Matcher absPath = ABSOLUTE_PATH.matcher(context.currentFile.getCanonicalPath());
                  if(absPath.find()){
                     newPath = absPath.group(1)+importedPath;
                  } else {
                     throw new Exception("Invalid Path given in: "+importedPath);
                  }
               }

               if(characters.charAt(0) == '}'){
                  characters.shift(1);


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
