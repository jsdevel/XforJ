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
package com.xforj;

import java.nio.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Joseph Spencer
 */
public class CharWrapper implements Characters {
   private char[] characters;
   private int line=1;
   private int column=1;

   public CharWrapper(char[] characters) throws IllegalArgumentException {
      if(characters == null || !(characters.length > 0)){
         throw new IllegalArgumentException("   .xforj files may not be empty.");
      }
      this.characters=characters;
   }
   
   public int length() {
      return characters.length;
   }

   public char charAt(int index) {
      if(index >= characters.length){
         throw new ArrayIndexOutOfBoundsException("   There are no more characters to parse.");
      }
      return characters[index];
   }

   public CharWrapper shift(int amount) throws Exception {
      int proposedLen = characters.length - amount;
      if(proposedLen > -1){
         //set the appropriate line / column values
         for(int i=0;i<amount;i++){
            char next = charAt(i);
            switch(next){
            case '\r':
               if(charAt(i+1) == '\n'){
                  i++;
               }
            case '\n':
               line++;
               column=1;
               continue;
            default:
               column++;
            }
         }
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
      Matcher spaceToRemove = match(SPACE);
      if(spaceToRemove.find()){
         shift(spaceToRemove.group(1).length());
         return true;
      }
      return false;
   }

   //ERROR HANDLING
   public String getErrorLocation(){
      return   "Line   : "+Integer.toString(line)+"\n"+
               "Column : "+Integer.toString(column)+"\n";
   }

} 