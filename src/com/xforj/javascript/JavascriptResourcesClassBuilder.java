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

package com.xforj.javascript;

import com.xforj.*;
import java.io.*;

/**
 * This class creates JavscriptResources.java from the
 * contents of the javascript files in the resources package.
 * 
 * This file should be run with the base project directory as the user's
 * PWD.  It isn't guaranteed to produce java code that is error free.
 * You need to make sure that any special characters have been properly escaped.
 * 
 * 
 * @author Joseph Spencer
 */
public class JavascriptResourcesClassBuilder {
   public static void main(String[] args) throws IOException {

      //When run from net
      String basePath = MainUtil.addFileSeperatorToEndOfPath(System.getProperty("user.dir"));
      String javascriptPath = basePath + "src/com/xforj/javascript/";
      String resourcePath = javascriptPath + "resources/";

      String js_CountElements = clean(MainUtil.getString(new File(resourcePath+"CountElements.js")));
      String js_EscapeXSS = clean(MainUtil.getString(new File(resourcePath+"EscapeXSS.js")));
      String js_Foreach = clean(MainUtil.getString(new File(resourcePath+"Foreach.js")));
      String js_SafeValue = clean(MainUtil.getString(new File(resourcePath+"SafeValue.js")));
      String js_GetSortArray = clean(MainUtil.getString(new File(resourcePath+"GetSortArray.js")));
      String js_StringBuffer = clean(MainUtil.getString(new File(resourcePath+"StringBuffer.js")));

      String js_XforJLib = MainUtil.getString(new File(resourcePath+"XforJ.lib.js"));

      String pack = JavascriptResourcesClassBuilder.class.getPackage().getName();


      String XforJLibContents = js_XforJLib.
         replace("\"","\\\"").
         replace("\n","\\n").
         //these replace the characters in XforJ.lib.js
         //key
         replace("'##count##'", Characters.js_CountElements).
         replace("'##escapexss##'", Characters.js_EscapeXSS).
         replace("'##foreach##'", Characters.js_Foreach).
         replace("'##safeValueName##'", Characters.js_SafeValue).
         replace("'##sortFunctionName##'", Characters.js_GetSortArray).
         replace("'##stringBufferName##'", Characters.js_StringBuffer).

         //value
         replace("'##countFN##'", js_CountElements).
         replace("'##escapexssFN##'", js_EscapeXSS).
         replace("'##foreachFN##'", js_Foreach).
         replace("'##safeValueFn##'", js_SafeValue).
         replace("'##sorFunctionFn##'", js_GetSortArray).
         replace("'##stringBufferFn##'", js_StringBuffer);

      File JavascriptResources = new File(javascriptPath + "JavascriptResources.java");

      String indent = "   ";
      CodeFormatter code = new CodeFormatter(indent);

      code.
         addLine("package "+pack+";").
         addLine("public class JavascriptResources {").addIndent().

            addLine("public static String getXforJLib(){").addIndent().
               addLine("return \""+XforJLibContents+"\";").removeIndent().
            addLine("}").

            addLine("public static String getStringBuffer(){").addIndent().
               addLine("return \""+js_StringBuffer+"\";").removeIndent().
            addLine("}").

            addLine("public static String getSafeValue(){").addIndent().
               addLine("return \""+js_SafeValue+"\";").removeIndent().
            addLine("}").

            addLine("public static String getGetSortArray(){").addIndent().
               addLine("return \""+js_GetSortArray+"\";").removeIndent().
            addLine("}").

            addLine("public static String getForeach(){").addIndent().
               addLine("return \""+js_Foreach+"\";").removeIndent().
            addLine("}").

            addLine("public static String getCountElements(){").addIndent().
               addLine("return \""+js_CountElements+"\";").removeIndent().
            addLine("}").

            addLine("public static String getEscapeXSS(){").addIndent().
               addLine("return \""+js_EscapeXSS+"\";").removeIndent().
            addLine("}").
         removeIndent().
         addLine("}");

      MainUtil.putString(JavascriptResources, code.toString());
   }

   /**
    * This cleans comments and normalizes space from the internal javascript
    * functions.  It will temporarily replace space after certain reserved words
    * I.E. in, instanceof, etc., with a special sequence of characters: '@@##@@'.
    * The special sequence is then replaced with a single space after cleaning.
    * 
    * @param input 
    * @return String
    **/
   private static String clean(String input){
      if(input == null){
         throw new NullPointerException("There is an internal javascript file that isn't in existence.");
      }
      return input.
         replace("\\", "\\\\").
         replaceAll("/\\*(?:(?!\\*/)[\\s\\S])*+\\*/", "").
         replaceAll("//[^\\n\\r]++","").
         replaceAll("var\\s","var@@##@@").
         replaceAll("return\\s","return@@##@@").
         replaceAll("typeof\\s","typeof@@##@@").
         replaceAll("\\s(in|instanceof)\\s","@@##@@$1@@##@@").
         replaceAll("\\s++", "").
         replace("\"", "\\\"").
         replaceAll("@@##@@", " ");
   }
}
