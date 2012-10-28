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

import java.util.regex.Pattern;

/**
 *
 * @author Joseph Spencer
 */
public interface Characters {
   //SEQUENCES
   Pattern SPACE = Pattern.compile("^(\\s++).*+");
   Pattern SPACE_PRECEDING_CURLY = Pattern.compile("^(\\s++)(?=\\{).*+");
   Pattern OPEN_BLOCK = Pattern.compile("^(\\{).*+");
   Pattern CLOSE_BLOCK = Pattern.compile("^(\\}).*+");
   Pattern INPUT_TOKENS = Pattern.compile("^([^\\{]++).*+");
   Pattern IMPORT_PATH = Pattern.compile("^((?:/?(?:\\.\\./)?(?:[^/\\}]++/)*+)?[^/\\}]++).*+");
   Pattern ABSOLUTE_PATH = Pattern.compile("^(/(?:[^/\\}]++/)*+).*+");
   Pattern NS = Pattern.compile("^((?:[a-zA-Z$_][a-zA-Z0-9$_]*+\\.)*+[a-zA-Z$_][a-zA-Z0-9$_]*+).*+");
   Pattern CONTEXT_STATIC_REFINEMENT_NAMESPACE = Pattern.compile("^(\\s*+(?:[a-zA-Z$_][a-zA-Z0-9$_]*+\\s*+\\.)*+\\s*+[a-zA-Z$_][a-zA-Z0-9$_]*+\\s*).*+");

   //RESERVED WORDS
   Pattern CHOOSE = Pattern.compile("^(\\{choose\\}).*+");
   Pattern CHOOSE_CLOSING = Pattern.compile("^(\\{/choose\\}).*+");
   Pattern COUNT = Pattern.compile("^(count\\().*+");
   Pattern CURRENT = Pattern.compile("^(current\\(\\)).*+");
   Pattern FOREACH = Pattern.compile("^(\\{foreach\\s++).*+");
   Pattern FOREACH_CLOSING = Pattern.compile("^(\\{/foreach\\}).*+");
   Pattern IF = Pattern.compile("^(\\{if\\s++).*+");
   Pattern IF_CLOSING = Pattern.compile("^(\\{/if\\}).*+");
   Pattern IMPORT = Pattern.compile("^(import)(?=\\s).*+");
   Pattern LAST = Pattern.compile("^(last\\(\\)).*+");
   Pattern NAME = Pattern.compile("^([a-zA-Z$_][a-zA-Z0-9$_]*+).*+");
   Pattern NAMESPACE = Pattern.compile("^(namespace)(?=\\s).*+");
   Pattern OTHERWISE = Pattern.compile("^(\\{otherwise\\}).*+");
   Pattern OTHERWISE_CLOSING = Pattern.compile("^(\\{/otherwise\\}).*+");
   Pattern POSITION = Pattern.compile("^(position\\(\\)).*+");
   Pattern TEMPLATE = Pattern.compile("^(template)(?=\\s|\\}).*+");
   Pattern TEMPLATE_CLOSING = Pattern.compile("^(\\{/template\\})(?=\\s|\\}).*+");
   Pattern TEXT = Pattern.compile("^(\\{text\\}).*+");
   Pattern TEXT_CLOSING = Pattern.compile("^(\\{/text\\}).*+");
   Pattern WHEN = Pattern.compile("^(\\{when\\s++).*+");
   Pattern WHEN_CLOSING = Pattern.compile("^(\\{/when\\}).*+");

   //For now these two have the { at the beginning.  This probably needs to 
   //change in the future, but for now it allows the other keywords to test
   //directly on char rather than instantiating a Matcher.
   Pattern PARAM = Pattern.compile("^(\\{param\\s++).*+");
   Pattern VARIABLE = Pattern.compile("^(\\{variable\\s++).*+");


   //PRIMITIVES
   Pattern INTEGER = Pattern.compile("^((?:[0-9]++)(?!\\.)).*+");
   Pattern DECIMAL = Pattern.compile("^(0x[0-9A-Fa-f]++(?:[eE][+-][0-9]++)?|(?:0(?=\\.)|[1-9][0-9]*+)(?:\\.?[0-9]*+[eE][+-][0-9]++|\\.[0-9]++(?:[eE][+-][0-9]++)?)).*+");
   Pattern STRING = Pattern.compile("^((['\"])((?:(?!\\2)(?!\\r?\\n)(?:\\\\\\\\|\\\\\\r?\\n|\\\\\\2|[^\\r\\n]))*+)\\2).*+");
   Pattern NULL = Pattern.compile("^(null)(?![a-zA-Z0-9$_@'\"]).*+");


   //JAVASCRIPT
   /*DEVELOPMENT
   final String js_append="append";
   final String js_bld="bld";
   final String js_context="context";
   final String js_count="count";
   final String js_CountElements="CountElements";
   final String js_currentNS="currentNS";
   final String js_data="data";
   final String js__data="_data";
   final String js_foreach="Foreach";
   final String js_last="last";
   final String js_params="params";
   final String js__params="_params";
   final String js_position="position";
   final String js_StringBuffer="StringBuffer";
   */

   /*MUNGED*/
   final String js_append="a";
   final String js_bld="b";
   final String js_context="ct";
   final String js_count="c";
   final String js_CountElements="C";
   final String js_currentNS="n";
   final String js_data="d";
   final String js__data="_d";
   final String js_foreach="f";
   final String js_last="l";
   final String js_params="p";
   final String js__params="_p";
   final String js_position="po";
   final String js_StringBuffer="S";
   /**/
}
