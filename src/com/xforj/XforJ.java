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
import java.util.*;
import java.util.Date;
import java.util.Iterator;

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
         XforJArguments arguments = XforJTerminal.getArguments(args);

         if(!arguments.hasInputfile() && !arguments.hasOutputfile()){
            throw new Exception("Both an input file and an output file must be given.");
         }

         long before = new Date().getTime();
         startCompiling(arguments);
         LOGGER.out("Time taken: "+Long.toString(new Date().getTime() - before));
      } catch(Exception exc) {
         handleGeneralError(exc);
      }

   }

   private static void handleGeneralError(Exception exc){
      LOGGER.out("FAILED FOR THE FOLLOWING REASON:\n");
      LOGGER.out(exc.getMessage());
      System.exit(UNABLE_TO_PARSE_FILE);
   }


   /**
    * This is the main entry point to XforJ from the ant task.
    * 
    * @param arguments
    */
   public static void startCompiling(XforJArguments arguments) {
      try {
         if(arguments.getDebug()){
            LOGGER.debug = true;
         }
         if(arguments.getWarn()){
            LOGGER.warn = true;
         }


         LOGGER.debug("startCompiling called.");

         if(//make sure there is input to process before proceeding.
            arguments.hasInputfile()
            || arguments.hasInputfiles()
         ){
            if(arguments.hasInputfile()){
               File input = arguments.getInputfile();
               if(arguments.hasOutputfile()){
                  compileNewFile(input, arguments.getOutputfile(), arguments);
               } else if(arguments.hasOutputdirectory()){
                  String outputDir = arguments.getOutputdirectory().getCanonicalPath();
                  if(!outputDir.endsWith(File.separator)){
                     outputDir+=File.separator;
                  }
                  String outputFilePath = outputDir+input.getName();
                  File outputFile = new File(outputFilePath);

                  compileNewFile(input, outputFile, arguments);
               } else {
                  throw new IllegalArgumentException("No target given for input file.");
               }

            }

            if(arguments.hasInputfiles()){
               if(arguments.hasOutputdirectory() && arguments.hasInputdirectory()){
                  String inputDirectoryPath = arguments.getInputdirectory().getCanonicalPath();
                  String outputDirectoryPath = arguments.getOutputdirectory().getCanonicalPath();

                  if(!inputDirectoryPath.endsWith(File.separator)){
                     inputDirectoryPath+=File.separator;
                  }
                  if(!outputDirectoryPath.endsWith(File.separator)){
                     outputDirectoryPath+=File.separator;
                  }

                  LOGGER.debug("inputDirectoryPath: "+inputDirectoryPath);
                  LOGGER.debug("outputDirectoryPath: "+outputDirectoryPath);

                  Iterator<File> files = arguments.getInputfiles().iterator(); 
                  while(files.hasNext()){
                     File next = files.next();
                     String fileName = next.getName();
                     String compiledFilePath = next.getCanonicalPath();

                     LOGGER.debug("fileName: "+fileName);
                     LOGGER.debug("outputPath: "+compiledFilePath);


                     compiledFilePath = compiledFilePath.replace(inputDirectoryPath, outputDirectoryPath);

                     LOGGER.debug("new outputPath: "+compiledFilePath);

                     compileNewFile(next, new File(compiledFilePath), arguments);
                  }
               } else {
                  throw new IllegalArgumentException("Both outputdirectory and inputdirectory must be given as attributes when using filesets.");
               }
            }
         } else {
            LOGGER.out("No input file[s] were given.  Exiting early.");
         }
      } catch(Exception exc) {
         handleGeneralError(exc);
      }
   }

   private static Set<File> compiledNewFiles = new HashSet(Arrays.asList(new File[]{}));
   private static void compileNewFile(File input, File outFile, XforJArguments arguments) throws Exception {
      if(compiledNewFiles.contains(input)){
         LOGGER.out("Ignoring: "+input.getCanonicalPath()+".  It has already been built.");
      } else {
         compiledNewFiles.add(input);
         String output = compileFile(input, new ProductionContext(input, arguments)).toString();
         MainUtil.putString(outFile, output);

         LOGGER.out("Compiled: "+input.getCanonicalPath());
         LOGGER.out("To: "+outFile.getCanonicalPath());
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
