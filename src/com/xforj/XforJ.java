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
package com.xforj;

import com.xforj.productions.ProductionContext;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.*;

/**
 *
 * @author Joseph Spencer
 */
public class XforJ implements Characters {
   //Exit codes
   public static final int UNABLE_TO_PARSE_FILE=1;
   public static final int IO_Error=2;
   public static final String PWD = System.getProperty("user.dir");

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      if(args.length < 2){
         LOGGER.out("\n\u00A9 Joseph Spencer.  Licensed under the Apache 2.0 License.\n"+
                     "Visit http://www.xforj.com for more info.");
         LOGGER.out("Usage:    java -jar XforJ.x.x.x.jar template output");
         LOGGER.out("Example:  java -jar XforJ.x.x.x.jar /my/template/file.html /my/template/output.js");
         return;
      }

      String inPath = getPathToUse(args[0]);
      String outPath = getPathToUse(args[1]);


      long before = new Date().getTime();
      LOGGER.out("Compiling:  "+inPath);
      String output = compileFile(inPath, null).toString(); 
      LOGGER.out("Time taken: "+Long.toString(new Date().getTime() - before));

      LOGGER.out("Outputting: "+outPath);
      try {
         MainUtil.putString(new File(outPath), output);
      } catch(IOException ex) {
         LOGGER.out("Something happened while attempting to wrtie to: "+outPath);
         LOGGER.out(ex.getMessage());
      }

   }

   public static Output compileFile(String path, ProductionContext previousContext) {
      File testFile = new File(path);
      CharWrapper wrapper=null;

      try{
         String absoluteFilePath = testFile.getCanonicalPath();
         ProductionContext context = new ProductionContext(absoluteFilePath, previousContext);
         char[] chars=MainUtil.getChars(testFile);

         CharWrapper characters = new CharWrapper(chars);
         wrapper=characters;
         while(characters.length() > 0){
            context.executeCurrent(characters);
         }
         context.close();
         return context.output;
      } catch(IOException exc){
         logException(testFile, exc, null);
         System.exit(IO_Error);
      } catch(Exception exc){
         logException(testFile, exc, wrapper);
         System.exit(UNABLE_TO_PARSE_FILE);
      }
      return null;
   }

   private static void logException(File file, Exception exc, CharWrapper wrapper){
      String message= "Unable to parse \""+
            file.getAbsolutePath()+
            "\" for the following reason:\n"+
            exc.getMessage();
      if(wrapper!=null){
         message+="\n"+wrapper.getErrorLocation();
      }
      LOGGER.out(message);
   }

   private static String getPathToUse(String path){
      String pathToUse;
      if(path.startsWith("/")){
         pathToUse = path;
      } else {
         pathToUse = PWD+"/"+path;
      }
      return pathToUse;
   }
}
