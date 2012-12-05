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
package com.xforj.javascript.templates;

import com.xforj.*;
import com.xforj.javascript.*;
import com.xforj.javascript.JavascriptResources;
import com.xforj.javascript.JavascriptResourcesClassBuilder;
import java.io.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This class is intended to be run from netbeans at the moment using shift + F6.
 * This is far from ideal.  At some point, a more formalized approach to running
 * the tests will be in order.
 * 
 * @author Joseph Spencer
 */
public class JavascriptTemplatesTest extends Assert {
   //Change this based on your path.
   private static String pathToProject = MainUtil.addFileSeperatorToEndOfPath(System.getProperty("user.dir"));
   private static String pathToTemplates = pathToProject+"test/com/xforj/javascript/templates/";
   private static String pathToTests = pathToTemplates+"tests/";
   private static String pathToCompiled = pathToTemplates+"compiled/";
   private static String pathToXforJLib = "/tmp/XforJ.lib.js";

   private void runNodeScript(String path, String argument) throws IOException, InterruptedException{
      String command = "node "+path + " "+argument;

      Runtime run = Runtime.getRuntime();
      Process pr = run.exec(command);
      int success = pr.waitFor();
      InputStream output;

      if(success == 0){
         output = pr.getInputStream();
      } else {
         output = pr.getErrorStream();
      }

      BufferedReader buf = new BufferedReader(new InputStreamReader(output));
      String line;
      StringBuilder reason = new StringBuilder();
      while((line = buf.readLine())!=null){
         reason.append(line).append("\n");
      }
      System.out.println(reason.toString());

      if(success != 0){
         fail("Failed for the following reason:\n"+reason.toString()+"\n   While evaluating: "+path);
      }
   }

   @Before public void createCurrentJSFiles() throws IOException{
      //make sure the js is up to date.
      JavascriptResourcesClassBuilder.main(null);
      String XforJLibFile = JavascriptResources.getXforJLib();
      MainUtil.putString(new File(pathToXforJLib), XforJLibFile);
   }

   // TODO add test methods here.
   // The methods must be annotated with annotation @Test. For example:
   //
   @Test
   public void test_XforJ_lib_js_methods() throws IOException, InterruptedException {
      runNodeScript(
         pathToTemplates+"test_XforJ.lib.js_.js", //path

         pathToXforJLib+//arg1
         " "+
         Characters.js_StringBuffer+ //arg2. name of StringBuffer fn
         " "+
         Characters.js_EscapeXSS //arg2. name of StringBuffer fn
      );
   }

   @Test public void run_node_js_tests() throws IOException, InterruptedException{
      File[] tests = new File(pathToTests).listFiles();

      for(File test: tests){
         System.out.println("Test: " + test.getName());
         runNodeScript(
            test.getAbsolutePath(),
            pathToCompiled + test.getName()
         );
      }
   }

}