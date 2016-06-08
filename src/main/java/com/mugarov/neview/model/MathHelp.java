/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.neview.model;

import com.mugarov.neview.model.elements.Dot;

/**
 *
 * @author mugarov
 */
public abstract class MathHelp {
    
    public static double getExponential(double value){
        return Math.pow(Dot.BASE, value);
    }
    
    public static double getLogarithmic(double value){
        return Math.log(value)/Math.log(Dot.BASE);
    }
    
}
