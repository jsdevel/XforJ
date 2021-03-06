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
package com.xforj;

import org.junit.*;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Joseph Spencer
 */
public class CharWrapperTest extends Assert {

   private static CharWrapper emptyChars;

   @BeforeClass
   public static void instantiateEmptyChars() throws Exception{
      emptyChars = new CharWrapper(new char[]{10,10});
      emptyChars.shift(2);
   }

   public CharWrapperTest() {}

   // TODO add test methods here.
   // The methods must be annotated with annotation @Test. For example:
   //
   @Test
   public void nullThrowsException() {
      try {
         new CharWrapper(null);
         fail("Expected failure on null in CharWrapperConstructor, but was success");
      } catch(Throwable e){}
   }

   @Test
   public void emptyCharsThrowsException() {
      try {
         new CharWrapper(new char[]{});
         fail("Expected failure on empty char array in CharWrapperConstructor, but was success");
      } catch(Throwable e){}
   }

   @Test
   public void nothingLeftToGet() {
      try {
         emptyChars.charAt(3);
         fail("Expected an error while trying to get a character when there were none left to parse.");
      } catch(Throwable e){}
   }

   @Test
   public void nothingLeftToShift(){
      try {
         emptyChars.shift(3);
         fail("Expected an error while trying to shift characters there were none left to parse.");
      } catch(Throwable e){}
   }
}