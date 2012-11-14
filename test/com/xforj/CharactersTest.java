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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import junit.framework.*;
import org.junit.Test;

/**
 *
 * @author Joseph Spencer
 */
public class CharactersTest extends Assert {
   
   public CharactersTest() {
   }
   // TODO add test methods here.
   // The methods must be annotated with annotation @Test. For example:
   //
   @Test
   public void importPath(){
      //given, expected
      Map<String, String> importFailures = new HashMap<String, String>();
      importFailures.put("test/bla{}.boo", "test/bla{}.boo");
      importFailures.put("test/bla{\\}.boo", "test/bla{\\}.boo");

      //given, expected
      Map<String, String> importSuccesses = new HashMap<String, String>();
      importSuccesses.put("test/bla{\\}.xforj}", "test/bla{\\}.xforj");
      importSuccesses.put("test/bla.xforj}", "test/bla.xforj");

      Pattern importPattern = Characters.IMPORT_PATH;

      //Failures first
      for(String givenPath:importFailures.keySet()){
         Matcher match = importPattern.matcher(givenPath);
         if(match.find()){
            fail("The following path did not match:" + match.group(1));
         }
      }

      //Successes second
      for(String givenPath:importSuccesses.keySet()){
         String expectedResult = importSuccesses.get(givenPath);
         Matcher match = importPattern.matcher(givenPath);
         if(match.find()){
            assertEquals(expectedResult, match.group(1));
         } else {
            fail("The following path did not match:" + givenPath);
         }
      }
   }
}
