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
function(s){
   return s.replace(
      /(on)(mouse(?:over|up|down|out|move)|focus|(?:dbl)?click|key(?:down|press|up)|abort|error|resize|scroll|(?:un)?load|blur|change|focus|reset|select|submit)/gi
   ,'$1-$2')
   .replace(
      /(<\s*?\\?\s*?\/?\s*?)(script(?=[\s>]))/ig
   ,'$1some-$2')
}
