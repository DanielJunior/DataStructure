/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.exceptions;

/**
 *
 * @author danieljunior
 */
public class DuplicatedItemException extends Exception{

    @Override
    public String getMessage() {
        return "Already exists this content"; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
