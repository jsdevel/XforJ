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
(function(StringBuffer, CountElements){var currentNS;try{people}catch(e){people={}}if(!people.cousins)people.cousins={};currentNS=people.cousins;currentNS.showPeople=$showPeople;function $showPeople(_data, _params){var context=_data||{},params=_params||{},bld=new StringBuffer(),last=''/0,position=last;bld.append('Hello there!       ');bld.append(((function(context){try{return context.boo}catch(e){}})(context)||""));bld.append('<h2>');bld.append(((function(context){try{return context.dan}catch(e){}})(context)||""));bld.append('</h2>');return bld.toString()}currentNS.showProperties=$showProperties;function $showProperties(_data, _params){var context=_data||{},params=_params||{},bld=new StringBuffer(),last=''/0,position=last;var __boo=params.boo||4||5&&3,__soo=params.soo||last,__joo=position,__zoo=CountElements(((function(context){try{return context.boo.doo.dee[5+4||__joo]}catch(e){}})(context)||"")),__dang=5;if(__boo===10){bld.append('  Hello there!  ');}if(__dang===5){bld.append('<div>Somedays I feel so overwhelmed.</div>');}bld.append(CountElements(((function(context){try{return context.relatives}catch(e){}})(context)||"")));bld.append(((function(context){try{return context[context]}catch(e){}})(context)||""));bld.append('<h2 id=\"asdf\">');bld.append(((function(context){try{return context.dan.boo[5].boo[4]}catch(e){}})(context)||""));bld.append('</h2><div><p>');bld.append('Some paragraph.');bld.append('</p></div>');if(__boo){if(5){bld.append('            Hey there!!!         ');}}else if(2){}else{}return bld.toString()}})(function StringBuffer(){var v=[],i=0;this.append=function(s){v[i++]=s||'';};this.toString=function(){return v.join('');};},function CountElements(obj){var cnt=0;var name;if(!!obj && typeof obj === 'object'){if(obj.slice){return obj.length>>>0;} else {for(name in obj){cnt++;}}}return cnt;});




