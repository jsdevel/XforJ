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
 * 
 * This file is run from the JavascriptTemplatesTest.
 */
var assert = require('assert');
var common = require('./common.js');
var args = process.argv.splice(2);
var XforJLibLoc = args[0];
var StringBuffer = args[1];
var EscapeXSS = args[2];
var msg;

console.log("Testing XforJ.lib.js");
common.include(XforJLibLoc);

assert(xforj, "xforj has not been initialized.");


//STRING BUFFER
var newSB = xforj[StringBuffer]();

assert(newSB, "StringBuffer is not initialized.");

newSB("hello");
newSB(" There");
newSB(" Buddy");

assert.equal(newSB.s(), "hello There Buddy", "StringBuffer is not working as expected.");



//ESCAPE XSS
var newEscapeXSS = xforj[EscapeXSS];
assert.equal(typeof newEscapeXSS, 'function', "EscapeXSS isn't initialized.");

var notEquals = [
   "<script",
   "<script>",
   "</script>",
   "onmouseover"
];
notEquals.forEach(function(val, index, array){
   assert.notEqual(newEscapeXSS(val), val, "Testing "+val+" failed.");
});
