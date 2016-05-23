/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neview.model.elements;

import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author mugarov
 */
public class DotSorter implements Comparator<Dot>{
    
    private boolean sortByCoverage;
    
    public DotSorter(boolean sortByCoverage){
        this.sortByCoverage = sortByCoverage;
    }
    
    @Override
    public int compare(Dot o1, Dot o2) {
        double value1;
        double value2;
        if(this.sortByCoverage){
            value1 = o1.getY();
            value2 = o2.getY();
        }
        else{
            value1 = o1.getX();
            value2 = o2.getX();
        }
        return (int) (value1-value2);
        
    }
}
