/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neview.model.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 *
 * @author mugarov
 */
public class DotBag extends ArrayList<Dot> {
    
    private String name;
 
    
    public DotBag(String name){
        super();
        this.name = name;
    }
    
    public DotBag(ArrayList<Dot> dots, String name){
        super(dots);
        this.name = name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void sort(boolean byCoverage){
        
        DotSorter sorter = new DotSorter(byCoverage);
        Collections.sort(this, sorter);
    }

    
    
}
