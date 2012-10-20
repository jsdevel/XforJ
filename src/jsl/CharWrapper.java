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
package jsl;

import java.nio.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Joseph Spencer
 */
public class CharWrapper {
   private char[] characters;
   private Pattern space = Pattern.compile("^(\\s++).*+");

   public CharWrapper(char[] characters) {
      this.characters=characters;
   }
   
   public int length() {
      return characters.length;
   }

   public char charAt(int index) {
      return characters[index];
   }

   public CharWrapper shift(int amount) throws Exception {
      int proposedLen = characters.length - amount;
      if(proposedLen > -1){
         char[] oldArr = characters;//testing
         char[] newArr = new char[proposedLen];
         System.arraycopy(characters, amount, newArr, 0, proposedLen);
         characters=newArr;
      } else {
         throw new Exception("Can't shift CharSequenceWrapper.  Nothing left to parse.");
      }
      return this;
   }

   public Matcher match(Pattern regex){
      return regex.matcher(CharBuffer.wrap(characters));
   }

   public boolean removeSpace() throws Exception {
      Matcher spaceToRemove = match(space);
      if(spaceToRemove.find()){
         shift(spaceToRemove.group(1).length());
         return true;
      }
      return false;
   }


   //TESTING
   public void destroy(){
      characters=new char[0];
   }

} 