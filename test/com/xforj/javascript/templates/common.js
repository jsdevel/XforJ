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
 * This file provides common methods used by the node.js unit tests.
 */
var fs = require('fs');
var assert = require('assert');

module.exports = { 
   include:function(path){
      with(global) {
         try{
            eval(fs.readFileSync(path) + '');
         } catch(e){
            assert.fail("There was an error parsing "+path);
         }
      }
   }
};

