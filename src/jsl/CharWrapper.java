/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsl;

import java.nio.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Joseph W.S. Spencer
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