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

/**
 * This class is used to build out all the javascript needed by XforJ.  
 * 
 * Said javascript may be loaded through the external library, or it may be
 * included with all output (default).
 * 
 * @author Joseph Spencer
 */
public class JavascriptBuilder {
   private static JavascriptBuilder instance;

   private final String XforJLibFile;

   private final String js_stringBuffer_fn;
   private final String js_safeValue_fn;
   private final String js_sortArray_fn;
   private final String js_foreach_fn;
   private final String js_count_fn;

   private final String js_stringBuffer;
   private final String js_safeValue;
   private final String js_sortArray;
   private final String js_foreach;
   private final String js_count;

   private JavascriptBuilder(XforJArguments arguments){ 
      //getResourceFileContents was causing issues.
      //String stringBufferFile = MainUtil.getResourceFileContents("/com/xforj/javascript/StringBuffer.js");
      String stringBufferFile =  "function(){"+
                                 "var r=[],"+
                                 "i=0,"+
                                 "t='number string boolean',"+
                                 "f=function(s){"+
                                 "var y,v;"+
                                 "try{"+
                                 "v=s();"+
                                 "}catch(e){"+
                                 "v=s;"+
                                 "}"+
                                 "y=typeof(v);"+
                                 "r[i++]=(t.indexOf(y)>-1)?v:''"+
                                 "};"+
                                 "f.s=function(){"+
                                 "return r.join('')"+
                                 (arguments.getEscapexss()?
                                    ".replace("+
                                    "/(on)(mouse(?:over|up|down|out|move)|focus|(?:dbl)?click|key(?:down|press|up)|abort|error|resize|scroll|(?:un)?load|blur|change|focus|reset|select|submit)/gi"+
                                    ",'$1-$2')"+
                                    ".replace("+
                                    "/(<\\s*?\\?\\s*?\\/?\\s*?)(script(?=[\\s>]))/ig"+
                                    ",'$1no$2')"
                                 :"")+
                                 "};"+
                                 "return f"+
                                 "}";
      String XforJLibContents="";
      if(!arguments.getEscapexss()){
         stringBufferFile = stringBufferFile.replaceFirst("/\\*escapexss\\*/(?:(?!/\\*/escapexss\\*/)[\\s\\S])*+/\\*/escapexss\\*/", "");
      }

      if(arguments.hasOutputlibrary() || !arguments.getUseexternal()){
         js_stringBuffer_fn=clean(stringBufferFile);
         //js_safeValue_fn = clean(MainUtil.getResourceFileContents("/com/xforj/javascript/SafeValue.js"));
         //js_sortArray_fn = clean(MainUtil.getResourceFileContents("/com/xforj/javascript/GetSortArray.js"));
         //js_foreach_fn = clean(MainUtil.getResourceFileContents("/com/xforj/javascript/Foreach.js"));
         //js_count_fn = clean(MainUtil.getResourceFileContents("/com/xforj/javascript/CountElements.js"));

         js_safeValue_fn = clean(
            "function(v){"+
               "try{"+
                  "return v()"+
               "}catch(e){"+
                  "return typeof(v)==='function'?void(0):v"+
               "}"+
            "}"         
         );
         js_sortArray_fn = clean(
            "function(l,s,i){"+
            "var r=[],a,v,o;"+
            "try{o=l()}catch(e){o=l}"+
            "if(!!o&&typeof(o)==='object'){"+
            "for(a in o){"+
            "try{"+
            "v=s(o[a]);"+
            "r.push({"+
            "n:a,"+//name"+
            "c:o[a],"+//context"+
            "k:typeof(v)==='string'&&i?v.toLowerCase():v"+////key.  Used by the sort algorithm in foreach."+
            "});"+
            "} catch(e){"+
            "r.push({"+
            "n:a,"+
            "c:o[a],"+
            "k:''"+
            "});"+
            "}"+
            "}"+
            "}"+
            "return r"+
            "}"         
         );
         js_foreach_fn = clean(
            "function(o,c,so,n){"+
            "var i=0,l,m;"+
            "if(!!o&&typeof(o)==='object'&&typeof(c)==='function'){"+
            "l=o.length;"+
            "if(so!==void(0))o.sort("+
            "function(c,d){"+
            "var a=c.k,b=d.k,at=typeof(a),bt=typeof(b);"+
            "if(a===b)return 0;"+
            "if(at===bt)return (!!so?a<b:a>b)?-1:1;"+
            "return (!!n?at<bt:at>bt)?-1:1"+
            "}"+
            ");"+
            "for(;i<l;i++){"+
            "m=o[i];"+
            "c(m.c, i+1, o.length, m.n)"+
            "}"+
            "}"+
            "}"         
         );
         js_count_fn = clean(
            "function(f){"+
            "var o,"+
            "c=0,"+
            "n;"+
            "try{o=f()}catch(e){o=f}"+
            "if(!!o && typeof(o)==='object'){"+
            "if(o.slice&&o.join&&o.pop){"+
            "return o.length>>>0;"+
            "}else{"+
            "for(n in o){"+
            "c++;"+
            "}"+
            "}"+
            "}"+
            "return c"+
            "}"         
         );
      } else {
         js_stringBuffer_fn="";
         js_safeValue_fn = "";
         js_sortArray_fn = "";
         js_foreach_fn = "";
         js_count_fn = "";
      }

      if(arguments.hasOutputlibrary()){
         //XforJLibContents = MainUtil.getResourceFileContents("/com/xforj/javascript/XforJ.lib.js").
         XforJLibContents ="xforj={"+
                     "'##stringBufferName##':'##stringBufferFn##',"+
                     "'##safeValueName##':'##safeValueFn##',"+
                     "'##sortFunctionName##':'##sorFunctionFn##',"+
                     "'##foreach##':'##foreachFN##',"+
                     "'##count##':'##countFN##'"+
                     "};".
            //these replace the characters in XforJ.lib.js
            //key
            replace("'##stringBufferName##'", Characters.js_StringBuffer).
            replace("'##safeValueName##'", Characters.js_SafeValue).
            replace("'##sortFunctionName##'", Characters.js_GetSortArray).
            replace("'##foreach##'", Characters.js_foreach).
            replace("'##count##'", Characters.js_CountElements).

            //value
            replace("'##stringBufferFn##'", js_stringBuffer_fn).
            replace("'##safeValueFn##'", js_safeValue_fn).
            replace("'##sorFunctionFn##'", js_sortArray_fn).
            replace("'##foreachFN##'", js_foreach_fn).
            replace("'##countFN##'", js_count_fn);

      } 

      //TODO: Make the namespace configurable.
      if(arguments.hasUseexternal()){
         js_stringBuffer="xforj."+Characters.js_StringBuffer;
         js_safeValue="xforj."+Characters.js_SafeValue;
         js_sortArray="xforj."+Characters.js_GetSortArray;
         js_foreach="xforj."+Characters.js_foreach;
         js_count="xforj."+Characters.js_CountElements;
      } else {
         js_stringBuffer=js_stringBuffer_fn;
         js_safeValue = js_safeValue_fn;
         js_sortArray = js_sortArray_fn;
         js_foreach=js_foreach_fn;
         js_count=js_count_fn;
      }

      XforJLibFile=XforJLibContents;
   }
   public static JavascriptBuilder getInstance(XforJArguments arguments){
      if(instance == null){
         instance = new JavascriptBuilder(arguments);
      }
      return instance;
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
      return input;//input.
         //replaceAll("/\\*(?:(?!\\*/)[\\s\\S])*+\\*/", "").
         //replaceAll("//[^\\n\\r]++","").
         //replaceAll("var\\s","var@@##@@").
         //replaceAll("return\\s","return@@##@@").
         //replaceAll("typeof\\s","typeof@@##@@").
         //replaceAll("\\s(in|instanceof)\\s","@@##@@$1@@##@@").
         //replaceAll("\\s++", "").
         //replaceAll("@@##@@", " ");
   }

   //Need to implement this;
   public String getLibrary(){
      return XforJLibFile;
   }

   public String getJSStringBuffer(){
      return js_stringBuffer;
   }
   //TODO Fix these
   public String getJSSortArray(){
      return js_sortArray;
   }
   public String getJSSafeValue(){
      return js_safeValue;
   }
   public String getJSForeach(){
      return js_foreach;
   }
   public String getJSCount(){
      return js_count;
   }
}
