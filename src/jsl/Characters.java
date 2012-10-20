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
   Pattern JSL = Pattern.compile("^(JSL)(?![a-zA-Z0-9$_]).*+");
   Pattern SPACE = Pattern.compile("^(\\s++).*+");
   Pattern OPEN_BLOCK = Pattern.compile("^(\\{).*+");
   Pattern CLOSE_BLOCK = Pattern.compile("^(\\}).*+");
   Pattern TEMPLATE = Pattern.compile("^(template)(?![a-zA-Z0-9]).*+");
   Pattern IF = Pattern.compile("^(if)(?![a-zA-Z0-9]).*+");
   Pattern CHOOSE = Pattern.compile("^(choose)(?![a-zA-Z0-9$_]).*+");
   Pattern WHEN = Pattern.compile("^(when)(?![a-zA-Z0-9$_]).*+");
   Pattern OTHERWISE = Pattern.compile("^(otherwise)(?![a-zA-Z0-9$_]).*+");
   Pattern NAME = Pattern.compile("^([$_a-zA-Z][a-zA-Z0-9$_]*+)\\}.*+");
   Pattern VARIABLE = Pattern.compile("^(@[a-zA-Z]++).*+");
   Pattern NAMESPACE = Pattern.compile("^((?:[a-zA-Z$_][a-zA-Z0-9$_]*+\\.)*+[a-zA-Z$_][a-zA-Z0-9$_]*+).*+");
   Pattern DATA = Pattern.compile("^((?!\\{)[^\\{]++).*+");
   Pattern RESERVED_SEQUENCE = Pattern.compile("^((?:callTemplate|forEach|variable|param|choose|if|import|otherwise|sort|template|when|withParam)\\s++[$_a-zA-Z]).*+");

   char open = 123;
   char close = 125;
   char forward = 47;
   char at = 64;
   char t = 116;
}
