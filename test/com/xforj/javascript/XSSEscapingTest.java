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
package com.xforj.javascript;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author Joseph Spencer
 */
public class XSSEscapingTest {
   private static Map<String, String> toEscape = new HashMap<String, String>();

   public static final String inlineEventRegex = "(on)"+
         "("+
            "mouse(?:over|up|down|out|move)|"+
            "focus|"+
            "(?:dbl)?click|"+
            "key(?:down|press|up)|"+
            "abort|"+
            "error|"+
            "resize|"+
            "scroll|"+
            "(?:un)?load|"+
            "blur|"+
            "change|"+
            "focus|"+
            "reset|"+
            "select|"+
            "submit"+
         ")";

   public static final String scriptEscapeRegex = "(<\\s*?\\\\?\\s*?/?\\s*?)(script(?=[\\s>]))";

   private String testString;
   public XSSEscapingTest() {
      StringBuilder bld = new StringBuilder();
      for(String key:toEscape.keySet()){
         bld.append(key).append(toEscape.get(key));
      }
      testString = bld.toString();
   }
   
   @BeforeClass
   public static void setUpClass() {
      toEscape.put("</script", " ");
      toEscape.put("<script", " ");
      toEscape.put("onclick", " ");
      toEscape.put("ondblclick", " ");
      toEscape.put("onmousedown", " ");
      toEscape.put("onmousemove", " ");
      toEscape.put("onmouseover", " ");
      toEscape.put("onmouseout", " ");
      toEscape.put("onmouseup", " ");
      toEscape.put("onkeydown", " ");
      toEscape.put("onkeypress", " ");
      toEscape.put("onkeyup", " ");
      toEscape.put("onabort", " ");
      toEscape.put("onerror", " ");
      toEscape.put("onload", " ");
      toEscape.put("onresize", " ");
      toEscape.put("onscroll", " ");
      toEscape.put("onunload", " ");
      toEscape.put("onblur", " ");
      toEscape.put("onchange", " ");
      toEscape.put("onfocus", " ");
      toEscape.put("onreset", " ");
      toEscape.put("onselect", " ");
      toEscape.put("onsubmit", " ");
   }
   
   @AfterClass
   public static void tearDownClass() {
   }
   
   @Before
   public void setUp() {
   }
   
   @After
   public void tearDown() {
   }
   // TODO add test methods here.
   // The methods must be annotated with annotation @Test. For example:
   //
   @Test
   public void testingXSSEscaping(){
      String escapedString = testString.replaceAll(
         inlineEventRegex
      , "$1-$2").replaceAll(scriptEscapeRegex, "$1no$2");

      System.out.println("Test string: "+testString);   
      System.out.println("Escaped string: "+escapedString);   
      System.out.println("Regex string: "+inlineEventRegex);   
      System.out.println("Regex string: "+scriptEscapeRegex);   

      Assert.assertEquals(escapedString.indexOf("</script"), -1);
      Assert.assertEquals(escapedString.indexOf("<script"), -1);
      Assert.assertEquals(escapedString.indexOf("onclick"), -1);
      Assert.assertEquals(escapedString.indexOf("ondblclick"), -1);
      Assert.assertEquals(escapedString.indexOf("onmousedown"), -1);
      Assert.assertEquals(escapedString.indexOf("onmousemove"), -1);
      Assert.assertEquals(escapedString.indexOf("onmouseover"), -1);
      Assert.assertEquals(escapedString.indexOf("onmouseout"), -1);
      Assert.assertEquals(escapedString.indexOf("onmouseup"), -1);
      Assert.assertEquals(escapedString.indexOf("onkeydown"), -1);
      Assert.assertEquals(escapedString.indexOf("onkeypress"), -1);
      Assert.assertEquals(escapedString.indexOf("onkeyup"), -1);
      Assert.assertEquals(escapedString.indexOf("onabort"), -1);
      Assert.assertEquals(escapedString.indexOf("onerror"), -1);
      Assert.assertEquals(escapedString.indexOf("onload"), -1);
      Assert.assertEquals(escapedString.indexOf("onresize"), -1);
      Assert.assertEquals(escapedString.indexOf("onscroll"), -1);
      Assert.assertEquals(escapedString.indexOf("onunload"), -1);
      Assert.assertEquals(escapedString.indexOf("onblur"), -1);
      Assert.assertEquals(escapedString.indexOf("onchange"), -1);
      Assert.assertEquals(escapedString.indexOf("onfocus"), -1);
      Assert.assertEquals(escapedString.indexOf("onreset"), -1);
      Assert.assertEquals(escapedString.indexOf("onselect"), -1);
      Assert.assertEquals(escapedString.indexOf("onsubmit"), -1);
   }
}
