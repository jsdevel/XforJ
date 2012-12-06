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
var common = require('../common.js');
var args = process.argv.splice(2);
var includescript = args[0];

common.include(includescript);

var context = {
   array:[0,1,2,3,4],
   object:{},
   bool:true
};
var tests = [
   ['testPositionInsideOfLoop', '12345'],
   ['testPositionOutsideOfLoop', 'NaN'],
   ['testCountArray', '5'],
   ['testCountObject', '3'],
   ['testCountNumber', '0'],
   ['testCountString', '0'],
   ['testCountBoolean', '0'],
   ['testNameInsideLoop', 'array|object|bool|'],
   ['testNameOutsideLoop', ''],
   ['testLastInsideLoop', '333'],
   ['testLastOutsideLoop', 'NaN']
];

tests.forEach(function(value, index, array){
   var method = value[0];
   var actual = testing[method](context);
   var expected = value[1];

   assert.equal(actual, expected, method);
});


