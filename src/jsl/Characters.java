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

import java.util.regex.Pattern;

/**
 *
 * @author Joseph Spencer
 */
public interface Characters {
   Pattern SPACE = Pattern.compile("^(\\s++).*+");
   Pattern OPEN_BLOCK = Pattern.compile("^(\\{).*+");
   Pattern CLOSE_BLOCK = Pattern.compile("^(\\}).*+");

   Pattern JSL = Pattern.compile("^(JSL)(?=\\s).*+");
   Pattern CHOOSE = Pattern.compile("^(choose)(?=\\}).*+");
   Pattern IF = Pattern.compile("^(if)(?=\\s|\\}).*+");
   Pattern IMPORT = Pattern.compile("^(import)(?=\\s).*+");
   Pattern TEMPLATE = Pattern.compile("^(template)(?=\\s|\\}).*+");
   Pattern OTHERWISE = Pattern.compile("^(otherwise)(?=\\}).*+");
   Pattern WHEN = Pattern.compile("^(when)(?=\\s|\\}).*+");
   Pattern NAME = Pattern.compile("^([$_a-zA-Z][a-zA-Z0-9$_]*+)\\}.*+");
   Pattern IMPORT_PATH = Pattern.compile("^((?:(?:\\.\\.)?/(?:[^/\\}]++/)*+)?[^/\\}]++).*+");
   Pattern ABSOLUTE_PATH = Pattern.compile("^(/(?:[^/\\}]++/)*+).*+");


   Pattern INPUT_TOKENS = Pattern.compile("^([^\\{]++).*+");

   Pattern VARIABLE = Pattern.compile("^(var)(?=\\s).*+");
   Pattern NAMESPACE = Pattern.compile("^((?:[a-zA-Z$_][a-zA-Z0-9$_]*+\\.)*+[a-zA-Z$_][a-zA-Z0-9$_]*+).*+");
   Pattern RESERVED_SEQUENCE = Pattern.compile("^((?:callTemplate|forEach|variable|param|choose|if|import|otherwise|sort|template|when|withParam)\\s++[$_a-zA-Z]).*+");

   char open = 123;
   char close = 125;
   char forward = 47;
   char at = 64;

   char a = 97;
   char b = 98;
   char c = 99;
   char d = 100;
   char e = 101;
   char f = 102;
   char g = 103;
   char h = 104;
   char i = 105;
   char j = 106;
   char k = 107;
   char l = 108;
   char m = 109;
   char n = 110;
   char o = 111;
   char p = 112;
   char q = 113;
   char r = 114;
   char s = 115;
   char t = 116;
   char u = 117;
   char v = 118;
   char w = 119;
   char x = 120;
   char y = 121;
   char z = 122;
}
