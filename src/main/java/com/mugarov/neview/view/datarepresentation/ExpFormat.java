/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.neview.view.datarepresentation;

import com.mugarov.neview.model.MathHelp;
import com.mugarov.neview.model.elements.Dot;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mugarov
 */
public class ExpFormat extends NumberFormat {
    
    public static final int MAX_POS = 4;
    private final boolean useInt;
    
    public ExpFormat(){
        this.useInt = true;
    }
    
    public ExpFormat(boolean useInt){
        this.useInt = useInt;
    }
    

    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
        double exo = MathHelp.getExponential(number);
        String write = Double.toString(exo);
        if(this.useInt){
            if(exo ==Math.floor(exo)){
                toAppendTo.append(Integer.toString((int)exo));
            }
        }
        else{
            
            if(write.length()<write.indexOf(".")+1+MAX_POS){
                toAppendTo.append(write);
            }
            else{
                toAppendTo.append(write.substring(0, write.indexOf(".")+1+MAX_POS));
            }
        }
        return toAppendTo;
    }

    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        return this.format((double)number, toAppendTo, pos);
        
    }

    @Override
    public Number parse(String source, ParsePosition parsePosition) {
        Number ret = null;
        try {
            ret = super.parse(source);
        } catch (ParseException ex) {
            Logger.getLogger(ExpFormat.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
}
