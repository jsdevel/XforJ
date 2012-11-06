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

import com.xforj.arguments.*;
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

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      try {
         startCompiling(XforJTerminal.getArguments(args));
      } catch(Exception exc) {
         handleGeneralError(exc);
      }

   }

   private static void handleGeneralError(Exception exc){
      LOGGER.out("FAILED FOR THE FOLLOWING REASON:\n");
      LOGGER.out(exc.getMessage());
      System.exit(UNABLE_TO_PARSE_FILE);
   }

   public static void startCompiling(XforJArguments arguments) {
      try {
         File input = arguments.getInputfile();

         File out = arguments.getOutputfile();
         String outPath = out.getAbsolutePath();

         long before = new Date().getTime();
         LOGGER.out("Compiling:  "+input.getAbsolutePath());
         String output = compileFile(input, new ProductionContext(input, arguments)).toString(); 
         LOGGER.out("Time taken: "+Long.toString(new Date().getTime() - before));

         LOGGER.out("Outputting: "+outPath);
         MainUtil.putString(new File(outPath), output);
      } catch(Exception exc) {
         handleGeneralError(exc);
      }
   }

   /**
    * This is for internal use by the compiler.
    * 
    * @param path Passed by import statements
    * @param previousContext
    * @return
    */
   public static Output compileFile(File fileToCompile, ProductionContext context) {
      CharWrapper wrapper=null;

      try{
         char[] chars=MainUtil.getChars(fileToCompile);

         CharWrapper characters = new CharWrapper(chars);
         wrapper=characters;
         while(characters.length() > 0){
            context.executeCurrent(characters);
         }
         context.close();
         return context.output;
      } catch(IOException exc){
         logException(fileToCompile, exc, null);
         System.exit(IO_Error);
      } catch(Exception exc){
         logException(fileToCompile, exc, wrapper);
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
}
