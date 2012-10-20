/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsl;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joseph W.S. Spencer
 */
public class Output {
   List<Object> prependNodes = new ArrayList();
   List<Object> appendNodes = new ArrayList();
   
   public Output prepend(Object obj){
      prependNodes.add(obj);
      return this;
   }

   public Output append(Object obj){
      appendNodes.add(0, obj);
      return this;
   }

   @Override
   public String toString(){
      StringBuilder str = new StringBuilder();
      while(!prependNodes.isEmpty()){
         str.append(prependNodes.remove(0).toString());
      }
      while(!appendNodes.isEmpty()){
         str.append(appendNodes.remove(appendNodes.size()-1).toString());
      }
      return str.toString();
   }
   
}
