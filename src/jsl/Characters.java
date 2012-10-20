/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsl;

import java.util.regex.Pattern;

/**
 *
 * @author Joseph W.S. Spencer
 */
public interface Characters {
   Pattern JSL = Pattern.compile("^(JSL)(?![a-zA-Z0-9$_]).*+");
   Pattern SPACE = Pattern.compile("^(\\s++).*+");
   Pattern OPEN_BLOCK = Pattern.compile("^(\\{).*+");
   Pattern CLOSE_BLOCK = Pattern.compile("^(\\}).*+");
   Pattern TEMPLATE = Pattern.compile("^(template)(?![a-zA-Z0-9]).*+");
   Pattern IF = Pattern.compile("^(if)(?![a-zA-Z0-9]).*+");
   Pattern CHOOSE = Pattern.compile("^(choose)(?![a-zA-Z0-9$_]).*+");
   Pattern WHEN = Pattern.compile("^(when)(?![a-zA-Z0-9$_]).*+");
   Pattern OTHERWISE = Pattern.compile("^(otherwise)(?![a-zA-Z0-9$_]).*+");
   Pattern NAME = Pattern.compile("^([$_a-zA-Z][a-zA-Z0-9$_]*+)\\}.*+");
   Pattern VARIABLE = Pattern.compile("^(@[a-zA-Z]++).*+");
   Pattern NAMESPACE = Pattern.compile("^((?:[a-zA-Z$_][a-zA-Z0-9$_]*+\\.)*+[a-zA-Z$_][a-zA-Z0-9$_]*+).*+");
   Pattern DATA = Pattern.compile("^((?!\\{)[^\\{]++).*+");
   Pattern RESERVED_SEQUENCE = Pattern.compile("^((?:callTemplate|forEach|variable|param|choose|if|import|otherwise|sort|template|when|withParam)\\s++[$_a-zA-Z]).*+");

   char open = 123;
   char close = 125;
   char forward = 47;
   char at = 64;
   char t = 116;
}
