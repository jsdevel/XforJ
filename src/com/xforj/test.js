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
(function(S,C,f){var n;try{people}catch(e){people={}}if(!people.cousins)people.cousins={};n=people.cousins;n.show=function(_d, _p){var ct=_d||{},p=_p||{},b=S(),l=''/0,po=l;b.a('Hello there!');f(((function(ct){try{return ct.cousins}catch(e){}})(ct)||""),function(ct,po,l){b.a('<h2>');b.a(((function(ct){try{return ct.name}catch(e){}})(ct)||""));b.a('</h2>');if(((function(ct){try{return ct.hobbies}catch(e){}})(ct)||"")){b.a('<ul>');f(((function(ct){try{return ct.hobbies}catch(e){}})(ct)||""),function(ct,po,l){b.a('<li>');b.a(((function(ct){try{return ct}catch(e){}})(ct)||""));b.a('</li>');});b.a('</ul>');}});return b.toString()};n.showProperties=function(_d, _p){var ct=_d||{},p=_p||{},b=S(),l=''/0,po=l;var __boo=p.boo||4||5&&3,__soo=p.soo||l,__joo=po,__zoo=C(((function(ct){try{return ct.boo.doo.dee[5+4||__joo]}catch(e){}})(ct)||"")),__dang=5;if(__boo===10){b.a('  Hello there!  ');}if(__dang===5){b.a('<div>Somedays I feel so overwhelmed.</div>');}b.a(C(((function(ct){try{return ct.relatives}catch(e){}})(ct)||"")));b.a(((function(ct){try{return ct[ct]}catch(e){}})(ct)||""));b.a('<h2 id=\"asdf\">');b.a(((function(ct){try{return ct.dan.boo[5].boo[4]}catch(e){}})(ct)||""));b.a('</h2><div><p>');b.a('Some paragraph.');b.a('</p></div>');if(__boo){if(5){b.a('            Hey there!!!         ');}}else if(2){}else{}return b.toString()};})(function(){var v=[],i=0;return{a:function(s){v[i++]=s||''},toString:function(){return v.join('');}};},function(o){var c=0;var n;if(!!o && typeof(o)==='object'){if(o.slice&&o.join&&o.pop){return o.length>>>0;}else{for(n in o){c++;}}}return c},function(o,c,s){var l=0,i=0,k=[];if(!!o&&typeof(o)==='object'&&typeof(c)==='function'){if(o.push&&o.slice&&o.join){l=o.length;for(;i<l;i++){c(o[i],i+1,l)}}else{for(i in o){k[k.length]=i;l++}if(!!s){}for(i=0;i<l;i++){c(o[k[i]],i,l)}}}});





