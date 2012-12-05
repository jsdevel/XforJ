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
import com.xforj.arguments.*;
import java.io.*;

/**
 * This class is used to build out all the javascript needed by XforJ.  
 * 
 * Said javascript may be loaded through the external library, or it may be
 * included with all output (default).
 * 
 * @author Joseph Spencer
 */
public class JavascriptBuilder {
   private final String js_count;
   private final String js_escapexss;
   private final String js_foreach;
   private final String js_safeValue;
   private final String js_sortArray;
   private final String js_stringBuffer;

   private JavascriptBuilder(XforJArguments arguments) throws IOException{ 

      if(arguments.hasOutputlibrary()){
         MainUtil.putString(arguments.getOutputlibrary(), JavascriptResources.getXforJLib());
      } 

      //TODO: Make the namespace configurable.
      if(arguments.hasUseexternal()){
         js_count="xforj."+Characters.js_CountElements;
         js_escapexss="xforj."+Characters.js_EscapeXSS;
         js_foreach="xforj."+Characters.js_Foreach;
         js_safeValue="xforj."+Characters.js_SafeValue;
         js_sortArray="xforj."+Characters.js_GetSortArray;
         js_stringBuffer="xforj."+Characters.js_StringBuffer;
      } else {
         js_count=JavascriptResources.getCountElements();
         js_escapexss=JavascriptResources.getEscapeXSS();
         js_foreach=JavascriptResources.getForeach();
         js_safeValue = JavascriptResources.getSafeValue();
         js_sortArray = JavascriptResources.getGetSortArray();
         js_stringBuffer=JavascriptResources.getStringBuffer();
      }
   }
   public static JavascriptBuilder getInstance(XforJArguments arguments) throws IOException{
      return new JavascriptBuilder(arguments);
   }

   public String getXforJLib(){
      return JavascriptResources.getXforJLib();
   }
   public String getJSCount(){
      return js_count;
   }
   public String getJSEscapeXSS(){
      return js_escapexss;
   }
   public String getJSForeach(){
      return js_foreach;
   }
   public String getJSSafeValue(){
      return js_safeValue;
   }
   public String getJSSortArray(){
      return js_sortArray;
   }
   public String getJSStringBuffer(){
      return js_stringBuffer;
   }
}
