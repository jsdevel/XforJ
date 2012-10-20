/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsl.strategies;

import jsl.*;

/**
 *
 * @author Joseph W.S. Spencer
 */
public interface Strategy {
   void execute(CharWrapper characters, StrategyContext context) throws Exception;
}
